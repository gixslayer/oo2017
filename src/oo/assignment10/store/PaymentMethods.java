package oo.assignment10.store;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public enum PaymentMethods implements PaymentMethod {
    IDeal {
        @Override
        public boolean pay(double amount) {
            // TODO: Get bank/accountNr/pin from user?

            System.out.printf("Payment of %f using IDeal\n", amount);

            return true;
        }
    },
    CreditCard {
        @Override
        public boolean pay(double amount) {
            // TODO: Get cardNr/expDate from user?

            System.out.printf("Payment of %f using CreditCard\n", amount);

            return true;
        }
    },
    PayPal {
        @Override
        public boolean pay(double amount) {
            // TODO: Get email/password from user?

            System.out.printf("Payment of %f using PayPal\n", amount);

            return false;
        }
    };
}
