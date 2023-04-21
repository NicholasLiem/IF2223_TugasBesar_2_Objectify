package com.objectify;

import com.objectify.datastore.JSONAdapter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
//        ArrayList<Orang> daftarKerabat1 = new ArrayList<>();
//        Orang Nicholas = new Orang("Nicholas Liem", "777777", 9999, daftarKerabat1);
//
//        ArrayList<Orang> daftarKerabat2 = new ArrayList<>();
//        Orang Sofyan = new Orang("Moch. Sofyan Firdaus", "7777771", 1111, daftarKerabat2);
//        Orang Juan = new Orang("Juan Christopher Santoso", "7777712", 9999, daftarKerabat2);
//
//        Nicholas.addKerabat(Sofyan);
//        Nicholas.addKerabat(Juan);
//
//        System.out.println(Nicholas);

        JSONAdapter jdatastore = JSONAdapter.getInstance();
        JSONAdapter testing = JSONAdapter.getInstance();
        System.out.println(jdatastore.hashCode() + ":" + testing.hashCode());
        ArrayList<?> data = jdatastore.readData("users.json");
        for (Object obj : data) {
            System.out.println(obj.toString());
        }
    }
}
