package com.objectify.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.objectify.models.transactions.TransactionHistory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@JsonTypeName("Member")
@XmlRootElement(name = "Member")
@XmlAccessorType(XmlAccessType.FIELD)
public class Member extends User implements Serializable {
    private static final long serialVersionUID = 6250297092636128656L;

    private String name;
    private String phoneNumber;
    private int points;

    public Member(){
        super();
    }

    public Member(boolean activationStatus, TransactionHistory transactionHistory, String name, String phoneNumber, int points) {
        super(activationStatus, transactionHistory);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.points = points;
    }

    public  Member(int id, boolean activationStatus, TransactionHistory th, String name, String phoneNumber, int points){
        super(id,activationStatus,th);
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

    @JsonIgnore
    public String getType(){
        return "Member";
    }
}
