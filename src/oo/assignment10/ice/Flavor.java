package oo.assignment10.ice;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public enum Flavor implements Ice {
    Vanilla(150, "Vanilla ice cream"),
    Yoghurt(200, "Yoghurt ice cream");

    Flavor(int price, String description) {
        this.price = price;
        this.description = description;
    }

    private final int price;
    private final String description;

    @Override
    public int price() {
        return price;
    }

    @Override
    public String giveDescription() {
        return description;
    }
}
