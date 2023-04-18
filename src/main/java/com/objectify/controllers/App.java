package com.objectify.controllers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * JavaFX App
 */
public class App extends Application {

    private Map<String, Scene> scenes = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        // Create instances of each scene with the appropriate root node and dimensions
        StackPane homeRoot = new StackPane(); // Example root node for the home page scene
        HomePage homePage = new HomePage(homeRoot, 800, 600, scenes);
        StackPane aboutRoot = new StackPane(); // Example root node for the about page scene
        AboutPage aboutPage = new AboutPage(aboutRoot, 800, 600);
        scenes.put("home", homePage);
        scenes.put("about", aboutPage);

        // Set the initial scene to the home page
        primaryStage.setScene(homePage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
