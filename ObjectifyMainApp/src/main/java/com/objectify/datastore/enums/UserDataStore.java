package com.objectify.datastore.enums;

import com.objectify.datastore.adapter.JSONAdapter;
import com.objectify.datastore.adapter.OBJAdapter;
import com.objectify.datastore.adapter.XMLAdapter;
import com.objectify.datastore.interfaces.DataStore;
import com.objectify.models.entities.UserManager;

import java.util.Optional;

public enum UserDataStore {
    JSON("JSON", new JSONAdapter<>("Users.json", UserManager.class)),
    OBJ("OBJ", new OBJAdapter<>("Users.obj")),
    XML("XML", new XMLAdapter<>("Users.xml", UserManager.class));


    private final DataStore<UserManager> dataStore;
    private final String name;

    UserDataStore(String name, DataStore<UserManager> dataStore) {
        this.name = name;
        this.dataStore = dataStore;
    }

    public DataStore<UserManager> getDataStore() {
        return dataStore;
    }

    public String getName() {
        return name;
    }

    public static String[] getDataStoreTypes() {
        String[] types = new String[UserDataStore.values().length];
        for (int i = 0; i < types.length; i++) {
            types[i] = UserDataStore.values()[i].name;
        }
        return types;
    }

    public static Optional<UserDataStore> fromName(String name) {
        for (UserDataStore userDataStore : UserDataStore.values()) {
            if (userDataStore.name.equals(name)) {
                return Optional.of(userDataStore);
            }
        }
        return Optional.empty();
    }
}
