package com.objectify.models.transactions;

import java.util.ArrayList;

public class TransactionHistory {
    private ArrayList<Transaction> transactions;

    public TransactionHistory() {
        this.transactions = new ArrayList<Transaction>();
    }

    public ArrayList<Transaction> getTransactionHistory() {
        return transactions;
    }

    public void setTransactionHistory(ArrayList<Transaction> transactionHistory) {
        this.transactions = new ArrayList<Transaction>();
        this.transactions = (ArrayList<Transaction>) transactionHistory.clone();
    }

    public void add(Transaction transaction) {
        this.transactions.add(transaction);
    }
}
