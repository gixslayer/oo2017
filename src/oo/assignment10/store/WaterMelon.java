package oo.assignment10.store;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class WaterMelon extends Item {

    public WaterMelon() {
        super("A water melon", 4.5);
    }

    @Override
    public double shippingCost() {
        return 6.75;
    }
}
