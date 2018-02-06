package oo.assignment1;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

/**
 * @author Ciske Harsema - s1010048
 */
public class StudentGroup {
    private Student[] students;

    public StudentGroup() {
        students = new Student[0];
    }

    public void create() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("How big is the group? ");
        int size = scanner.nextInt();
        students = new Student[size];

        for(int i = 0; i < size; ++i) {
            System.out.print("Enter student data (firstName secondName number): ");
            String firstName = scanner.next();
            String secondName = scanner.next();
            int studentNumber = scanner.nextInt();

            students[i] = new Student(firstName, secondName, studentNumber);
        }
    }

    public void display() {
        System.out.println("\nThe group now contains:");

        for(Student student : students) {
            System.out.println(student);
        }
    }

    public Optional<Student> getStudent(int studentNumber) {
        return Arrays.stream(students).filter(s -> s.getNumber() == studentNumber).findFirst();
    }
}
