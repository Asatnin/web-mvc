package hello.service;

import hello.domain.Statistics;
import hello.web.model.StatisticsRequest;

import java.util.List;

public interface StatisticsService {
    Statistics getById(int id);
    Statistics save(StatisticsRequest statisticsRequest);
    void deleteById(int id);
    Statistics updateStatistics(int id, StatisticsRequest statisticsRequest);
}
