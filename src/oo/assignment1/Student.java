package oo.assignment1;

/**
 * @author Ciske Harsema - s1010048
 */
public class Student {
    private String firstName;
    private String secondName;
    private final int number;

    public Student(String firstName, String secondName, int number) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.number = number;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return String.format("%s %s, s%d", firstName, secondName, number);
    }
}
