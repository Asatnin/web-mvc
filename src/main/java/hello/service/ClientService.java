package hello.service;

import hello.domain.Client;
import hello.domain.User;

public interface ClientService {
    boolean isExists(String clientId);
    Client getClient(String clientId, String clientSecret, String code);
    Client updateClientCode(String clientId, String code);
    Client updateClientRedirectUri(String clientId, String redirectUri);
    Client updateClientUser(String clientId, User user);
    Client getClient(String clientId, String clientSecret);
}
