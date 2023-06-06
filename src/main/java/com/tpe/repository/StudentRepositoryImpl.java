package com.tpe.repository;

import com.tpe.domain.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Component
@Repository//db ye erişilen class, componentin gelişmişi
public class StudentRepositoryImpl implements StudentRepository{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Student student) {
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();

        session.saveOrUpdate(student);//db de kayıt varsa update eder, yoksa kaydeder.

        transaction.commit();
        session.close();
    }

    @Override
    public List<Student> findAll() {
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();

        List<Student> studentList=session.createQuery("FROM Student",Student.class).getResultList();

        transaction.commit();
        session.close();

        return studentList;
    }

    @Override
    public Optional<Student> findById(Long id) {
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();

        Student student=session.get(Student.class,id);
        Optional<Student> optional=Optional.ofNullable(student);//student yoksa, içi boş bir optional objesi döndürür.

        transaction.commit();
        session.close();
        return optional;
    }

    @Override
    public void delete(Long id) {
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();

        Student student=session.load(Student.class,id);//sileceğimiz objeyi proxy getirmesi yeterli
        session.delete(student);

        transaction.commit();
        session.close();
    }
}
