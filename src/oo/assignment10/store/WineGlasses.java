package oo.assignment10.store;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class WineGlasses extends Item {

    public WineGlasses() {
        super("Wine glasses", 8.5);
    }

    @Override
    public double shippingCost() {
        return 6.75;
    }
}
