package com.objectify.models.items;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.objectify.datastore.SystemPointOfSales;
import com.objectify.exceptions.ItemNotFoundException;
import com.objectify.models.transactions.DataContainer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

@XmlRootElement(name = "ShoppingCart")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShoppingCart implements Serializable {
    @JsonIgnore
    private Map<Integer, Integer> cartItems;
    @XmlElement(name = "DataContainers")
    private ArrayList<DataContainer> dataContainer;
    public ShoppingCart() {
        this.cartItems = new HashMap<>();
        this.dataContainer = new ArrayList<>();
    }

    public ShoppingCart(Map<Integer, Integer> cart) {
        this.cartItems = cart;
        this.dataContainer = new ArrayList<>();
    }

    public Map<Integer, Integer> getCartItems() {
        return this.cartItems;
    }

    public void setCartItems(Map<Integer, Integer> cartItems) {
        this.cartItems = cartItems;
    }


    public ArrayList<DataContainer> getDataContainer() {
        return dataContainer;
    }

    public void setDataContainer(ArrayList<DataContainer> dataContainer) {
        this.dataContainer = dataContainer;
    }

    public void addCartItem(Integer productId, Integer quantity) {
        cartItems.putIfAbsent(productId, quantity);
    }

    public void setCartItem(Map<Integer, Integer> cartItems){
        this.cartItems = cartItems;
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
            value += entry.getValue() * SystemPointOfSales.getInstance().getStorageManager().searchById(entry.getKey()).getProductPrice();
        }

        return value;
    }

    public void decQuantityStorage() {
        for (Entry<Integer, Integer> entry : this.cartItems.entrySet()) {
            try {
                SystemPointOfSales.getInstance().getStorageManager().searchById(entry.getKey())
                        .setProductStock(SystemPointOfSales.getInstance().getStorageManager().searchById(entry.getKey()).getProductStock() - entry.getValue());
            } catch (ItemNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Entry<Integer, Integer> entry : cartItems.entrySet()) {
            sb.append(" ");
            try {
                sb.append(SystemPointOfSales.getInstance().getStorageManager().searchById(entry.getKey()).getProductName());
            } catch (ItemNotFoundException e) {
                throw new RuntimeException(e);
            }
            sb.append(" ");
            try {
                sb.append(SystemPointOfSales.getInstance().getStorageManager().searchById(entry.getKey()).getProductPrice());
            } catch (ItemNotFoundException e) {
                throw new RuntimeException(e);
            }
            sb.append(" ");
            try {
                sb.append(SystemPointOfSales.getInstance().getStorageManager().searchById(entry.getKey()).getProductPrice() * entry.getValue());
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

    public void populateProductDetails() throws ItemNotFoundException {
        for (Map.Entry<Integer, Integer> entry : this.getCartItems().entrySet()){
            this.dataContainer.add(new DataContainer(SystemPointOfSales.getInstance().getStorageManager().searchById(entry.getKey()).getProductName(), SystemPointOfSales.getInstance().getStorageManager().searchById(entry.getKey()).getProductPrice(), entry.getValue()));
        }
    }
}
