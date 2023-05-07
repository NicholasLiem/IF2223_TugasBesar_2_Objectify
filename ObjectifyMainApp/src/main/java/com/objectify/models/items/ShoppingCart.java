package com.objectify.models.items;

import com.objectify.datastore.SystemPointOfSales;
import com.objectify.exceptions.ItemNotFoundException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Iterator;

@XmlRootElement(name = "ShoppingCart")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShoppingCart implements Serializable {

    private StorageManager sm = SystemPointOfSales.getInstance().getStorageManager();
    private Map<Integer, Integer> cartItems;

    public ShoppingCart() {
        this.cartItems = new HashMap<>();
    }

    public ShoppingCart(Map<Integer, Integer> cart) {
        this.cartItems = cart;
    }

    public Map<Integer, Integer> getItems() {
        return this.cartItems;
    }

    public void addCartItem(Integer productId, Integer quantity) {
        cartItems.putIfAbsent(productId, quantity);
    }

    public void incQuantity(Integer productId) {
        cartItems.replace(productId, cartItems.get(productId) + 1);
    }

    public void removeCartItem(Integer productId) {
        cartItems.remove(productId);
    }

    public void decQuantity(Integer productId) {
        cartItems.replace(productId, cartItems.get(productId) - 1);
    }

    public int value() throws ItemNotFoundException {
        Iterator<Entry<Integer, Integer>> iterator = cartItems.entrySet().iterator();

        int value = 0;
        while (iterator.hasNext()) {
            Entry<Integer, Integer> entry = iterator.next();
            value += entry.getValue() * sm.searchById(entry.getKey()).getProductPrice();
        }

        return value;
    }

    public void decQuantityStorage() {
        for (Entry<Integer, Integer> entry : this.cartItems.entrySet()) {
            try {
                sm.searchById(entry.getKey())
                        .setProductStock(sm.searchById(entry.getKey()).getProductStock() - entry.getValue());
            } catch (ItemNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Entry<Integer, Integer> entry : cartItems.entrySet()) {
            sb.append(entry.getValue());
            sb.append(" ");
            try {
                sb.append(sm.searchById(entry.getKey()).getProductName());
            } catch (ItemNotFoundException e) {
                throw new RuntimeException(e);
            }
            sb.append(" ");
            try {
                sb.append(sm.searchById(entry.getKey()).getProductPrice());
            } catch (ItemNotFoundException e) {
                throw new RuntimeException(e);
            }
            sb.append(" ");
            try {
                sb.append(sm.searchById(entry.getKey()).getProductPrice() * entry.getValue());
            } catch (ItemNotFoundException e) {
                throw new RuntimeException(e);
            }
            sb.append("\n");
        }
        try {
            sb.append("Subtotal: " + value() + "\n");
        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}
