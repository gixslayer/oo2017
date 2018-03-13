package oo.assignment6;

/**
 * Simple wrapper class to represent a cached value that might or might not be valid.
 *
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Cached<T> {
    private T value;
    private boolean valid;

    public Cached() {
        this.valid = false;
    }

    public Cached(T value) {
        this.value = value;
        this.valid = true;
    }

    private Cached(Cached<T> copy) {
        this.value = copy.value;
        this.valid = copy.valid;
    }

    public Cached<T> copy() {
        return new Cached<>(this);
    }

    public boolean isValid() {
        return valid;
    }

    public T getValue() {
        return value;
    }

    public void invalidate() {
        valid = false;
    }

    public void set(T value) {
        this.value = value;
        this.valid = true;
    }
}
