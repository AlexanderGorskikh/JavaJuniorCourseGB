package Lesson3;

import Lesson3.annotations.SQLHandler;
import Lesson3.annotations.Table;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class MyImplementationJDBC {
    private Connection connection = null;
    private final SQLHandler sqlHandler = new SQLHandler();

    public <T> void createConnection(Class<T> type) throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:myData");
        String table = type.getAnnotation(Table.class).table();
        createTable(type);
        insertData(table,
                "id,age,firstName,lastName",
                """
                        (1, 45, 'Bob', 'Ross1'),
                        (2, 46, 'Bob', 'Ross2'),
                        (3, 47, 'Bob', 'Ross3'),
                        (4, 48, 'Bob', 'Ross4'),
                        (5, 49, 'Bob', 'Ross5')
                        """);
        deleteRowWithId(table, 3);
        acceptConnection(table);
        connection.close();
    }

    public <T> void createTable(Class<T> type) throws SQLException {
        try {
            var statement = connection.createStatement();
            String sql = "CREATE TABLE " + sqlHandler.createSQLTable(type, true);
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void acceptConnection(String table) throws SQLException {
        try (var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(
                    "select id,age,firstName,lastName from " + table);
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long age = resultSet.getLong("age");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                System.out.println(id + " " + age + " " + firstName + " " + lastName);
            }
        }
    }

    public <T> void insertData(String table, String params, String values) throws SQLException {
        try (var statement = connection.createStatement()) {
            String sql = "INSERT INTO "
                    + table
                    + "(" + params + ")"
                    + " VALUES "
                    + values;
            statement.executeUpdate(sql);
        }
    }

    private void deleteRowWithId(String table, int id) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            System.out.println("DELETED: " + statement.executeUpdate("delete from " + table + " where id = " + id));
        }
    }
}

