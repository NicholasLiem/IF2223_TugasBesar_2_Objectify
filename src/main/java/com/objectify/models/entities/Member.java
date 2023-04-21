package com.objectify.models.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.objectify.models.transactions.TransactionHistory;

@JsonTypeName("Member")
public class Member extends User{

    private String name;
    private String phoneNumber;
    private int points;

    public Member(){

    }

    public Member(int userID, boolean activationStatus, TransactionHistory transactionHistory, String name, String phoneNumber, int points) {
        super(userID, activationStatus, transactionHistory);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", points=" + points +
                '}';
    }
}
