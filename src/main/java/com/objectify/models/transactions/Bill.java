package com.objectify.models.transactions;

import com.objectify.models.entities.User;
import com.objectify.models.entities.VIP;
import com.objectify.models.entities.Member;
import com.objectify.models.items.ShoppingCart;
import com.objectify.models.items.Product;

public class Bill {
    private User user;
    private ShoppingCart shoppingCart;

    public Bill(User user) {
        this.user = user;
        this.shoppingCart = new ShoppingCart();
    }

    public int value() {
        if (user instanceof VIP) {
            return Math.round(0.9 * shoppingCart.value());
        }
        return shoppingCart.value();
    }

    public void addItem(Product product, int quantity) {
        shoppingCart.addItem(product, quantity);
    }

    public void save() {

    }

    public void pay(boolean usePoints) {
        if (user instanceof VIP) {
            if (usePoints) {
                int vipPoints = ((VIP) user).getPoints();
                if (vipPoints >= this.value()){
                    ((VIP) user).setPoints(vipPoints - this.value());
                } else { 
                    ((VIP) user).setPoints(Math.round((int) ((this.value() - vipPoints) * 0.01)));
                }
            }
        } else if (user instanceof Member) {
            if (usePoints) {
                int vipPoints = ((Member) user).getPoints();
                if (vipPoints >= this.value()){
                    ((Member) user).setPoints(vipPoints - this.value());
                } else { 
                    ((Member) user).setPoints(Math.round((int) ((this.value() - vipPoints) * 0.01)));
                }
            }
        }
    }
}