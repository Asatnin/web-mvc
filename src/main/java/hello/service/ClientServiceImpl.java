package hello.service;

import hello.domain.Client;
import hello.domain.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ClientServiceImpl implements ClientService {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean isExists(String clientId) {
        Query<Client> query = sessionFactory.getCurrentSession()
                .createQuery("from Client where client_id = :client_id", Client.class);
        query.setParameter("client_id", clientId);

        return query.getResultList().size() > 0;
    }

    @Override
    public Client updateClientCode(String clientId, String code) {
        Query<Client> query = sessionFactory.getCurrentSession()
                .createQuery("from Client where client_id = :client_id", Client.class);
        query.setParameter("client_id", clientId);

        Client client = query.getSingleResult();
        client.setCode(code);
        return sessionFactory.getCurrentSession()
                .get(Client.class, sessionFactory.getCurrentSession().save(client));
    }

    @Override
    public Client updateClientRedirectUri(String clientId, String redirectUri) {
        Query<Client> query = sessionFactory.getCurrentSession()
                .createQuery("from Client where client_id = :client_id", Client.class);
        query.setParameter("client_id", clientId);

        Client client = query.getSingleResult();
        client.setRedirectUri(redirectUri);
        return sessionFactory.getCurrentSession()
                .get(Client.class, sessionFactory.getCurrentSession().save(client));
    }

    @Override
    public Client getClient(String clientId, String clientSecret, String code) {
        Query<Client> query = sessionFactory.getCurrentSession()
                .createQuery("from Client where client_id = :client_id "
                        + "and client_secret = :client_secret "
                        + "and code = :code", Client.class);
        query.setParameter("client_id", clientId);
        query.setParameter("client_secret", clientSecret);
        query.setParameter("code", code);

        return query.getResultList().size() == 0 ? null : query.getResultList().get(0);
    }

    @Override
    public Client getClient(String clientId, String clientSecret) {
        Query<Client> query = sessionFactory.getCurrentSession()
                .createQuery("from Client where client_id = :client_id "
                        + "and client_secret = :client_secret", Client.class);
        query.setParameter("client_id", clientId);
        query.setParameter("client_secret", clientSecret);

        return query.getResultList().size() == 0 ? null : query.getResultList().get(0);
    }

    @Override
    public Client updateClientUser(String clientId, User user) {
        Query<Client> query = sessionFactory.getCurrentSession()
                .createQuery("from Client where client_id = :client_id", Client.class);
        query.setParameter("client_id", clientId);

        Client client = query.getSingleResult();
        client.setUser(user);
        return sessionFactory.getCurrentSession()
                .get(Client.class, sessionFactory.getCurrentSession().save(client));
    }
}
