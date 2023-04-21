package com.objectify.models.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.objectify.models.transactions.TransactionHistory;

@JsonTypeName("customer")
public class Customer extends User {

    public Customer(){
        super();
    }

    public Customer(int userID, boolean activationStatus, TransactionHistory transactionHistory){
        super(userID, activationStatus, transactionHistory);
    }

    @Override
    public String toString() {
        return "Customer{}";
    }
}
