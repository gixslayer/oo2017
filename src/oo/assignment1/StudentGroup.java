package oo.assignment1;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Ciske Harsema - s1010048
 */
public class StudentGroup {
    private final Student[] students;

    public StudentGroup(int size) {
        students = new Student[size];
    }

    public void set(int index, Student student) {
        students[index] = student;
    }

    public Optional<Student> getStudent(int studentNumber) {
        return Arrays.stream(students).filter(s -> s.getNumber() == studentNumber).findFirst();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(students).forEach(s -> stringBuilder.append(s).append('\n'));

        return stringBuilder.toString();
    }
}
