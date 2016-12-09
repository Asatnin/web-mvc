package hello.service;

import hello.domain.AccessTokenInfo;
import hello.domain.Client;
import hello.domain.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
@Transactional
public class AccessTokenImpl implements AccessTokenService {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public AccessTokenInfo create(String accessToken, String refreshToken, int expiresIn,
                                  User user, Client client) {
        AccessTokenInfo accessTokenInfo = new AccessTokenInfo();
        accessTokenInfo.setAccessToken(accessToken);
        accessTokenInfo.setRefreshToken(refreshToken);
        accessTokenInfo.setTokenType("bearer");
        accessTokenInfo.setExpiresIn(expiresIn);
        accessTokenInfo.setIssueDate(new Date());
        accessTokenInfo.setUser(user);
        accessTokenInfo.setClient(client);

        return sessionFactory.getCurrentSession().get(AccessTokenInfo.class,
                sessionFactory.getCurrentSession().save(accessTokenInfo));
    }

    @Override
    public AccessTokenInfo retrieve(Client client, User user) {
        Query<AccessTokenInfo> query = sessionFactory.getCurrentSession()
                .createQuery("from AccessTokenInfo where client = :client and user = :user",
                        AccessTokenInfo.class);
        query.setParameter("client", client);
        query.setParameter("user", user);
        return query.getResultList().size() == 0 ? null : query.getResultList().get(0);
    }

    @Override
    public AccessTokenInfo save(AccessTokenInfo accessTokenInfo) {
        AccessTokenInfo oldInfo = sessionFactory.getCurrentSession().get(AccessTokenInfo.class,
                accessTokenInfo.getId());
        oldInfo.setAccessToken(accessTokenInfo.getAccessToken());
        oldInfo.setRefreshToken(accessTokenInfo.getRefreshToken());
        oldInfo.setExpiresIn(accessTokenInfo.getExpiresIn());
        oldInfo.setIssueDate(new Date());
        return sessionFactory.getCurrentSession().get(AccessTokenInfo.class,
                sessionFactory.getCurrentSession().save(oldInfo));
    }

    @Override
    public AccessTokenInfo getForRefresh(String refreshToken, Client client) {
        Query<AccessTokenInfo> query = sessionFactory.getCurrentSession()
                .createQuery("from AccessTokenInfo where refreshtoken = :refreshtoken "
                                + "and client = :client", AccessTokenInfo.class);
        query.setParameter("refreshtoken", refreshToken);
        query.setParameter("client", client);
        return query.getResultList().size() == 0 ? null : query.getResultList().get(0);
    }

    @Override
    public AccessTokenInfo getAccessTokenInfo(String accessToken) {
        Query<AccessTokenInfo> query = sessionFactory.getCurrentSession()
                .createQuery("from AccessTokenInfo where accesstoken = :accesstoken",
                        AccessTokenInfo.class);
        query.setParameter("accesstoken", accessToken);
        return query.getResultList().size() == 0 ? null : query.getResultList().get(0);
    }
}
