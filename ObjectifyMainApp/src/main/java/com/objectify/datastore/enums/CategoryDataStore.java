package com.objectify.datastore.enums;

import com.objectify.datastore.adapter.JSONAdapter;
import com.objectify.datastore.adapter.OBJAdapter;
import com.objectify.datastore.adapter.XMLAdapter;
import com.objectify.datastore.interfaces.DataStore;
import com.objectify.models.items.CategoryManager;

import java.util.Optional;

public enum CategoryDataStore {
    JSON("JSON", new JSONAdapter<>("Categories.json", CategoryManager.class)),
    OBJ("OBJ", new OBJAdapter<>("Categories.obj")),
    XML("XML", new XMLAdapter<>("Categories.xml", CategoryManager.class));


    private final DataStore<CategoryManager> dataStore;
    private final String name;

    CategoryDataStore(String name, DataStore<CategoryManager> dataStore) {
        this.name = name;
        this.dataStore = dataStore;
    }

    public DataStore<CategoryManager> getDataStore() {
        return dataStore;
    }

    public String getName() {
        return name;
    }

    public static String[] getDataStoreTypes() {
        String[] types = new String[CategoryDataStore.values().length];
        for (int i = 0; i < types.length; i++) {
            types[i] = CategoryDataStore.values()[i].name;
        }
        return types;
    }

    public static Optional<CategoryDataStore> fromName(String name) {
        for (CategoryDataStore categoryDataStore : CategoryDataStore.values()) {
            if (categoryDataStore.name.equals(name)) {
                return Optional.of(categoryDataStore);
            }
        }
        return Optional.empty();
    }
}
