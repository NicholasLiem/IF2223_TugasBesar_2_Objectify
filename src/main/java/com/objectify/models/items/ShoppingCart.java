package com.objectify.models.items;

import java.util.*;                                                                                                                                                                                                                                                                                                          v

public class ShoppingCart {
    private Map<Product,Integer> map;

    public ShoppingCart() {
        this.map = new HashMap<>();
    }

    public void addItem(Product product, Integer quantity){
        Integer freq = map.get(product);
        map.put(product, (freq == null) ? quantity : freq + quantity);
    }
}