package com.objectify.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.objectify.models.transactions.TransactionHistory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@JsonTypeName("VIP")
@XmlRootElement(name = "VIP")
@XmlAccessorType(XmlAccessType.FIELD)
public class VIP extends User implements Serializable {
    private static final long serialVersionUID = -7078300799080787479L;

    private String name;
    private String phoneNumber;
    private int points;

    public VIP() {
        super();
    }

    public VIP(boolean activationStatus, TransactionHistory transactionHistory, String name, String phoneNumber, int points) {
        super(activationStatus, transactionHistory);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.points = points;
    }

    public VIP(int id, boolean activationStatus, TransactionHistory th, String name, String phoneNumber, int points){
        super(id,activationStatus,th);
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

    @JsonIgnore
    public String getType(){
        return "VIP";
    }
}
