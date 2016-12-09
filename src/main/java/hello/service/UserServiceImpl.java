package hello.service;

import hello.domain.User;
import hello.web.model.UserRequest;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User save(UserRequest userRequest) {
        User user = new User();
        user.setLogin(userRequest.getLogin());
        user.setPassword(userRequest.getPassword());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());

        return sessionFactory.getCurrentSession().get(User.class,
                sessionFactory.getCurrentSession().save(user));
    }

    @Override
    public User get(int id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public User checkLogin(String login, String password) {
        Query<User> query = sessionFactory.getCurrentSession()
                .createQuery("from User where login = :login and password = :password", User.class);
        query.setParameter("login", login);
        query.setParameter("password", password);

        return query.getResultList().size() == 0 ? null : query.getResultList().get(0);
    }
}
