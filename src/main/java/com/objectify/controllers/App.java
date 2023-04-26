package com.objectify.controllers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Membuat objek TabPane
        TabPane tabPane = new TabPane();

        // Membuat objek MenuBar
        MenuBar menuBar = new MenuBar();

        // Membuat objek Menu
        Menu fileMenu = new Menu("File");

        // Membuat objek MenuItem
        MenuItem newMenuItem = new MenuItem("New");
        MenuItem openMenuItem = new MenuItem("Open");

        // Menambahkan MenuItem ke Menu
        fileMenu.getItems().addAll(newMenuItem, openMenuItem);

        // Menambahkan Menu ke MenuBar
        menuBar.getMenus().add(fileMenu);

        // Membuat objek Page
        Pane page1 = new Pane();
        Pane page2 = new Pane();

        // Membuat objek Tab
        Tab tab1 = new Tab("Tab 1", page1);
        Tab tab2 = new Tab("Tab 2", page2);

        // Menambahkan Tab ke TabPane
        tabPane.getTabs().addAll(tab1, tab2);

        // Membuat objek BorderPane sebagai root node
        BorderPane root = new BorderPane();

        // Menambahkan TabPane dan MenuBar ke BorderPane
        root.setCenter(tabPane);
        root.setTop(menuBar);

        // Membuat objek Scene dengan BorderPane sebagai root node
        Scene scene = new Scene(root, 800, 600);

        // Menampilkan Scene di dalam Stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
