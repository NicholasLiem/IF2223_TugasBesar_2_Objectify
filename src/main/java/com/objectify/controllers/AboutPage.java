package com.objectify.controllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AboutPage extends Scene {

    public AboutPage(Parent root, double width, double height) {
        super(root, width, height);

        // Create the content of the about page scene
        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(20);
        content.setPadding(new Insets(40));

        Label titleLabel = new Label("About This Application");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label descLabel = new Label("This is a simple JavaFX application that demonstrates "
                + "how to create and navigate between different scenes.");

        Label authorLabel = new Label("Created by: Your Name");

        content.getChildren().addAll(titleLabel, descLabel, authorLabel);
        ((StackPane) root).getChildren().add(content);
    }
}