package com.objectify.models.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.objectify.models.transactions.TransactionHistory;

import javax.xml.bind.annotation.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Member.class, name = "Member"),
        @JsonSubTypes.Type(value = VIP.class, name = "VIP"),
        @JsonSubTypes.Type(value = Customer.class, name = "Customer")
})
@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Member.class, VIP.class, Customer.class})
public abstract class User {
    private int userID;
    private String type;
    private boolean activationStatus;

    @XmlElement(name = "UserTransactionHistory")
    private TransactionHistory transactionHistory;
    public User(int userID, boolean activationStatus, TransactionHistory transactionHistory) {
        this.userID = userID;
        this.activationStatus = activationStatus;
        this.transactionHistory = transactionHistory;
    }

    public User() {

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

    public abstract String toString();
}
