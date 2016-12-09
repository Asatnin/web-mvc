package hello.service;

import hello.domain.Course;
import hello.domain.Instructor;
import hello.domain.Statistics;
import hello.web.model.CourseRequest;
import hello.web.model.InstructorRequest;
import hello.web.model.StatisticsRequest;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class CourseServiceImpl implements CourseService {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private InstructorService instructorService;

    @Override
    public Course save(CourseRequest courseRequest) {
        Course course = new Course();
        course.setTitle(courseRequest.getTitle());

        return sessionFactory.getCurrentSession().get(Course.class,
                sessionFactory.getCurrentSession().save(course));
    }

    @Override
    public List<Course> getAllCourses() {
        return sessionFactory.getCurrentSession().createQuery("from Course", Course.class).list();
    }

    @Override
    public List<Course> getAllCourses(int page, int size) {
        Query<Course> query = sessionFactory.getCurrentSession().createQuery("from Course", Course.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.list();
    }

    @Override
    public Course getById(int id) {
        return sessionFactory.getCurrentSession().get(Course.class, id);
    }

    @Override
    public void deleteStatisticsFromCourse(int courseId, int statisticsId) {
        Course course = getById(courseId);
        Statistics statistics = statisticsService.getById(statisticsId);
        statisticsService.deleteById(statisticsId);
        course.getStatisticsList().remove(statistics);
        sessionFactory.getCurrentSession().save(course);
    }

    @Override
    public int addStatisticsToCourse(int courseId, StatisticsRequest statisticsRequest) {
        Course course = getById(courseId);
        Statistics statistics = statisticsService.save(statisticsRequest);
        course.getStatisticsList().add(statistics);
        sessionFactory.getCurrentSession().save(course);
        return statistics.getId();
    }

    @Override
    public int addInstructorToCourse(int courseId, InstructorRequest instructorRequest) {
        Course course = getById(courseId);
        Instructor instructor = instructorService.save(instructorRequest);
        course.getInstructors().add(instructor);
        sessionFactory.getCurrentSession().save(course);
        return instructor.getId();
    }

    @Override
    public void deleteInstructorFromCourse(int courseId, int instructorId) {
        Course course = getById(courseId);
        Instructor instructor = instructorService.getById(instructorId);
        instructorService.delete(instructor);
        course.getInstructors().remove(instructor);
        sessionFactory.getCurrentSession().save(course);
    }

    @Override
    public List<Statistics> allStatistics(int courseId) {
        return sessionFactory.getCurrentSession().get(Course.class, courseId).getStatisticsList();
    }

    @Override
    public List<Statistics> allStatistics(int courseId, int page, int size) {
        List<Statistics> list = allStatistics(courseId);
        if (page * size >= list.size()) {
            list = new ArrayList<>();
        } else {
            list = list.subList(page * size, Math.min(page * size + size, list.size()));
        }
        return list;
    }

    @Override
    public List<Instructor> allInstructors(int courseId) {
        return sessionFactory.getCurrentSession().get(Course.class, courseId).getInstructors();
    }

    @Override
    public List<Instructor> allInstructors(int courseId, int page, int size) {
        List<Instructor> list = allInstructors(courseId);
        if (page * size >= list.size()) {
            list = new ArrayList<>();
        } else {
            list = list.subList(page * size, Math.min(page * size + size, list.size()));
        }
        return list;
    }
}
