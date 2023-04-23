package com.objectify.models.items;

import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Iterator;

public class ShoppingCart {
    private Map<Product, Integer> cartItems;

    public ShoppingCart() {
        this.cartItems = new HashMap<Product, Integer>();
    }

    public void addCartItem(Product product, Integer quantity) {
        cartItems.putIfAbsent(product, quantity);
    }

    public void incQuantity(Product product) {
        cartItems.replace(product, cartItems.get(product) + 1);
    }

    public void removeCartItem(Product product) {
        cartItems.remove(product);
    }

    public void decQuantity(Product product) {
        cartItems.replace(product, cartItems.get(product) - 1);
    }

    public int value() {
        Iterator<Entry<Product, Integer>> iterator = cartItems.entrySet().iterator();

        int value = 0;
        while (iterator.hasNext()) {
            Entry<Product, Integer> entry = iterator.next();
            value += entry.getValue();
        }

        return value;
    }

}

    public void addItem(Product product, Integer quantity) {
        Integer freq = map.get(product);
        map.put(product, (freq == null) ? quantity : freq + quantity);
    }
}