package hello.web.model;

import hello.domain.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseStatisticsResponse {
    private String title;
    private List<StatisticsResponse> statistics = new ArrayList<>();
    private List<InstructorResponse> instructors = new ArrayList<>();

    public CourseStatisticsResponse(Course course) {
        title = course.getTitle();
        statistics = course.getStatisticsList()
                .stream()
                .map(StatisticsResponse::new)
                .collect(Collectors.toList());
        instructors = course.getInstructors()
                .stream()
                .map(InstructorResponse::new)
                .collect(Collectors.toList());
    }

    public String getTitle() {
        return title;
    }

    public List<StatisticsResponse> getStatistics() {
        return statistics;
    }

    public List<InstructorResponse> getInstructors() {
        return instructors;
    }
}
