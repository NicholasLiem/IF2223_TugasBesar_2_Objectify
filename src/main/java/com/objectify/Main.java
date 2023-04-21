package com.objectify;

import com.objectify.datastore.JSONAdapter;
import com.objectify.datastore.enums.JSONType;
import com.objectify.models.entities.*;
import com.objectify.models.transactions.TransactionHistory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        JSONAdapter jdatastore = JSONAdapter.getInstance();
        UserManager um = new UserManager();
        um.setListOfUsers(jdatastore.readData(JSONType.USERS.getFileName(), User.class));
        jdatastore.saveData(JSONType.USERS.getFileName(), um.getListOfUsers());
        jdatastore.deleteData();
    }
}
