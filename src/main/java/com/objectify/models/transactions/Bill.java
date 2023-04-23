package com.objectify.models.transactions;

import com.objectify.models.entities.User;
import com.objectify.models.entities.VIP;
import com.objectify.models.entities.Member;
import com.objectify.models.items.ShoppingCart;
import com.objectify.models.items.Product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bill {
    private User user;
    private ShoppingCart shoppingCart;

    public Bill(User user) {
        this.user = user;
        this.shoppingCart = new ShoppingCart();
    }

    public User getUser() {
        return user;
    }

    public int value() {
        if (user instanceof VIP) {
            return Math.round((int)(0.9 * shoppingCart.value()));
        }
        return shoppingCart.value();
    }

    public void addItem(Product product, int quantity) {
        shoppingCart.addCartItem(product, quantity);
    }

    public void removeItem(Product product) {
        shoppingCart.removeCartItem(product);
    }

    public void incQuantity(Product product) {
        shoppingCart.incQuantity(product);
    }

    public void decQuantity(Product product) {
        shoppingCart.decQuantity(product);
    }

    public void save() {

    }

    public void pay(boolean usePoints) {
        
        int amount = this.value();

        if (user instanceof VIP) { // VIP user handling
            if (usePoints) {
                int vipPoints = ((VIP) user).getPoints();
                if (vipPoints >= this.value()){
                    amount = 0;
                    ((VIP) user).setPoints(vipPoints - this.value());
                } else { 
                    amount = this.value() - vipPoints;
                    ((VIP) user).setPoints(Math.round((int) (amount * 0.01)));
                }
            }
        } else if (user instanceof Member) { // Member user handling
            if (usePoints) {
                int vipPoints = ((Member) user).getPoints();
                if (vipPoints >= this.value()){
                    amount = 0;
                    ((Member) user).setPoints(vipPoints - this.value());
                } else { 
                    amount = this.value() - vipPoints;
                    ((Member) user).setPoints(Math.round((int) ((amount) * 0.01)));
                }
            }
        }

        // Update user's transaction history
        TransactionHistory history = user.getTransactionHistory();
        int count = history.getTransactionHistory().size();
        // Get current datetime
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // Create fixed bill
        Transaction newTransaction = new Transaction(count, dateTime, amount, shoppingCart);
        history.add(newTransaction);
        user.setTransactionHistory(history);
    }
}