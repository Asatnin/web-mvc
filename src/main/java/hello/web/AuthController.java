package hello.web;

import hello.domain.AccessTokenInfo;
import hello.domain.Client;
import hello.domain.User;
import hello.service.AccessTokenService;
import hello.service.ClientService;
import hello.service.UserService;
import hello.web.model.AccessTokenResponse;
import hello.web.model.UserLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.UUID;

@Controller
public class AuthController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/auth", method = RequestMethod.GET)
    public Object auth(@RequestParam("response_type") String responseType,
                       @RequestParam("client_id") String clientId,
                       @RequestParam("redirect_uri") String redirectUri,
                       @RequestParam("scope") String scope,
                       HttpSession session) {
        if (responseType.equals("code")) {
            if (clientService.isExists(clientId)) {
                /*String code = genCode();
                clientService.updateClientCode(clientId, code);
                clientService.updateClientRedirectUri(clientId, redirectUri);*/
                //return new ModelAndView("login", "userRequest", new UserLoginRequest());
                session.setAttribute("response_type", responseType);
                session.setAttribute("client_id", clientId);
                session.setAttribute("redirect_uri", redirectUri);
                session.setAttribute("scope", scope);
                return "forward:/login";
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginPage() {
        return new ModelAndView("login", "userRequest", new UserLoginRequest());
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@ModelAttribute("userRequest") UserLoginRequest userRequest,
                                        @SessionAttribute("response_type") String responseType,
                                        @SessionAttribute("client_id") String clientId,
                                        @SessionAttribute("redirect_uri") String redirectUri,
                                        @SessionAttribute("scope") String scope,
                                        HttpSession session) {
        User user = userService.checkLogin(userRequest.getLogin(), userRequest.getPassword());
        if (user != null) {
            session.removeAttribute("response_type");
            session.removeAttribute("client_id");
            session.removeAttribute("redirect_uri");
            session.removeAttribute("scope");

            String code = genCode();
            clientService.updateClientCode(clientId, code);
            clientService.updateClientRedirectUri(clientId, redirectUri);
            clientService.updateClientUser(clientId, user);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.LOCATION, redirectUri + "?code=" + code);
            return ResponseEntity.status(HttpStatus.FOUND).headers(headers).body(null);
        }

        session.removeAttribute("response_type");
        session.removeAttribute("client_id");
        session.removeAttribute("redirect_uri");
        session.removeAttribute("scope");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping(path = "/token", method = RequestMethod.POST)
    @ResponseBody
    public Object token(@RequestParam("grant_type") String grantType,
                      @RequestParam("client_id") String clientId,
                      @RequestParam("client_secret") String clientSecret,
                      @RequestParam("code") String code) {
        if (grantType.equals("authorization_code")) {
            // тут дб клиент. И проверить, что до этого не существует уже созданного токена для этого клиента
            /// с этим юзером
            Client client = clientService.getClient(clientId, clientSecret, code);
            if (client != null && client.getUser() != null) {
                AccessTokenInfo prevInfo = accessTokenService.retrieve(client, client.getUser());

                String accessToken = genCode();
                String refreshToken = genCode();
                int expiresIn = 60;

                if (prevInfo != null) {
                    prevInfo.setAccessToken(accessToken);
                    prevInfo.setRefreshToken(refreshToken);
                    prevInfo.setExpiresIn(expiresIn);

                    return new AccessTokenResponse(accessTokenService.save(prevInfo));
                }

                AccessTokenInfo accessTokenInfo = accessTokenService.create(accessToken,
                        refreshToken, expiresIn, client.getUser(), client);
                return new AccessTokenResponse(accessTokenInfo);
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping(path = "/refresh", method = RequestMethod.POST)
    @ResponseBody
    public Object refresh(@RequestParam("grant_type") String grantType,
                          @RequestParam("refresh_token") String refreshToken,
                          @RequestParam("client_id") String clientId,
                          @RequestParam("client_secret") String clientSecret) {
        if (grantType.equals("refresh_token")) {
            Client client = clientService.getClient(clientId, clientSecret);
            if (client != null) {
                AccessTokenInfo prevInfo = accessTokenService.getForRefresh(refreshToken, client);
                if (prevInfo != null) {

                    String accessToken = genCode();
                    String newRefreshToken = genCode();
                    int expiresIn = 60;

                    prevInfo.setAccessToken(accessToken);
                    prevInfo.setRefreshToken(newRefreshToken);
                    prevInfo.setExpiresIn(expiresIn);

                    return new AccessTokenResponse(accessTokenService.save(prevInfo));
                }
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    private String genCode() {
        return UUID.randomUUID().toString();
        /*SecureRandom random = new SecureRandom();
        byte[] codeBytes = new byte[20];
        random.nextBytes(codeBytes);
        return new String(codeBytes);*/
    }
}
