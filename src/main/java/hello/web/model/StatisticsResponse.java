package hello.web.model;

import hello.domain.Statistics;

public class StatisticsResponse {
    private String year;
    private int averageScore;

    public StatisticsResponse(Statistics statistics) {
        year = statistics.getYear();
        averageScore = statistics.getAverageScore();
    }

    public String getYear() {
        return year;
    }

    public int getAverageScore() {
        return averageScore;
    }
}
