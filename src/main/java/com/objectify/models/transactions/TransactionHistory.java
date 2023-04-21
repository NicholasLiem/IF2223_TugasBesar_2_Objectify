package com.objectify.models.transactions;

import java.util.ArrayList;

public class TransactionHistory {
    private ArrayList<Transaction> transactions;


    public TransactionHistory(){
    }

    public ArrayList<Transaction> getTransactionHistory() {
        return transactions;
    }

    public void setTransactionHistory(ArrayList<Transaction> transactionHistory) {
        this.transactions = transactionHistory;
    }
}
