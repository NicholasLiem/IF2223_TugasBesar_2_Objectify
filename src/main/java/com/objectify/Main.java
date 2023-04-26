package com.objectify;

import com.objectify.datastore.JSONAdapter;
import com.objectify.datastore.XMLAdapter;
import com.objectify.datastore.enums.XMLType;
import com.objectify.models.entities.*;
import com.objectify.models.transactions.Transaction;
import com.objectify.models.transactions.TransactionHistory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, JAXBException {
//        ArrayList<User> expectedUsers = new ArrayList<>();
//        expectedUsers.add(new Member(1001, true, null, "Lala", "0812", 1000));
//        expectedUsers.add(new VIP(1002, false, null, "Lili", "0813", 500));
//        expectedUsers.add(new Customer(1003, true, null));
        XMLAdapter xmlAdapter = XMLAdapter.getInstance();
//        xmlAdapter.writeData(XMLType.USERS.getFileName(), expectedUsers);

        ArrayList<User> actualUsers = xmlAdapter.readData(XMLType.USERS.getFileName(), User.class);
        for(User u: actualUsers){
            System.out.println(u.getClass());
        }
    }

}
