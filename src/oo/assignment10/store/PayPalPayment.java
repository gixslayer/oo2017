package oo.assignment10.store;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class PayPalPayment implements PaymentMethod {
    private final String email;
    private final String password;
    private final int code;

    public PayPalPayment(String email, String password, int code) {
        this.email = email;
        this.password = password;
        this.code = code;
    }

    @Override
    public boolean pay(double amount) {
        System.out.printf("Payment of %f using %s\n", amount, toString());

        // Returning false for testing purposes.
        return false;
    }

    @Override
    public String toString() {
        return String.format("PayPal (email=%s password=%s code=%d)", email, password, code);
    }
}
