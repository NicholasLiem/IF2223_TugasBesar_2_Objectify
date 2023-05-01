package com.objectify;

import com.objectify.models.entities.Customer;
import com.objectify.models.entities.VIP;
import com.objectify.models.transactions.TransactionHistory;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        VIP user = new VIP(1, true, new TransactionHistory(), "abc", "0800xxx", 0);
        System.out.println(user);
    }
}
