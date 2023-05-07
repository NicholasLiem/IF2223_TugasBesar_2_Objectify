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
    private ShoppingCart shoppingCart;

    public Transaction() {

    }

    public Transaction(int transactionId, String dateTime, String description, double amount, ShoppingCart shoppingCart) {
        this.description = description;
        this.transactionId = transactionId;
        this.dateTime = dateTime;
        this.amount = amount;
        this.shoppingCart = shoppingCart;
    }

    public int getTransactionId() {
        return this.transactionId;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name = "ShoppingCart")
    public ShoppingCart getShoppingCart() {
        return this.shoppingCart;
    }

    public void setShoppingCart(ShoppingCart cart) {
        this.shoppingCart= cart;
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
