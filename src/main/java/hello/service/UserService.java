package hello.service;

import hello.domain.User;
import hello.web.model.UserRequest;

public interface UserService {
    User save(UserRequest userRequest);
    User get(int id);
    User checkLogin(String login, String password);
}
