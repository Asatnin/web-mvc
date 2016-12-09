package hello.service;

import hello.domain.AccessTokenInfo;
import hello.domain.Client;
import hello.domain.User;

public interface AccessTokenService {
    AccessTokenInfo create(String accessToken, String refreshToken, int expiresIn, User user, Client client);
    AccessTokenInfo retrieve(Client client, User user);
    AccessTokenInfo save(AccessTokenInfo accessTokenInfo);
    AccessTokenInfo getForRefresh(String refreshToken, Client client);
    AccessTokenInfo getAccessTokenInfo(String accessToken);
}
