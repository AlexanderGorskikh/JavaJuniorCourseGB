package Lesson3.model;

import Lesson3.annotations.Column;
import Lesson3.annotations.Id;
import Lesson3.annotations.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(table = "Student")
public class Student {
    @Id
    private int id;
    @Column(column = "age")
    private int age;
    @Column(column = "firstName")
    private String firstName;
    @Column(column = "lastName")
    private String lastName;
}
