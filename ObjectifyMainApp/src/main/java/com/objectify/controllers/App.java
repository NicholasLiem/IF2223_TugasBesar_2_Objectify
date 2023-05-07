package com.objectify.controllers;

import com.objectify.controllers.scenes.LandingScene;
import com.objectify.controllers.scenes.MainScene;
import com.objectify.datastore.SystemPointOfSales;
import com.objectify.models.items.ShoppingCart;
import com.objectify.models.transactions.Transaction;
import com.objectify.models.transactions.TransactionManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.HashMap;

public class App extends Application {

    private final MainScene mainScene = new MainScene();

    @Override
    public void start(Stage primaryStage) {
        SystemPointOfSales.getInstance().setApp(this);
        LandingScene landingScene = new LandingScene(primaryStage, mainScene);

        SystemPointOfSales.getInstance().getSettings().initialiseDataStores("JSON");
        SystemPointOfSales.getInstance().getSettings().loadAllDataStore();

        HashMap<Integer, Integer> k = new HashMap<>();
        k.put(1, 1);
        ShoppingCart sc = new ShoppingCart(k);
        Transaction t = new Transaction(1, "", "", 1, sc);
        TransactionManager ta = SystemPointOfSales.getInstance().getTransactionManager();
        ta.addTransaction(t);
        SystemPointOfSales.getInstance().getSettings().saveAllDataStore();

        primaryStage.setScene(landingScene);
        primaryStage.setTitle("Objectify Manager");
        primaryStage.show();
    }

    public MainScene getMainScene() {
        return mainScene;
    }
}