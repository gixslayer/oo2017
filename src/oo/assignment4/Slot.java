package oo.assignment4;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public enum Slot {
    Empty,
    Cross,
    Circle;

    /**
     * Returns the mark in this slot.
     * @return The mark in the slot, or null if the slot is empty.
     */
    public Mark getMark() {
        switch(this) {
            case Cross:
                return Mark.Cross;
            case Circle:
                return Mark.Circle;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case Empty:
                return " ";
            case Cross:
                return "x";
            case Circle:
                return "o";
            default:
                return this.name();
        }
    }
}
