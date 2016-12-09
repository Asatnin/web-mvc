package hello.service;

import hello.domain.Instructor;
import hello.web.model.InstructorRequest;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class InstructorServiceImpl implements InstructorService {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Instructor save(InstructorRequest instructorRequest) {
        Instructor instructor = new Instructor();
        instructor.setFirstName(instructorRequest.getFirstName());
        instructor.setLastName(instructorRequest.getLastName());
        instructor.setUniversity(instructorRequest.getUniversity());

        return sessionFactory.getCurrentSession().get(Instructor.class,
                sessionFactory.getCurrentSession().save(instructor));
    }

    @Override
    public Instructor getById(int id) {
        return sessionFactory.getCurrentSession().get(Instructor.class, id);
    }

    @Override
    public void delete(Instructor instructor) {
        sessionFactory.getCurrentSession().delete(instructor);
    }
}
