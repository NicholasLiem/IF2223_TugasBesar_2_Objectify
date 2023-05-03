package com.objectify.controllers.scenes;

import com.objectify.controllers.components.menubar.MenuBarManager;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class MainScene extends Scene {
    public MainScene() {
        super(new BorderPane(), 800, 600);

        TabPane tabPane = new TabPane();
        MenuBarManager mbManager = new MenuBarManager(tabPane);

        MenuBar menuBar = mbManager.getMenuBar();

        BorderPane root = (BorderPane) getRoot();
        root.setCenter(tabPane);
        root.setTop(menuBar);
    }
}