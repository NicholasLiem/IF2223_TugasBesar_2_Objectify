package com.objectify.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.objectify.models.transactions.Transaction;
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

    public Customer(boolean activationStatus, TransactionHistory transactionHistory){
        super(activationStatus, transactionHistory);
    }

    public  Customer(int id, boolean activationStatus, TransactionHistory th){
        super(id,activationStatus,th);
    }

    @Override
    public String toString() {
        return "Customer{}";
    }

    @JsonIgnore

    public  String getType(){
        return "Customer";
    }
}
