package oo.assignment4;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public enum Mark {
    Circle, Cross;

    public Mark other() {
        return this == Circle ? Cross : Circle;
    }
}
