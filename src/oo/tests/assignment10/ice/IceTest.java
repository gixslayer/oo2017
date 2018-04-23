package oo.tests.assignment10.ice;

import oo.assignment10.ice.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class IceTest {

    @Test
    public void testVanilla() {
        Ice ice = Flavor.Vanilla;

        assertEquals(150, ice.price());
        assertEquals("Vanilla ice cream", ice.giveDescription());
    }

    @Test
    public void testYoghurt() {
        Ice ice = Flavor.Yoghurt;

        assertEquals(200, ice.price());
        assertEquals("Yoghurt ice cream", ice.giveDescription());
    }

    @Test
    public void testVanillaComposition() {
        Ice ice = new Sprinkles(new Sprinkles(new WhippedCream(new ChocolateDip(Flavor.Yoghurt))));

        assertEquals(280, ice.price());
        assertEquals("Yoghurt ice cream with chocolate dip with whipped cream with sprinkles with sprinkles", ice.giveDescription());
    }

    @Test
    public void testYoghurtComposition() {
        Ice ice = new WhippedCream(new Sprinkles(new WhippedCream(new ChocolateDip(new ChocolateDip(Flavor.Vanilla)))));

        assertEquals(310, ice.price());
        assertEquals("Vanilla ice cream with chocolate dip with chocolate dip with whipped cream with sprinkles with whipped cream", ice.giveDescription());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChocolateOnWhippedCreamDirect() {
        Ice ice = new ChocolateDip(new WhippedCream(Flavor.Vanilla));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChocolateOnWhippedCreamIndirect() {
        Ice ice = new ChocolateDip(new Sprinkles(new Sprinkles(new WhippedCream(Flavor.Yoghurt))));
    }
}
