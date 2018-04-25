package oo.assignment10.store;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class IDealPayment implements PaymentMethod {
    private final String bank;
    private final long accountNumber;
    private final int pin;

    public IDealPayment(String bank, long accountNumber, int pin) {
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.pin = pin;
    }

    @Override
    public boolean pay(double amount) {
        System.out.printf("Payment of %f using %s\n", amount, toString());

        return true;
    }

    @Override
    public String toString() {
        return String.format("IDeal (bank=%s account=%d pin=%d)", bank, accountNumber, pin);
    }
}
