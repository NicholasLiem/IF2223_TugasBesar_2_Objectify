package com.objectify.controllers.scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LandingScene extends Scene {
    public LandingScene(Stage primaryStage, MainScene mainScene) {
        super(new VBox(), 800, 600);

        VBox layout = (VBox) getRoot();
        layout.setAlignment(Pos.CENTER);

        Text welcomeText = new Text("Welcome to Objectify Manager");
        welcomeText.setFont(Font.font("Arial", 36));

        Button startButton = new Button("Start Application");
        startButton.setOnAction(e -> primaryStage.setScene(mainScene));

        layout.getChildren().addAll(welcomeText, startButton);
    }
}