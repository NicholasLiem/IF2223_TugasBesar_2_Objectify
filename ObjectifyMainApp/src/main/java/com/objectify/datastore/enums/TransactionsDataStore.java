package com.objectify.datastore.enums;

import com.objectify.datastore.DataStore;
import com.objectify.datastore.JSONAdapter;
import com.objectify.datastore.OBJAdapter;
import com.objectify.datastore.XMLAdapter;
import com.objectify.models.transactions.TransactionHistory;

import java.util.Optional;

public enum TransactionsDataStore {
    JSON("JSON", new JSONAdapter<>("Transactions.json", TransactionHistory.class)),
    OBJ("OBJ", new OBJAdapter<>("Transactions.obj")),
    XML("XML", new XMLAdapter<>("Transactions.xml", TransactionHistory.class));


    private final DataStore<TransactionHistory> dataStore;
    private final String name;

    TransactionsDataStore(String name, DataStore<TransactionHistory> dataStore) {
        this.name = name;
        this.dataStore = dataStore;
    }

    public DataStore<TransactionHistory> getDataStore() {
        return dataStore;
    }

    public String getName() {
        return name;
    }

    public static String[] getDataStoreTypes() {
        String[] types = new String[TransactionsDataStore.values().length];
        for (int i = 0; i < types.length; i++) {
            types[i] = TransactionsDataStore.values()[i].name;
        }
        return types;
    }

    public static Optional<TransactionsDataStore> fromName(String name) {
        for (TransactionsDataStore transactionDataStore : TransactionsDataStore.values()) {
            if (transactionDataStore.name.equals(name)) {
                return Optional.of(transactionDataStore);
            }
        }
        return Optional.empty();
    }
}
