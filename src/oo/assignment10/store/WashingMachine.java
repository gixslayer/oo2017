package oo.assignment10.store;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class WashingMachine extends Item {

    public WashingMachine() {
        super("A washing machine", 499);
    }

    @Override
    public double shippingCost() {
        return 30;
    }
}
