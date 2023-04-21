package com.objectify.models.entities;

import java.util.ArrayList;

public class UserManager {

    private ArrayList<User> listOfUsers;

    public UserManager(){
        this.listOfUsers = new ArrayList<>();
    }

    public void addUser(User user){
        this.listOfUsers.add(user);
    }

    public void removeUser(User user){
        this.listOfUsers.remove(user);
    }
}
