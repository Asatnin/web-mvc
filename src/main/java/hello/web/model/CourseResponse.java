package hello.web.model;

import hello.domain.Course;

public class CourseResponse {
    private String title;

    public CourseResponse(Course course) {
        title = course.getTitle();
    }

    public String getTitle() {
        return title;
    }
}
