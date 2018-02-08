package oo.assignment1;

import java.util.Optional;
import java.util.Scanner;

/**
 * @author Ciske Harsema - s1010048
 */
public class AdministrationTUI {
    private final Scanner scanner;
    private StudentGroup studentGroup;

    public AdministrationTUI() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Create a student group of user defined size, and populate it with student data.
     */
    private void createGroup() {
        System.out.print("How big is the group? ");
        int size = scanner.nextInt();
        studentGroup = new StudentGroup(size);

        for(int i = 0; i < size; ++i) {
            System.out.print("Enter student data (firstName secondName number): ");
            String firstName = scanner.next();
            String secondName = scanner.next();
            int studentNumber = scanner.nextInt();

            studentGroup.set(i, new Student(firstName, secondName, studentNumber));
        }
    }

    private void displayGroup() {
        System.out.println("\nThe group now contains:");
        System.out.println(studentGroup);
    }

    /**
     * Change student names, until the user signals it wishes to stop.
     */
    private void mutateGroup() {
        int studentNumber;

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

                    displayGroup();
                } else {
                    scanner.nextLine(); // Consume buffered name(s) to avoid parsing it as an integer.
                    System.err.println("No student found with that student number");
                }
            }
        } while(studentNumber >= 0);
    }

    /**
     * Create a new student administration, which the user can mutate, until it
     * signals it wishes to stop.
     */
    public void run() {
        createGroup();
        displayGroup();
        mutateGroup();
    }
}
