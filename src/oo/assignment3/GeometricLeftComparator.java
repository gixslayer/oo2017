package oo.assignment3;

import java.util.Comparator;

public class GeometricLeftComparator implements Comparator<Geometric> {
    @Override
    public int compare(Geometric geometric, Geometric t1) {
        return Double.compare(geometric.left(), t1.left());
    }
}
