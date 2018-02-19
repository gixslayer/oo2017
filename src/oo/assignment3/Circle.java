package oo.assignment3;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Circle implements Geometric {
    private double x;
    private double y;
    private double r;

    public Circle(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    @Override
    public double left() {
        return x - r;
    }

    @Override
    public double right() {
        return x + r;
    }

    @Override
    public double bottom() {
        return y - r;
    }

    @Override
    public double top() {
        return y + r;
    }

    @Override
    public double area() {
        return Math.PI * r * r;
    }

    @Override
    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }

    @Override
    public int compareTo(Geometric geometric) {
        return Double.compare(area(), geometric.area());
    }

    @Override
    public String toString() {
        return String.format("x: %f y: %f r: %f", x, y, r);
    }
}
