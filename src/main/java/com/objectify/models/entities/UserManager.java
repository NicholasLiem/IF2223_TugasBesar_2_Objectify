package com.objectify.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement(name = "UserManager")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor @Getter @Setter
public class UserManager implements Serializable {
    private static final long serialVersionUID = 1082101602017142362L;

    @XmlElement(name = "Users")
    private ArrayList<User> listOfUsers = new ArrayList<>();

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
