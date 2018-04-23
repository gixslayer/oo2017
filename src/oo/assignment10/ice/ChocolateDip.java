package oo.assignment10.ice;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class ChocolateDip extends Topping {

    public ChocolateDip(Ice ice) {
        super(ice, 30, " with chocolate dip");

        if(hasWhippedCreamTopping(ice)) {
            throw new IllegalArgumentException("Cannot apply chocolate dip to whipped cream topping");
        }
    }

    private static boolean hasWhippedCreamTopping(Ice ice) {
        while(ice != null) {
            if (ice instanceof WhippedCream) return true;

            ice = ice instanceof Topping ? ((Topping) ice).ice : null;
        }

        return false;
    }
}
