package com.objectify.models.transactions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "TransactionManager")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionManager {
    @XmlElement(name = "Transactions")
    private ArrayList<Transaction> listOfTransactions;

    public TransactionManager() {
        this.listOfTransactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction){
        this.listOfTransactions.add(transaction);
    }

    public void removeTransaction(Transaction transaction){
        this.listOfTransactions.remove(transaction);
    }

    public void printOutUsers(){
    }

    public ArrayList<Transaction> getListOfTransaction(){
        return this.listOfTransactions;
    }

    public void setListOfTransactions(ArrayList<Transaction> listOfTransactions){
        this.listOfTransactions = listOfTransactions;
    }
}
