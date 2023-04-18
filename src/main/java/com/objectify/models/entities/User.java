package com.objectify.models.entities;

import com.objectify.models.transactions.TransactionHistory;

public abstract class User {
    private int userID;
    private boolean activationStatus;
    private TransactionHistory transactionHistory;

    public User(int userID, boolean activationStatus, TransactionHistory transactionHistory) {
        this.userID = userID;
        this.activationStatus = activationStatus;
        this.transactionHistory = transactionHistory;
    }

    public int getUserID() {
        return userID;
    }

    public boolean isActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(boolean activationStatus) {
        this.activationStatus = activationStatus;
    }

    public TransactionHistory getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(TransactionHistory transactionHistory) {
        this.transactionHistory = transactionHistory;
    }
}
