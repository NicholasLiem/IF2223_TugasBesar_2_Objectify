package com.objectify;

import com.objectify.controllers.App;
import com.objectify.datastore.SystemPointOfSales;
import javafx.application.Application;

public class Main {
    public static void main(String[] args){
        Application.launch(App.class, args);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Saving all data stores...");
                SystemPointOfSales.getInstance().getSettings().saveAllDataStore();
            }
        }));
    }
}