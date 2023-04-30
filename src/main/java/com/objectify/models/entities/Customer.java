package com.objectify.models.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.objectify.models.transactions.TransactionHistory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@JsonTypeName("Customer")
@XmlRootElement(name = "Customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer extends User implements Serializable {
    private static final long serialVersionUID = -8262644533659493562L;

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
