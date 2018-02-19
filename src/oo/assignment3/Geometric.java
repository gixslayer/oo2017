package oo.assignment3;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public interface Geometric extends Comparable<Geometric> {
    double left();
    double right();
    double bottom();
    double top();
    double area();

    void move(double dx, double dy);
}
