package com.objectify.models.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.objectify.models.transactions.TransactionHistory;
import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@JsonTypeName("Member")
@XmlRootElement(name = "Member")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@Getter @Setter
@ToString(callSuper = true)
public class Member extends User implements Serializable {
    private static final long serialVersionUID = 6250297092636128656L;

    private String name;
    private String phoneNumber;
    private int points;

    public Member(int userID, boolean activationStatus, TransactionHistory transactionHistory, String name, String phoneNumber, int points) {
        super(userID, activationStatus, transactionHistory);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.points = points;
    }
}
