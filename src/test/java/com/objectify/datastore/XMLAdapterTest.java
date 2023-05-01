package com.objectify.datastore;

import com.objectify.models.transactions.Transaction;
import com.objectify.models.transactions.TransactionHistory;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class XMLAdapterTest {

    private final TransactionHistory transactionHistory = new TransactionHistory();

    {
        transactionHistory.addTransaction(new Transaction());
        transactionHistory.addTransaction(new Transaction());
        transactionHistory.addTransaction(new Transaction());
    }

    private final XMLAdapter<TransactionHistory> dataStore = new XMLAdapter<>("test.xml", TransactionHistory.class);

    @Test
    @Order(1)
    void write() {
        dataStore.write(transactionHistory);

        try {
            String contents = new String(Files.readAllBytes(dataStore.getXmlPath()));
            assertTrue(contents.contains("Transactions"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(2)
    void read() throws InterruptedException {
        Thread.sleep(100);
        TransactionHistory writtenUserManager = dataStore.read().orElse(new TransactionHistory());
        assertEquals(transactionHistory.getTransactions().size(), writtenUserManager.getTransactions().size());
        for (int i = 0; i < transactionHistory.getTransactions().size(); i++) {
            String expected = transactionHistory.getTransactions().get(i).toString();
            String actual = writtenUserManager.getTransactions().get(i).toString();
            assertEquals(expected, actual);
        }
    }

    @Test
    void delete() {
    }
}