package com.objectify.controllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Map;

public class HomePage extends Scene {

    private Map<String, Scene> scenes;

    public HomePage(Parent root, double width, double height, Map<String, Scene> scenes) {
        super(root, width, height);
        this.scenes = scenes;

        // Create the content of the home page scene
        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(20);
        content.setPadding(new Insets(40));

        Label titleLabel = new Label("Welcome to My Application");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label descLabel = new Label("This is a simple JavaFX application that demonstrates "
                + "how to create and navigate between different scenes.");

        Button aboutButton = new Button("About");
        aboutButton.setOnAction(event -> {
            // Switch to the about page scene
            Scene aboutPage = scenes.get("about");
            if (aboutPage != null) {
                ((StackPane) getRoot()).getChildren().set(0, aboutPage.getRoot());
            }
        });

        content.getChildren().addAll(titleLabel, descLabel, aboutButton);
        ((StackPane) root).getChildren().add(content);
    }
}
