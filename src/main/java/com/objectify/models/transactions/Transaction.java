package com.objectify.models.transactions;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "Transaction")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class Transaction implements Serializable {
    private static final long serialVersionUID = -8082164294766414620L;
    
    private int transactionId;
    private String dateTime;
    private String description;
    private double amount;
    
}
