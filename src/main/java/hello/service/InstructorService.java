package hello.service;

import hello.domain.Instructor;
import hello.web.model.InstructorRequest;

import java.util.List;

public interface InstructorService {
    Instructor save(InstructorRequest instructorRequest);
    Instructor getById(int id);
    void delete(Instructor instructor);
}
