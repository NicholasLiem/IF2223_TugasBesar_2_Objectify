package com.objectify.models.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.objectify.models.transactions.TransactionHistory;
import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@JsonTypeName("VIP")
@XmlRootElement(name = "VIP")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@ToString(callSuper = true)
@Getter @Setter
public class VIP extends User implements Serializable {
    private static final long serialVersionUID = -7078300799080787479L;

    private String name;
    private String phoneNumber;
    private int points;

    public VIP(int userID, boolean activationStatus, TransactionHistory transactionHistory, String name, String phoneNumber, int points) {
        super(userID, activationStatus, transactionHistory);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.points = points;
    }
}
