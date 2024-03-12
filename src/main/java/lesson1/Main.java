package lesson1;

import lesson1.homework.Department;
import lesson1.homework.Homework;
import lesson1.homework.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;

public class Main {

    public static void main(String[] args) {
        List<Department> departments = createDepartments();
        List<Person> persons = createPersons(departments);
//        Homework.printNamesOrdered(persons);
//        Homework.findFirstPersons(persons).forEach(System.out::println);
//        System.out.println(Homework.findTopDepartment(persons));
//        System.out.println(Homework.printDepartmentOldestPerson(persons));
    }


    private static List<Department> createDepartments() {
        List<Department> departments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            departments.add(new Department("Department #" + i));
        }
        return departments;
    }

    private static List<Person> createPersons(List<Department> departments) {
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            persons.add(new Person(
                    "Person #" + i,
                    ThreadLocalRandom.current().nextInt(20, 61),
                    ThreadLocalRandom.current().nextInt(20_000, 100_000) * 1.0,
                    departments.get(ThreadLocalRandom.current().nextInt(departments.size()))
            ));
        }
        return persons;
    }
}