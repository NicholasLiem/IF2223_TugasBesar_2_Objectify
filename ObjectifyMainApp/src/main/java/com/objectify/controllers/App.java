package com.objectify.controllers;

import com.objectify.controllers.scenes.LandingScene;
import com.objectify.controllers.scenes.MainScene;
import com.objectify.datastore.SystemPointOfSales;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.nio.file.Paths;

public class App extends Application {

    private final MainScene mainScene = new MainScene();

    @Override
    public void start(Stage primaryStage) {
        SystemPointOfSales.getInstance().setApp(this);
        LandingScene landingScene = new LandingScene(primaryStage, mainScene);

        Path resPath = Paths.get("ObjectifyMainApp", "resources", "images", "bmo.png");
        primaryStage.getIcons().add(new Image(resPath.toUri().toString()));

        primaryStage.setScene(landingScene);
        primaryStage.setTitle("Objectify Manager");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public MainScene getMainScene() {
        return mainScene;
    }
}