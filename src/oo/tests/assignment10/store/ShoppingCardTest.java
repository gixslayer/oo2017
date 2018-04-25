package oo.tests.assignment10.store;

import oo.assignment10.store.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class ShoppingCardTest {
    private static final double DELTA = 0.00001;
    private static final PaymentMethod IDEAL = new IDealPayment("ING", 12345678, 1234);
    private static final PaymentMethod PAYPAL = new PayPalPayment("john@doe.com", "hunter2", 9999);
    private static final PaymentMethod CREDITCARD = new CreditCardPayment("jane doe", "01-01-2024", 987654321, 456);
    private static final PaymentMethod DEFAULT_PAYMENT_METHOD = IDEAL;

    private static ShoppingCart cart;

    @BeforeClass
    public static void createCart() {
        cart = new ShoppingCart(DEFAULT_PAYMENT_METHOD);
    }

    @Before
    public void resetCart() {
        cart.clear();
        cart.setPaymentMethod(DEFAULT_PAYMENT_METHOD);
    }

    @Test
    public void priceOfEmpty() {
        assertEquals(0.0, cart.totalCost(), DELTA);
    }

    @Test
    public void priceOfEmptyByRemoval() {
        Item item = new WashingMachine();
        cart.addItem(item);
        cart.removeItem(item);

        assertEquals(0.0, cart.totalCost(), DELTA);
    }

    @Test
    public void priceOfWashingMachineSingle() {
        cart.addItem(new WashingMachine());

        assertEquals(529, cart.totalCost(), DELTA);
    }

    @Test
    public void priceOfWashingMachineMultiple() {
        cart.addItem(new WashingMachine());
        cart.addItem(new WashingMachine());

        assertEquals(1028, cart.totalCost(), DELTA);
    }

    @Test
    public void priceOfWaterMelonSingle() {
        cart.addItem(new WaterMelon());

        assertEquals(11.25, cart.totalCost(), DELTA);
    }

    @Test
    public void priceOfWaterMelonMultiple() {
        cart.addItem(new WaterMelon());
        cart.addItem(new WaterMelon());

        assertEquals(15.75, cart.totalCost(), DELTA);
    }

    @Test
    public void priceOfWineGlassesSingle() {
        cart.addItem(new WineGlasses());

        assertEquals(15.25, cart.totalCost(), DELTA);
    }

    @Test
    public void priceOfWineGlassesMultiple() {
        cart.addItem(new WineGlasses());
        cart.addItem(new WineGlasses());

        assertEquals(23.75, cart.totalCost(), DELTA);
    }

    @Test
    public void equalShippingCostIncurredOnce() {
        cart.addItem(new WaterMelon());
        cart.addItem(new WineGlasses());

        assertEquals(19.75, cart.totalCost(), DELTA);
    }

    @Test
    public void largeCart() {
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
        assertEquals(IDEAL, cart.getPaymentMethod());
    }

    @Test
    public void paymentIDeal() {
        cart.setPaymentMethod(IDEAL);
        cart.addItem(new WashingMachine());
        cart.addItem(new WaterMelon());
        cart.addItem(new WineGlasses());

        assertEquals(IDEAL, cart.getPaymentMethod());
        assertTrue(cart.checkout());
    }

    @Test
    public void paymentCreditCard() {
        cart.setPaymentMethod(CREDITCARD);
        cart.addItem(new WashingMachine());
        cart.addItem(new WaterMelon());
        cart.addItem(new WineGlasses());

        assertEquals(CREDITCARD, cart.getPaymentMethod());
        assertTrue(cart.checkout());
    }

    @Test
    public void paymentPayPal() {
        cart.setPaymentMethod(PAYPAL);
        cart.addItem(new WashingMachine());
        cart.addItem(new WaterMelon());
        cart.addItem(new WineGlasses());

        assertEquals(PAYPAL, cart.getPaymentMethod());
        assertFalse(cart.checkout());
    }
}
