package com.objectify.models.transactions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.objectify.models.items.ShoppingCart;

import java.io.Serializable;

import com.objectify.models.items.ShoppingCart;

@XmlRootElement(name = "Transaction")
@XmlAccessorType(XmlAccessType.FIELD)
public class Transaction implements Serializable {
    private static final long serialVersionUID = -8082164294766414620L;

    private int transactionId;
    private String dateTime;
    private double amount;
    private String description;

    @XmlElement(name = "ShoppingCart")
    private ShoppingCart cart;

    public Transaction() {

    }

    public Transaction(int transactionId, String dateTime, String description, double amount, ShoppingCart cart) {
        this.description = description;
        this.transactionId = transactionId;
        this.dateTime = dateTime;
        this.amount = amount;
        this.cart = cart;
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

    @XmlElement(name = "ShoppingCart")
    public ShoppingCart getCart() {
        return this.cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", dateTime='" + dateTime + '\'' +
                ", amount=" + amount +
                '}';
    }

    public void printToPDF() {
        Thread thread = new TransactionPDFGenerator(this);
        thread.start();
    }
}
