package com.objectify.controllers.scenes;

import com.objectify.controllers.components.menubar.MenuBarManager;
import com.objectify.controllers.pages.DashboardPane;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class MainScene extends Scene {
    
    private final MenuBarManager mbManager;
    
    public MainScene() {
        super(new BorderPane(), 800, 600);

        TabPane tabPane = new TabPane();
        mbManager = new MenuBarManager(tabPane);

        MenuBar menuBar = mbManager.getMenuBar();

        BorderPane root = (BorderPane) getRoot();
        root.setCenter(tabPane);
        root.setTop(menuBar);

        DashboardPane dashboardPane = new DashboardPane();
        root.setCenter(dashboardPane);

        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == null) {
                root.setCenter(dashboardPane);
            } else {
                root.setCenter(tabPane);
            }
        });
    }
    
    public MenuBarManager getMbManager() {
        return mbManager;
    }
}