package hello.web.model;

import hello.domain.Instructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InstructorResponse {
    private String firstName;
    private String lastName;
    private String university;

    public InstructorResponse(Instructor instructor) {
        firstName = instructor.getFirstName();
        lastName = instructor.getLastName();
        university = instructor.getUniversity();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUniversity() {
        return university;
    }
}
