package com.objectify.models.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.objectify.models.transactions.TransactionHistory;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Member.class, name = "Member"),
        @JsonSubTypes.Type(value = VIP.class, name = "VIP"),
        @JsonSubTypes.Type(value = Customer.class, name = "Customer")
})
@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ Member.class, VIP.class, Customer.class })

public abstract class User implements Serializable {
    private static final long serialVersionUID = 7695914912555037086L;
    private static int totalUser = 0;
    private int userID;
    private String type;
    private boolean activationStatus;

    @XmlElement(name = "UserTransactionHistory")
    private TransactionHistory transactionHistory;

    public User(boolean activationStatus, TransactionHistory transactionHistory) {
        this.userID = totalUser + 1;
        this.activationStatus = activationStatus;
        this.transactionHistory = transactionHistory;
        totalUser++;
    }

    public User() {
        this.userID = totalUser + 1;
        this.transactionHistory = new TransactionHistory();
        totalUser++;
    }

    public User(int id, boolean activationStatus, TransactionHistory th) {
        this.userID = id;
        this.activationStatus = activationStatus;
        this.transactionHistory = th;
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

    public TransactionHistory getUserTransactions() {
        return transactionHistory;
    }

    public void setTransactionHistory(TransactionHistory transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public abstract String toString();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        return ((User) o).getUserID() == this.getUserID();
    }

    public abstract String getType();
}
