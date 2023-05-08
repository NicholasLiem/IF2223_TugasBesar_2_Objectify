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
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LandingScene extends Scene {
    private Text dateText;
    private Text timeText;

    public LandingScene(Stage primaryStage, MainScene mainScene) {
        super(new VBox(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());

        Path cssPath = Paths.get("ObjectifyMainApp","src", "resources", "css", "landingPage.css");
        String cssUrl = cssPath.toUri().toString();
        this.getStylesheets().add(cssUrl);
        VBox layout = (VBox) getRoot();
        layout.getStyleClass().add("landing-page");
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(30);

        Text welcomeText = new Text("Objectify Manager");
        welcomeText.setFont(Font.font("Nunito sans", FontWeight.EXTRA_BOLD, 75));
        welcomeText.getStyleClass().add("welcome-text");
        welcomeText.setFill(Color.WHITE);

        dateText = new Text();
        dateText.setFont(Font.font("Nunito sans", 30));
        dateText.getStyleClass().add("date");
        dateText.setFill(Color.WHITE);

        timeText = new Text();
        timeText.setFont(Font.font("Nunito sans", FontWeight.BOLD,20));
        timeText.setFill(Color.WHITE);

        Button startButton = new Button("Start Application");
        startButton.setFont(Font.font("Nunito sans",FontWeight.BOLD,15));
        startButton.getStyleClass().add("start-button");
        startButton.setOnAction(e -> primaryStage.setScene(mainScene));
        startButton.setPrefWidth(350);

        VBox managedBy = new VBox();
        managedBy.getStyleClass().add("author");
        Label author = new Label("Created and Managed By : ");
        Text sofyan = new Text("Moch. Sofyan - 13521083");
        Text nat = new Text("Nathania Calista - 13521139");
        Text nic = new Text("Nicholas Liem - 13521135");
        Text bitha = new Text("Tabitha Permalla - 13521111");
        Text hosea = new Text("Hosea Abednego - 13521057");
        managedBy.getChildren().addAll(author,hosea,sofyan,bitha,nic,nat);
        author.setTextFill(Color.WHITE);
        sofyan.setFill(Color.WHITE);nat.setFill(Color.WHITE);
        nic.setFill(Color.WHITE);bitha.setFill(Color.WHITE);hosea.setFill(Color.WHITE);
        managedBy.setPadding(new Insets(50,0,0,0));

        layout.getChildren().addAll(welcomeText, dateText, timeText, startButton,managedBy);
        layout.setPadding(new Insets(50,0,0,0));
        VBox.setMargin(managedBy,new Insets(70,0,0,0));
        
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
