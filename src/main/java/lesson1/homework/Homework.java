package lesson1.homework;


import java.util.*;
import java.util.stream.Collectors;

public class Homework {

    /**
     * Реализовать методы, описанные ниже:
     */

    /**
     * Вывести на консоль отсортированные (по алфавиту) имена персонов
     */
    public static void printNamesOrdered(List<Person> persons) {
        persons.stream().sorted(Comparator.comparing(Person::getName)).forEach(System.out::println);
    }

    /**
     * В каждом департаменте найти самого взрослого сотрудника.
     * Вывести на консоль мапипнг department -> personName
     * Map<Department, Person>
     */
    public static Map<Department, Person> printDepartmentOldestPerson(List<Person> persons) {
        Map<Department, List<Person>> map =
                persons.stream()
                        .collect(Collectors.groupingBy(Person::getDepartment));
        HashMap<Department, Person> tmp = new HashMap<>();
        for (Department d : map.keySet()) {
            Optional<Person> optional = map.get(d).stream()
                    .max(Comparator.comparing(Person::getAge));
            tmp.put(d, optional.get());
        }
        return tmp;
    }

    /**
     * Найти 10 первых сотрудников, младше 30 лет, у которых зарплата выше 50_000
     */
    public static List<Person> findFirstPersons(List<Person> persons) {
        return persons.stream()
                .filter(person -> person.getSalary() > 50000)
                .filter(person -> person.getAge() < 30)
                .limit(10)
                .collect(Collectors.toList());
    }

    /**
     * Найти депаратмент, чья суммарная зарплата всех сотрудников максимальна
     */
    public static Optional<Department> findTopDepartment(List<Person> persons) {
        Map<Department, Double> map = persons.stream()
                .collect(Collectors.groupingBy(
                        Person::getDepartment,
                        Collectors.summingDouble(
                                Person::getSalary)));
        Optional<Double> max = map
                .values()
                .stream()
                .max(Double::compareTo);
        for (Department d : map.keySet()) {
            if (map.get(d).equals(max.orElse(0.0))) return Optional.of(d);
        }
        return Optional.empty();
    }
}
