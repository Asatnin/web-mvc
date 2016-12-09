package hello.service;

import hello.domain.Statistics;
import hello.web.model.StatisticsRequest;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Statistics getById(int id) {
        return sessionFactory.getCurrentSession().get(Statistics.class, id);
    }

    @Override
    public Statistics save(StatisticsRequest statisticsRequest) {
        Statistics statistics = new Statistics();
        statistics.setYear(statisticsRequest.getYear());
        statistics.setAverageScore(statisticsRequest.getAverageScore());

        return sessionFactory.getCurrentSession().get(Statistics.class,
                sessionFactory.getCurrentSession().save(statistics));
    }

    @Override
    public void deleteById(int id) {
        Statistics statistics = getById(id);
        sessionFactory.getCurrentSession().delete(statistics);
    }

    @Override
    public Statistics updateStatistics(int id, StatisticsRequest statisticsRequest) {
        Statistics statistics = getById(id);
        statistics.setAverageScore(statisticsRequest.getAverageScore());
        statistics.setYear(statisticsRequest.getYear());

        sessionFactory.getCurrentSession().update(statistics);
        return getById(id);
    }
}
