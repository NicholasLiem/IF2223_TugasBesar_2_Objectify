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

    public ShoppingCart(Map<Product, Integer> cart) {
        this.cartItems = cart;
    }

    public Map<Product, Integer> getItems() {
        return this.cartItems;
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
            value += entry.getKey().getProductPrice() * entry.getValue();
        }

        return value;
    }

    public Map<Product, Integer> getItems() {
        return cartItems;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Entry<Product, Integer> entry : cartItems.entrySet()) {
            sb.append(entry.getValue());
            sb.append(" ");
            sb.append(entry.getKey().getProductName());
            sb.append(" ");
            sb.append(entry.getKey().getProductPrice());
            sb.append(" ");
            sb.append(entry.getKey().getProductPrice() * entry.getValue());
            sb.append("\n");
        }
        sb.append("Subtotal: " + value() + "\n");
        return sb.toString();
    }
}
