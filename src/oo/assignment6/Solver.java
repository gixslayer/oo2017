package oo.assignment6;

import java.util.*;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Solver {
    private final Queue<Configuration> toExamine;
    private final Set<Configuration> recurrences;

    public Solver(Configuration initialConfiguration) {
        this.toExamine = new PriorityQueue<>();
        //this.toExamine = new LinkedList<>();
        this.toExamine.add(initialConfiguration);
        this.recurrences = new HashSet<>();
        this.recurrences.add(initialConfiguration);
    }

    public Optional<Configuration> solve() {
        while(!toExamine.isEmpty()) {
            Configuration next = toExamine.remove();

            if(next.isSolution()) {
                return Optional.of(next);
            } else {
                for(Configuration configuration : next.getSuccessors()) {
                    // Filter out configurations we've already seen.
                    if(!recurrences.contains(configuration)) {
                        toExamine.add(configuration);
                        recurrences.add(configuration);
                    }
                }
            }
        }

        return Optional.empty();
    }

}