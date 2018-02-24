package oo.assignment3;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class GeometricCollection {
    public static final int MAX_ELEMENTS = 10;

    private final Geometric[] elements;
    private int numElements;

    public GeometricCollection() {
        elements = new Geometric[MAX_ELEMENTS];
        numElements = 0;
    }

    public boolean canAdd() {
        return numElements < MAX_ELEMENTS;
    }

    public void add(Geometric geometric) {
        elements[numElements++] = geometric;
    }

    public Geometric[] getElements() {
        return elements;
    }

    public int getNumElements() {
        return numElements;
    }

    /**
     * Get the element at the specified index, if one exists.
     * @param index The index of the element
     * @return The element, if it exists
     */
    public Optional<Geometric> get(int index) {
        return index >= 0 && index < numElements ? Optional.of(elements[index]) : Optional.empty();
    }

    /**
     * Remove the element at the specified index, if one exists.
     * @param index The index of the element
     */
    public void remove(int index) {
        if(index >= 0 && index < numElements) {
            // Shift all elements following the element at index one position to the left
            System.arraycopy(elements, index + 1, elements, index, numElements - index - 1);
            --numElements;
        }
    }

    /**
     * Sort the collection based on the given order, in an ascending manner.
     * @param order The order to sort in
     */
    public void sort(SortOrder order) {
        if(order == SortOrder.Area) {
            Arrays.sort(elements, 0, numElements);
        } else if(order == SortOrder.Bottom) {
            Arrays.sort(elements, 0, numElements, new GeometricBottomComparator());
        } else if(order == SortOrder.Left) {
            Arrays.sort(elements, 0, numElements, new GeometricLeftComparator());
        }
    }
}
