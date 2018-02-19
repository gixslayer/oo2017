package oo.assignment3;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Rectangle implements Geometric {
    private double x;
    private double y;
    private double w;
    private double h;

    public Rectangle(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    public double left() {
        return x;
    }

    @Override
    public double right() {
        return x + w;
    }

    @Override
    public double bottom() {
        return y;
    }

    @Override
    public double top() {
        return y + h;
    }

    @Override
    public double area() {
        return w * h;
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
        return String.format("x: %f y: %f w: %f h: %f", x, y, w, h);
    }
}
