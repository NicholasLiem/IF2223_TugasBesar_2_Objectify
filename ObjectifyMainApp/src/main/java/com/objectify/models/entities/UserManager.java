package com.objectify.models.entities;

import com.objectify.models.transactions.TransactionHistory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement(name = "UserManager")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserManager implements Serializable {
    private static final long serialVersionUID = 1082101602017142362L;
    @XmlElement(name = "Users")
    private ArrayList<User> listOfUsers;

    public UserManager() {
        this.listOfUsers = new ArrayList<>();
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

    public ArrayList<User> getListOfUsers(){
        return this.listOfUsers;
    }

    public void setListOfUsers(ArrayList<User> listOfUsers){
        this.listOfUsers = listOfUsers;
    }

    public void updateCustomer(int id, boolean activationStatus){
        for(User u : this.listOfUsers){
            if(u.getUserID() == id){
                u.setActivationStatus(activationStatus);
                break;
            }
        }
    }
    public User getUser(int id){
        for(User u : this.listOfUsers){
            if(u.getUserID() == id){
                return u;
            }
        }
        return null;
    }

    public void upgradeCustomer(Customer c,boolean activationStatus, String name, String phoneNumber, int points,String type){
        this.removeUser((User)c);
        if(type == "Member"){
            Member newMember = new Member(c.getUserID(),activationStatus,c.getUserTransactions(),name,phoneNumber,points);
            this.addUser(newMember);
        }
        if(type == "VIP"){
            VIP newVIP = new VIP(c.getUserID(),activationStatus,c.getUserTransactions(),name,phoneNumber,points);
            this.addUser(newVIP);
        }

    }

    public void displayUsers(){
        System.out.println("Our users : ");
        for (User u : this.listOfUsers){
            System.out.println(u.getUserID());
            System.out.println(u.getType());
            System.out.println(u.isActivationStatus());
            if(u instanceof Member){
                System.out.println(u.getUserID());
                System.out.println(((Member) u).getName() + "Member");
                System.out.println(((Member) u).getPhoneNumber());
                System.out.println(((Member) u).getPoints());
            }
            else if (u instanceof  VIP){
                System.out.println(((VIP) u).getName() + "VIP");
                System.out.println(((VIP) u).getPhoneNumber());
                System.out.println(((VIP) u).getPoints());
            }
        }
    }

}

