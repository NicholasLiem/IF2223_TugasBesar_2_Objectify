package com.objectify.controllers.scenes;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Box;

public class LandingScene extends Scene {
    private Text dateText;
    private Text timeText;

    public LandingScene(Stage primaryStage, MainScene mainScene) {
        super(new VBox(), 800, 600);
        Path cssPath = Paths.get("ObjectifyMainApp","src", "resources", "css", "landingPage.css");
        String cssUrl = cssPath.toUri().toString();
        this.getStylesheets().add(cssUrl);
        VBox layout = (VBox) getRoot();
        layout.getStyleClass().add("landing-page");
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(30);

        Text welcomeText = new Text("Welcome to Objectify Manager");
        welcomeText.setFont(Font.font("Nunito sans", FontWeight.BOLD, 36));
        welcomeText.getStyleClass().add("welcome-text");

        dateText = new Text();
        dateText.setFont(Font.font("Nunito sans", 18));
        dateText.getStyleClass().add("date");

        timeText = new Text();
        timeText.setFont(Font.font("Nunito sans", 18));

        Button startButton = new Button("Start Application");
        startButton.getStyleClass().add("start-button");
        startButton.setOnAction(e -> primaryStage.setScene(mainScene));

        layout.getChildren().addAll(welcomeText, dateText, timeText, startButton);

        Thread timeThread = new Thread(() -> {
            while (true) {
                DateFormat dateFormat = new SimpleDateFormat("EEE, d MMMM yyyy");
                DateFormat timeFormat = new SimpleDateFormat("h:mm a");
                Date date = new Date();
                String currentDate = dateFormat.format(date);
                String currentTime = timeFormat.format(date);
                Platform.runLater(() -> {
                    dateText.setText(currentDate);
                    timeText.setText(currentTime);
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        timeThread.setDaemon(true);
        timeThread.start();
    }
}
