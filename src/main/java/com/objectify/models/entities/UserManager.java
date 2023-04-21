package com.objectify.models.entities;

import com.objectify.datastore.JSONAdapter;
import com.objectify.datastore.enums.JSONType;

import java.io.IOException;
import java.util.ArrayList;

public class UserManager {

    private ArrayList<User> listOfUsers;

    public UserManager() throws IOException {
        JSONAdapter jsonAdapter = JSONAdapter.getInstance();
        this.listOfUsers = jsonAdapter.readData(JSONType.USERS.getFileName(), User.class);
    }

    public void addUser(User user){
        this.listOfUsers.add(user);
    }

    public void removeUser(User user){
        this.listOfUsers.remove(user);
    }

    public void printOutUsers(){
        User u = this.listOfUsers.get(0);
        System.out.println(u.toString());
    }
}
