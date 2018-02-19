package oo.assignment3;

import java.util.Comparator;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class GeometricBottomComparator implements Comparator<Geometric> {
    @Override
    public int compare(Geometric geometric, Geometric t1) {
        return Double.compare(geometric.bottom(), t1.bottom());
    }
}
