package com.objectify.datastore.enums;

import com.objectify.datastore.adapter.JSONAdapter;
import com.objectify.datastore.adapter.OBJAdapter;
import com.objectify.datastore.adapter.XMLAdapter;
import com.objectify.datastore.interfaces.DataStore;
import com.objectify.models.transactions.TransactionManager;

import java.util.Optional;

public enum TransactionsDataStore {
    JSON("JSON", new JSONAdapter<>("Transactions.json", TransactionManager.class)),
    OBJ("OBJ", new OBJAdapter<>("Transactions.obj")),
    XML("XML", new XMLAdapter<>("Transactions.xml", TransactionManager.class));


    private final DataStore<TransactionManager> dataStore;
    private final String name;

    TransactionsDataStore(String name, DataStore<TransactionManager> dataStore) {
        this.name = name;
        this.dataStore = dataStore;
    }

    public DataStore<TransactionManager> getDataStore() {
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
