package oo.assignment10.store;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class ShoppingCart {
    private final List<Item> items;
    private PaymentMethod paymentMethod;

    public ShoppingCart(PaymentMethod paymentMethod) {
        this.items = new ArrayList<>();
        this.paymentMethod = paymentMethod;
    }

    public double totalCost() {
        double totalPrice = items.stream()
                .map(Item::getPrice)
                .reduce(Double::sum)
                .orElse(0.0);
        double totalShippingCost = items.stream()
                .map(Item::shippingCost)
                .distinct()
                .reduce(Double::sum)
                .orElse(0.0);

        return totalPrice + totalShippingCost;
    }

    public boolean checkout() {
        return paymentMethod.pay(totalCost());
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void clear() {
        items.clear();
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
}
