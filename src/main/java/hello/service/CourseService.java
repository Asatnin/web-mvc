package hello.service;

import hello.domain.Course;
import hello.domain.Instructor;
import hello.domain.Statistics;
import hello.web.model.CourseRequest;
import hello.web.model.InstructorRequest;
import hello.web.model.StatisticsRequest;

import java.util.List;

public interface CourseService {
    Course save(CourseRequest courseRequest);
    List<Course> getAllCourses();
    List<Course> getAllCourses(int page, int size);
    Course getById(int id);
    void deleteStatisticsFromCourse(int courseId, int statisticsId);
    int addStatisticsToCourse(int courseId, StatisticsRequest statisticsRequest);
    int addInstructorToCourse(int courseId, InstructorRequest instructorRequest);
    void deleteInstructorFromCourse(int courseId, int instructorId);
    List<Statistics> allStatistics(int courseId);
    List<Statistics> allStatistics(int courseId, int page, int size);
    List<Instructor> allInstructors(int courseId);
    List<Instructor> allInstructors(int courseId, int page, int size);
}
