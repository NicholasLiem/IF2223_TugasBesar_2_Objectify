package com.objectify.controllers.scenes;

import com.objectify.controllers.components.menubar.MenuBarManager;
import com.objectify.controllers.pages.CashierPage;
import com.objectify.datastore.SystemPointOfSales;
import com.objectify.exceptions.InvalidArgumentsException;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;

public class MainScene extends Scene {
    
    private final MenuBarManager mbManager;
    private final TabPane tabPane;
    
    public MainScene() {
        super(new BorderPane(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());

        System.out.println("Initializing data with JSON datastore (default)");

        SystemPointOfSales.getInstance().getSettings().initialiseDataStores("JSON");
        SystemPointOfSales.getInstance().getSettings().loadAllDataStore();

        tabPane = new TabPane();
        mbManager = new MenuBarManager(tabPane);

        MenuBar menuBar = mbManager.getMenuBar();

        BorderPane root = (BorderPane) getRoot();
        root.setCenter(tabPane);
        root.setTop(menuBar);

        CashierPage cashierPage = new CashierPage();
        root.setCenter(cashierPage);

        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == null) {
                root.setCenter(cashierPage);
            } else {
                root.setCenter(tabPane);
            }
        });

        SystemPointOfSales.getInstance().registerCommand("NewTab", (spos, args) -> {
            try {
                Tab tab = (Tab) args[0];
                tabPane.getTabs().add(tab);
                tabPane.getSelectionModel().select(tab);
            } catch (ClassCastException e) {
                throw new InvalidArgumentsException("expected arguments (Tab tab)");
            }
        });
    }
    
    public MenuBarManager getMbManager() {
        return mbManager;
    }
}
