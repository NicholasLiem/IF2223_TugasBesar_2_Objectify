package com.objectify.models.entities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.objectify.datastore.JSONAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class UserManager {

    private ArrayList<User> listOfUsers;

    public UserManager() throws IOException {
        JSONAdapter jsonAdapter = JSONAdapter.getInstance();
        this.listOfUsers = jsonAdapter.readData("users.json", User.class);
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
