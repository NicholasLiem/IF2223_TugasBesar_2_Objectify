package com.objectify.models.transactions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement(name = "TransactionHistory")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionHistory implements Serializable {
    private static final long serialVersionUID = -3303240649720672148L;

    @XmlElement(name = "Transactions")
    private ArrayList<Transaction> transactions;

    public TransactionHistory() {
        this.transactions = new ArrayList<Transaction>();
    }

    public TransactionHistory(ArrayList<Transaction> transactions){
        this.transactions = transactions;
    }

    public ArrayList<Transaction> getTransactionHistory() {
        return transactions;
    }

    public void setTransactionHistory(ArrayList<Transaction> transactionHistory) {
        this.transactions = new ArrayList<Transaction>();
        this.transactions = (ArrayList<Transaction>) transactionHistory.clone();
    }

    public void add(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public void addTransaction(Transaction newTransaction){
        this.transactions.add(newTransaction);
    }
}
