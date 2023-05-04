package com.objectify.controllers;

import com.objectify.controllers.scenes.LandingScene;
import com.objectify.datastore.Settings;
import com.objectify.models.entities.User;
import com.objectify.models.entities.UserManager;
import com.objectify.models.items.CategoryManager;
import com.objectify.models.items.StorageManager;
import javafx.application.Application;
import com.objectify.controllers.scenes.MainScene;
import javafx.stage.Stage;

public class App extends Application {

    private Settings settings = Settings.getInstance();
    private UserManager userManager = UserManager.getInstance();
    private CategoryManager categoryManager = CategoryManager.getInstance();
    private StorageManager storageManager = StorageManager.getInstance();

    @Override
    public void start(Stage primaryStage) {
        MainScene mainScene = new MainScene();
        LandingScene landingScene = new LandingScene(primaryStage, mainScene);

        primaryStage.setScene(landingScene);
        primaryStage.setTitle("Objectify Manager");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}