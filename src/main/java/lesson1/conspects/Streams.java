package lesson1.conspects;

import lesson1.homework.Department;
import lesson1.homework.Person;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Streams {

    public static void main(String[] args) {
//    Optional<Person> personOpt = Optional.empty();
//    personOpt.or
        List<Department> departments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            departments.add(new Department("Department #" + i));
        }

        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            persons.add(new Person(
                    "Person #" + i,
                    ThreadLocalRandom.current().nextInt(20, 61),
                    ThreadLocalRandom.current().nextInt(20_000, 100_000) * 1.0,
                    departments.get(ThreadLocalRandom.current().nextInt(departments.size()))
            ));
        }

        // Найти сотрудника, который получает больше всех
        persons.stream()
                .max(Comparator.comparing(Person::getSalary))
                .ifPresent(System.out::println);

        Function<Person, Integer> personDepartmentNumberExtractor = person -> {
            String departmentName = person.getDepartment().getName();
            return Integer.parseInt(departmentName.split("#")[1]);
        };

        // Найти сотрудников, которые старше 40 лет и работают в департаменте с номером большe, и сохранить их в LinkedList
        LinkedList<Person> collect = persons.stream()
                .filter(it -> it.getAge() > 40)
                .filter(it -> personDepartmentNumberExtractor.apply(it) > 3)
                .collect(Collectors.toCollection(LinkedList::new));

        // Найти департаменты, в которых работают сотрудники, которые получают выше среднего
        double averageSalary = persons.stream()
                .mapToDouble(Person::getSalary)
                .average()
                .orElse(0.0);

        persons.stream()
                .filter(it -> it.getSalary() > averageSalary)
                .map(Person::getDepartment)
                .distinct()
                .forEach(System.out::println);


        // Собрать Map<String, List<Person>> - в которой ключ - имя отдела, значение - сотрудники, которые работают в этом отделе
        Map<String, List<Person>> personsGroupedByDepName = persons.stream()
                .collect(Collectors.groupingBy(it -> it.getDepartment().getName()));
        System.out.println(personsGroupedByDepName);


        // Собрать Map<String, Person> - в которой ключ - имя отдела, значение - сотрудник с самой высокой зарплатой в этом отделе
        Comparator<Person> salaryComparator = Comparator.comparing(Person::getSalary);
        Map<String, Person> maxSalary = persons.stream()
                .collect(Collectors.toMap(it -> it.getDepartment().getName(), Function.identity(), (first, second) -> {
                    if (salaryComparator.compare(first, second) > 0) {
                        return first;
                    }
                    return second;
                }));
    }
}
