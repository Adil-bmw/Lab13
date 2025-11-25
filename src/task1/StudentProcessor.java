package task1;

import java.util.*;
import java.util.stream.Collectors;

public class StudentProcessor {
    public static void main(String[] args) {
        List<Student> students = generateStudents();

        System.out.println("=== Студенты с GPA > 3.5 ===");
        List<Student> top = students.stream()
            .filter(s -> s.getGpa() > 3.5)
            .sorted(Comparator.comparing(Student::getName))
            .collect(Collectors.toList());
        top.forEach(System.out::println);
        System.out.println("Всего: " + top.size());

        System.out.println("\n=== Студенты, отсортированные по имени ===");
        students.stream()
            .sorted(Comparator.comparing(Student::getName))
            .forEach(System.out::println);

        System.out.println("\n=== Имена студентов 3-го курса ===");
        List<String> namesCourse3 = students.stream()
            .filter(s -> s.getCourse() == 3)
            .map(Student::getName)
            .collect(Collectors.toList());
        System.out.println(namesCourse3);

        double avgGpa = students.stream()
            .mapToDouble(Student::getGpa)
            .average()
            .orElse(0.0);
        System.out.printf("\nСредний GPA всех студентов: %.2f\n", avgGpa);

        Student best = students.stream()
            .max(Comparator.comparingDouble(Student::getGpa))
            .orElse(null);
        System.out.println("\nСтудент с максимальным GPA: " + (best == null ? "Не найден" : best));

        System.out.println("\n=== Группировка по курсам ===");
        Map<Integer, List<Student>> byCourse = students.stream()
            .collect(Collectors.groupingBy(Student::getCourse));
        byCourse.forEach((course, list) -> {
            System.out.println("Курс " + course + ": " + list.size() + " студентов");
        });

        System.out.println("\n=== Количество студентов на каждом курсе ===");
        Map<Integer, Long> countByCourse = students.stream()
            .collect(Collectors.groupingBy(Student::getCourse, Collectors.counting()));
        System.out.println(countByCourse);
    }

    private static List<Student> generateStudents() {
        return Arrays.asList(
            new Student("Александр", 19, 2, 3.21),
            new Student("Алиса", 18, 2, 3.85),
            new Student("Дмитрий", 20, 3, 3.92),
            new Student("Елена", 17, 1, 3.67),
            new Student("Иван", 20, 3, 3.10),
            new Student("Мария", 19, 3, 3.45),
            new Student("Олег", 21, 4, 2.95),
            new Student("Наталья", 18, 1, 3.50),
            new Student("Пётр", 22, 4, 3.02),
            new Student("Светлана", 20, 2, 3.76),
            new Student("Роман", 19, 1, 2.88),
            new Student("Кирилл", 21, 4, 3.40),
            new Student("Вера", 18, 1, 3.60),
            new Student("Галина", 20, 3, 3.30),
            new Student("Татьяна", 19, 2, 3.11),
            new Student("Юрий", 22, 4, 3.05),
            new Student("Ирина", 18, 1, 3.95),
            new Student("Сергей", 20, 3, 2.99),
            new Student("Людмила", 21, 4, 3.47),
            new Student("Анна", 19, 2, 3.88),
            new Student("Максим", 20, 3, 3.20),
            new Student("Оксана", 18, 1, 2.77),
            new Student("Виктор", 22, 4, 3.66),
            new Student("Егор", 19, 2, 3.33),
            new Student("Полина", 20, 3, 3.58)
        );
    }
}
