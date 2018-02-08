package oo.assignment1;

import java.util.Optional;
import java.util.Scanner;

/**
 * @author Ciske Harsema - s1010048
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int studentNumber;

        System.out.print("How big is the group? ");
        int size = scanner.nextInt();
        StudentGroup studentGroup = new StudentGroup(size);

        for(int i = 0; i < size; ++i) {
            System.out.print("Enter student data (firstName secondName number): ");
            String firstName = scanner.next();
            String secondName = scanner.next();
            studentNumber = scanner.nextInt();

            studentGroup.set(i, new Student(firstName, secondName, studentNumber));
        }

        System.out.println("\nThe group now contains:");
        System.out.println(studentGroup);

        do {
            System.out.print("Student number and new first/second name? ");
            studentNumber = scanner.nextInt();

            if(studentNumber >= 0) {
                Optional<Student> student = studentGroup.getStudent(studentNumber);

                if (student.isPresent()) {
                    String newFirstName = scanner.next();
                    String newSecondName = scanner.next();

                    student.get().setFirstName(newFirstName);
                    student.get().setSecondName(newSecondName);

                    System.out.println("\nThe group now contains:");
                    System.out.println(studentGroup);
                } else {
                    scanner.nextLine(); // Consume buffered name(s) to avoid parsing it as an integer.
                    System.err.println("No student found with that student number");
                }
            }
        } while(studentNumber >= 0);
    }
}
