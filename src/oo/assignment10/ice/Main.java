package oo.assignment10.ice;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Main {

    public static void main(String[] args) {
        Ice ice1 = new Sprinkles(new Sprinkles(new WhippedCream(new ChocolateDip(Flavor.Yoghurt))));
        Ice ice2 = new WhippedCream(new Sprinkles(new WhippedCream(new ChocolateDip(new ChocolateDip(Flavor.Vanilla)))));

        System.out.printf("Price: %d\nDescription: %s\n", ice1.price(), ice1.giveDescription());
        System.out.printf("Price: %d\nDescription: %s\n", ice2.price(), ice2.giveDescription());

        try {
            Ice ice3 = new ChocolateDip(new WhippedCream(Flavor.Vanilla));
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            Ice ice4 = new ChocolateDip(new Sprinkles(new Sprinkles(new WhippedCream(Flavor.Yoghurt))));
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
