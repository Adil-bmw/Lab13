package task1;

import java.util.*;
import java.util.stream.Collectors;

public class Student {
    private String name;
    private int age;
    private int course;
    private double gpa;

    public Student(String name, int age, int course, double gpa) {
        this.name = name;
        this.age = age;
        this.course = course;
        this.gpa = gpa;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public int getCourse() { return course; }
    public double getGpa() { return gpa; }

    @Override
    public String toString() {
        return String.format("%s (Курс %d, GPA: %.2f)", name, course, gpa);
    }
}
