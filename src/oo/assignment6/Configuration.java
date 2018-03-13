package oo.assignment6;

import java.util.Collection;
import java.util.List;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public interface Configuration extends Comparable<Configuration> {
    Configuration getParent();
    Collection<Configuration> getSuccessors();
    List<Configuration> getPathFromRoot();
    boolean isSolution();
}
