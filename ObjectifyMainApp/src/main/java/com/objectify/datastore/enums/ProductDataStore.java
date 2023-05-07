package com.objectify.datastore.enums;

import com.objectify.datastore.adapter.JSONAdapter;
import com.objectify.datastore.adapter.OBJAdapter;
import com.objectify.datastore.adapter.XMLAdapter;
import com.objectify.datastore.interfaces.DataStore;
import com.objectify.models.items.StorageManager;

import java.util.Optional;

public enum ProductDataStore {
    JSON("JSON", new JSONAdapter<>("Products.json", StorageManager.class)),
    OBJ("OBJ", new OBJAdapter<>("Products.obj")),
    XML("XML", new XMLAdapter<>("Products.xml", StorageManager.class));


    private final DataStore<StorageManager> dataStore;
    private final String name;

    ProductDataStore(String name, DataStore<StorageManager> dataStore) {
        this.name = name;
        this.dataStore = dataStore;
    }

    public DataStore<StorageManager> getDataStore() {
        return dataStore;
    }

    public String getName() {
        return name;
    }

    public static String[] getDataStoreTypes() {
        String[] types = new String[ProductDataStore.values().length];
        for (int i = 0; i < types.length; i++) {
            types[i] = ProductDataStore.values()[i].name;
        }
        return types;
    }

    public static Optional<ProductDataStore> fromName(String name) {
        for (ProductDataStore productDataStore : ProductDataStore.values()) {
            if (productDataStore.name.equals(name)) {
                return Optional.of(productDataStore);
            }
        }
        return Optional.empty();
    }
}
