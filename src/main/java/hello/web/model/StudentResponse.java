package hello.web.model;

import hello.domain.Student;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class StudentResponse {
    private String firstName;
    private String lastName;
    private Date dayOfBirth;
    private List<CourseResponse> courses = new ArrayList<>();

    public StudentResponse(Student student) {
        firstName = student.getFirstName();
        lastName = student.getLastName();
        dayOfBirth = student.getDayOfBirth();
        courses = student.getCourses()
                .stream()
                .map(CourseResponse::new)
                .collect(Collectors.toList());
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDayOfBirth() {
        return dayOfBirth;
    }

    public List<CourseResponse> getCourses() {
        return courses;
    }
}
