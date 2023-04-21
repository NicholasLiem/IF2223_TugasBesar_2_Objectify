package com.objectify.models.entities;

import com.objectify.models.transactions.TransactionHistory;

public class Customer extends User {
    public Customer(int userID, boolean activationStatus, TransactionHistory transactionHistory){
        super(userID, activationStatus, transactionHistory);
    }
}
