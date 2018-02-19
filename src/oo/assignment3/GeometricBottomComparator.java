package oo.assignment3;

import java.util.Comparator;

public class GeometricBottomComparator implements Comparator<Geometric> {
    @Override
    public int compare(Geometric geometric, Geometric t1) {
        return Double.compare(geometric.bottom(), t1.bottom());
    }
}
