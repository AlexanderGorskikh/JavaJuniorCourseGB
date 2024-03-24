package Lesson4.core;

import Lesson4.model.Student;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class DataBase {
    private final Configuration configuration = new Configuration().configure();

    public DataBase() {
        try (var sessionFactory = configuration.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                addEntities(session, List.of(
                        new Student(1L, 18L, "Bob", "Ross"),
                        new Student(2L, 55L, "Bobs", "Ross1"),
                        new Student(3L, 20L, "Boba", "Ross2"),
                        new Student(4L, 21L, "Bobd", "Ross4"),
                        new Student(5L, 12L, "Bobff", "Ross5"),
                        new Student(6L, 15L, "Bobx", "Ross1")));
                System.out.println(findStudentById(session, 3L));
                deleteStudent(session, 5L);
                mergeStudent(session, 4L, "");
                createQuery(session);
            }
        }
    }


    public void addEntities(Session session, List<Student> students) {
        var transaction = session.beginTransaction();
        students.forEach(session::persist);
        transaction.commit();
    }

    public Student findStudentById(Session session, Long id) {
        var transaction = session.getTransaction();
        return session.find(Student.class, id);
    }

    public void deleteStudent(Session session, Long id) {
        Student student = session.find(Student.class, id);
        var transaction = session.beginTransaction();
        session.remove(student);
        transaction.commit();
    }

    public void mergeStudent(Session session, Long id, String newFirstName) {
        var transaction = session.beginTransaction();
        Student student = session.find(Student.class, id);
        student.setFirstName(newFirstName);
        session.merge(student);
        transaction.commit();
    }

    public void createQuery(Session session) {
        var transaction = session.beginTransaction();
        Query<Student> studentQuery = session.createNativeQuery(
                "SELECT * FROM Student WHERE age >= 20", Student.class);
        studentQuery.getResultList().forEach(System.out::println);
        transaction.commit();
    }
}
