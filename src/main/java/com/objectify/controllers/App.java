package com.objectify.controllers;

import com.objectify.controllers.scenes.LandingScene;
import javafx.application.Application;
import com.objectify.controllers.scenes.MainScene;
import javafx.stage.Stage;

public class App extends Application {

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