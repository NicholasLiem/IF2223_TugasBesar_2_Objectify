package com.objectify.models.transactions;

import java.time.LocalDateTime;

import com.objectify.models.items.ShoppingCart;

public class Transaction {
    private int transactionId;
    private String dateTime;
    private double amount;
    private ShoppingCart cart;

    public Transaction() {

    }

    public Transaction(int transactionId, String dateTime, double amount, ShoppingCart cart) {
        this.transactionId = transactionId;
        this.dateTime = dateTime;
        this.amount = amount;
        this.cart = cart;
    }

    public void save() {
        // save to database
    }

    public int getId() {
        return this.transactionId;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public double getAmount() {
        return this.amount;
    }

    public ShoppingCart getCartitems() {
        return this.cart;
    }
}
