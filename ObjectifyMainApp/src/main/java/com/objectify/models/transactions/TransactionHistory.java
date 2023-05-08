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
        this.transactions = new ArrayList<>();
    }

    public TransactionHistory(ArrayList<Transaction> transactions){

        this.transactions = transactions;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactionHistory(ArrayList<Transaction> transactionHistory) {
        this.transactions = transactionHistory;
    }
    public void addTransaction(Transaction newTransaction){
        this.transactions.add(newTransaction);
    }

    public void printToPDF(String filename) {
        Thread thread = new TransactionHistoryPDFGenerator(this, filename);
        thread.start();
    }
}
