package hello.service;

import hello.domain.Course;
import hello.domain.Student;
import hello.web.model.CourseRequest;
import hello.web.model.StudentRequest;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class StudentServiceImpl implements StudentService {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private CourseService courseService;

    @Override
    public List<Student> getAllStudents() {
        return sessionFactory.getCurrentSession().createQuery("from Student", Student.class).list();
    }

    @Override
    public List<Student> getAllStudents(int page, int size) {
        Query<Student> query = sessionFactory.getCurrentSession().createQuery("from Student", Student.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.list();
    }

    @Override
    public Student getById(int id) {
        return sessionFactory.getCurrentSession().get(Student.class, id);
    }

    @Override
    public Student save(StudentRequest studentRequest) {
        Student student = new Student();
        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setDayOfBirth(studentRequest.getDayOfBirth());

        return sessionFactory.getCurrentSession().get(Student.class,
                sessionFactory.getCurrentSession().save(student));
    }

    @Override
    public void delete(int id) {
        Student student = getById(id);
        sessionFactory.getCurrentSession().delete(student);
    }

    @Override
    public Student update(int id, StudentRequest studentRequest) {
        Student student = getById(id);
        student.setFirstName(studentRequest.getFirstName() != null ?
                studentRequest.getFirstName() : student.getFirstName());
        student.setLastName(studentRequest.getLastName() != null ?
                studentRequest.getLastName() : student.getLastName());
        student.setDayOfBirth(studentRequest.getDayOfBirth() != null ?
                studentRequest.getDayOfBirth() : student.getDayOfBirth());
        sessionFactory.getCurrentSession().update(student);

        return getById(id);
    }

    @Override
    public int addCourseToStudent(int studentId, CourseRequest courseRequest) {
        Student student = getById(studentId);
        Course course = courseService.save(courseRequest);
        student.getCourses().add(course);
        sessionFactory.getCurrentSession().save(student);
        return course.getId();
    }
}
