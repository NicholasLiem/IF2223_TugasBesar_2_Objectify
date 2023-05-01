package com.objectify.models.transactions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement(name = "TransactionHistory")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@Getter @Setter
public class TransactionHistory implements Serializable {
    private static final long serialVersionUID = -3303240649720672148L;

    @XmlElement(name = "Transactions")
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public TransactionHistory(ArrayList<Transaction> transactions){
        this.transactions = transactions;
    }

    public void addTransaction(Transaction newTransaction){
        this.transactions.add(newTransaction);
    }
}
