package com.objectify.models.transactions;

import com.objectify.exceptions.ItemNotFoundException;
import com.objectify.models.entities.User;
import com.objectify.models.entities.UserManager;
import com.objectify.models.entities.VIP;
import com.objectify.datastore.SystemPointOfSales;
import com.objectify.models.entities.Member;
import com.objectify.models.items.ShoppingCart;
import com.objectify.models.items.StorageManager;
import com.objectify.models.items.Product;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bill implements Serializable {
    private User user;
    private ShoppingCart shoppingCart;

    public Bill(User user, ShoppingCart cart) {
        this.user = user;
        this.shoppingCart = cart;
    }

    public User getUser() {
        return user;
    }

    public ShoppingCart getCart() {
        return this.shoppingCart;
    }

    public int value() throws ItemNotFoundException {
        if (user instanceof VIP) {
            return Math.round((int) (0.9 * shoppingCart.value()));
        }
        return shoppingCart.value();
    }

    public void addItem(Integer productId, int quantity) throws ItemNotFoundException {
        shoppingCart.addCartItem(productId, quantity);
    }

    public void removeItem(Integer productId) {
        shoppingCart.removeCartItem(productId);
    }

    public void incQuantity(Integer productId) throws ItemNotFoundException {
        shoppingCart.incQuantity(productId);
    }

    public void decQuantity(Integer productIdt) {
        shoppingCart.decQuantity(productIdt);
    }

    public void pay(boolean usePoints, String description) throws ItemNotFoundException {

        int amount = this.value();
        UserManager userManager = SystemPointOfSales.getInstance().getUserManager();
        if (user instanceof VIP) { // VIP user handling
            if (usePoints) {
                int vipPoints = ((VIP) user).getPoints();
                if (vipPoints >= this.value()) {
                    amount = 0;
                    ((VIP) userManager.getUser(this.user.getUserID())).setPoints(vipPoints - this.value());
                } else {
                    amount = this.value() - vipPoints;
                    ((VIP) userManager.getUser(this.user.getUserID())).setPoints(Math.round((int) (amount * 0.01)));
                }
            }
        } else if (user instanceof Member) { // Member user handling
            if (usePoints) {
                int memberPoints = ((Member) user).getPoints();
                if (memberPoints >= this.value()) {
                    amount = 0;
                    ((Member) userManager.getUser(this.user.getUserID())).setPoints(memberPoints - this.value());
                } else {
                    amount = this.value() - memberPoints;
                    ((Member) userManager.getUser(this.user.getUserID())).setPoints(Math.round((int) (amount * 0.01)));
                }
            }
        }

        // Update user's transaction history
        TransactionHistory history = user.getTransactionHistory();
        int count = history.getTransactions().size();
        // Get current datetime
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // Create fixed bill
        Transaction newTransaction = new Transaction(count, dateTime, description, amount, shoppingCart);
        TransactionManager transactionManager = SystemPointOfSales.getInstance().getTransactionManager();
        this.shoppingCart.decQuantityStorage();
        transactionManager.addTransaction(newTransaction);
        history.addTransaction(newTransaction);
        user.setTransactionHistory(history);
        this.shoppingCart.populateProductDetails();
    }
}