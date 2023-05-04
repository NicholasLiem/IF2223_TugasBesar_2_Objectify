package com.objectify.models.transactions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.objectify.models.items.ShoppingCart;

public class Transaction {
@XmlRootElement(name = "Transaction")
@XmlAccessorType(XmlAccessType.FIELD)
public class Transaction implements Serializable {
    private static final long serialVersionUID = -8082164294766414620L;
    
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

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", dateTime='" + dateTime + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                '}';
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", dateTime='" + dateTime + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                '}';
    }
}
