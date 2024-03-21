package Lesson3;

import Lesson3.model.Student;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        var jdbc = new MyImplementationJDBC();
        jdbc.createConnection(Student.class);
    }
}
