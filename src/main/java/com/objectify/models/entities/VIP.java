package com.objectify.models.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.objectify.models.transactions.TransactionHistory;
@JsonTypeName("vip")
public class VIP extends User{

    private String name;
    private String phoneNumber;
    private int points;

    public VIP(int userID, boolean activationStatus, TransactionHistory transactionHistory, String name, String phoneNumber, int points) {
        super(userID, activationStatus, transactionHistory);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getPoints() {
        return points;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "VIP{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", points=" + points +
                '}';
    }
}
