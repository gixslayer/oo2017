package oo.assignment3;

public interface Geometric extends Comparable<Geometric> {
    double left();
    double right();
    double bottom();
    double top();
    double area();

    void move(double dx, double dy);
}
