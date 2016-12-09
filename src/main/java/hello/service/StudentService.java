package hello.service;

import hello.domain.Student;
import hello.web.model.CourseRequest;
import hello.web.model.StudentRequest;

import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();
    List<Student> getAllStudents(int page, int size);
    Student getById(int id);
    Student save(StudentRequest studentRequest);
    void delete(int id);
    Student update(int id, StudentRequest studentRequest);
    int addCourseToStudent(int studentId, CourseRequest courseRequest);
}
