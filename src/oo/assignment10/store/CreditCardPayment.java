package oo.assignment10.store;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class CreditCardPayment implements PaymentMethod {
    private final String name;
    private final String date;
    private final int number;
    private final int cvv;

    public CreditCardPayment(String name, String date, int number, int cvv) {
        this.name = name;
        this.date = date;
        this.number = number;
        this.cvv = cvv;
    }

    @Override
    public boolean pay(double amount) {
        System.out.printf("Payment of %f using %s\n", amount, toString());

        return true;
    }

    @Override
    public String toString() {
        return String.format("CreditCard (name=%s date=%s number=%d cvv=%d)", name, date, number, cvv);
    }
}
