package oo.tests.assignment10.store;

import oo.assignment10.store.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class ShoppingCardTest {
    private static final double DELTA = 0.00001;

    @Test
    public void priceOfEmpty() {
        ShoppingCart cart = new ShoppingCart();

        assertEquals(0.0, cart.totalCost(), DELTA);
    }

    @Test
    public void priceOfEmptyByRemoval() {
        ShoppingCart cart = new ShoppingCart();
        Item item = new WashingMachine();
        cart.addItem(item);
        cart.removeItem(item);

        assertEquals(0.0, cart.totalCost(), DELTA);
    }

    @Test
    public void priceOfWashingMachineSingle() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new WashingMachine());

        assertEquals(529, cart.totalCost(), DELTA);
    }

    @Test
    public void priceOfWashingMachineMultiple() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new WashingMachine());
        cart.addItem(new WashingMachine());

        assertEquals(1028, cart.totalCost(), DELTA);
    }

    @Test
    public void priceOfWaterMelonSingle() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new WaterMelon());

        assertEquals(11.25, cart.totalCost(), DELTA);
    }

    @Test
    public void priceOfWaterMelonMultiple() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new WaterMelon());
        cart.addItem(new WaterMelon());

        assertEquals(15.75, cart.totalCost(), DELTA);
    }

    @Test
    public void priceOfWineGlassesSingle() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new WineGlasses());

        assertEquals(15.25, cart.totalCost(), DELTA);
    }

    @Test
    public void priceOfWineGlassesMultiple() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new WineGlasses());
        cart.addItem(new WineGlasses());

        assertEquals(23.75, cart.totalCost(), DELTA);
    }

    @Test
    public void equalShippingCostIncurredOnce() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new WaterMelon());
        cart.addItem(new WineGlasses());

        assertEquals(19.75, cart.totalCost(), DELTA);
    }

    @Test
    public void largeCart() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new WashingMachine());
        cart.addItem(new WaterMelon());
        cart.addItem(new WineGlasses());
        cart.addItem(new WashingMachine());
        cart.addItem(new WashingMachine());
        cart.addItem(new WineGlasses());
        cart.addItem(new WaterMelon());
        cart.addItem(new WaterMelon());
        cart.addItem(new WaterMelon());

        assertEquals(1568.75, cart.totalCost(), DELTA);
    }

    @Test
    public void paymentDefault() {
        ShoppingCart cart = new ShoppingCart();

        assertEquals(PaymentMethods.IDeal, cart.getPaymentMethod());
    }

    @Test
    public void paymentIDeal() {
        ShoppingCart cart = new ShoppingCart();
        cart.setPaymentMethod(PaymentMethods.IDeal);
        cart.addItem(new WashingMachine());
        cart.addItem(new WaterMelon());
        cart.addItem(new WineGlasses());

        assertEquals(PaymentMethods.IDeal, cart.getPaymentMethod());
        assertTrue(cart.checkout());
    }

    @Test
    public void paymentCreditCard() {
        ShoppingCart cart = new ShoppingCart();
        cart.setPaymentMethod(PaymentMethods.CreditCard);
        cart.addItem(new WashingMachine());
        cart.addItem(new WaterMelon());
        cart.addItem(new WineGlasses());

        assertEquals(PaymentMethods.CreditCard, cart.getPaymentMethod());
        assertTrue(cart.checkout());
    }

    @Test
    public void paymentPayPal() {
        ShoppingCart cart = new ShoppingCart();
        cart.setPaymentMethod(PaymentMethods.PayPal);
        cart.addItem(new WashingMachine());
        cart.addItem(new WaterMelon());
        cart.addItem(new WineGlasses());

        assertEquals(PaymentMethods.PayPal, cart.getPaymentMethod());
        assertFalse(cart.checkout());
    }
}
