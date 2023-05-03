package com.objectify.controllers;

import com.objectify.controllers.components.menubar.MenuBarManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        TabPane tabPane = new TabPane();
        MenuBarManager mbManager = new MenuBarManager(tabPane);
        // Membuat objek TabPane


        // Membuat objek MenuBar
        MenuBar menuBar = mbManager.getMenuBar();

        // Membuat objek BorderPane sebagai root node
        BorderPane root = new BorderPane();

        // Menambahkan TabPane dan MenuBar ke BorderPane
        root.setCenter(tabPane);
        root.setTop(menuBar);

        // Membuat objek Scene dengan BorderPane sebagai root node
        Scene scene = new Scene(root, 800, 600);

        // Menampilkan Scene di dalam Stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Objectify Manager");
        //  primaryStage.getIcons().add(new Image("myIcon.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
