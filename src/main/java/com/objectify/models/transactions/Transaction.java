package com.objectify.models.transactions;

import java.time.LocalDateTime;

public class Transaction {
    private int transactionId;
    private String dateTime;
    private String description;
    private double amount;

    public Transaction(){

    }
    public Transaction(int transactionId, String dateTime, String description, double amount) {
        this.transactionId = transactionId;
        this.dateTime = dateTime;
        this.description = description;
        this.amount = amount;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
