package oo.assignment10.ice;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public abstract class Topping implements Ice {
    protected final Ice ice;
    private final int price;
    private final String description;

    public Topping(Ice ice, int price, String description) {
        this.ice = ice;
        this.price = price;
        this.description = description;
    }

    @Override
    public int price() {
        return ice.price() + price;
    }

    @Override
    public String giveDescription() {
        return ice.giveDescription() + description;
    }
}
