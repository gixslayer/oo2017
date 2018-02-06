package oo.assignment1;

import java.util.Optional;
import java.util.Scanner;

/**
 * @author Ciske Harsema - s1010048
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentGroup studentGroup = new StudentGroup();
        int studentNumber;

        studentGroup.create();
        studentGroup.display();

        do {
            System.out.print("\nStudent number and new first/second name? ");
            studentNumber = scanner.nextInt();

            if(studentNumber >= 0) {
                Optional<Student> student = studentGroup.getStudent(studentNumber);

                if (student.isPresent()) {
                    String newFirstName = scanner.next();
                    String newSecondName = scanner.next();

                    student.get().setFirstName(newFirstName);
                    student.get().setSecondName(newSecondName);

                    studentGroup.display();
                } else {
                    scanner.nextLine(); // Consume buffered name(s) to avoid parsing it as an integer.
                    System.err.println("No student found with that student number");
                }
            }
        } while(studentNumber >= 0);
    }
}
