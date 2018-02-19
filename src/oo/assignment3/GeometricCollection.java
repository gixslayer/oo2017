package oo.assignment3;

import java.util.Optional;

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

    public Optional<Geometric> get(int index) {
        Optional<Geometric> result = Optional.empty();

        if(index >= 0 && index < numElements) {
            result = Optional.of(elements[index]);
        }

        return result;
    }

    public void remove(int index) {
        if(index >= 0 && index < numElements) {
            System.arraycopy(elements, index + 1, elements, index, numElements - index - 1);
            --numElements;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < numElements; ++i) {
            sb.append(elements[i]).append('\n');
        }

        return sb.toString();
    }
}
