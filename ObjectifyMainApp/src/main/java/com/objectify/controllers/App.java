package com.objectify.controllers;

import com.objectify.controllers.scenes.LandingScene;
import com.objectify.controllers.scenes.MainScene;
import com.objectify.datastore.SystemPointOfSales;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    private final MainScene mainScene = new MainScene();

    @Override
    public void start(Stage primaryStage) {
        SystemPointOfSales.getInstance().setApp(this);
        LandingScene landingScene = new LandingScene(primaryStage, mainScene);

        SystemPointOfSales.getInstance().getSettings().initialiseDataStores("XML");
        SystemPointOfSales.getInstance().getSettings().loadAllDataStore();

        primaryStage.setScene(landingScene);
        primaryStage.setTitle("Objectify Manager");
        primaryStage.show();
    }

    public MainScene getMainScene() {
        return mainScene;
    }
}