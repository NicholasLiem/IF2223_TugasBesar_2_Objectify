package com.objectify.controllers.components.menus;

import com.objectify.controllers.pages.*;
import com.objectify.controllers.pages.CashierPage;
import com.objectify.datastore.SystemPointOfSales;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class FileMenu extends AppMenu {
    private boolean settingsTabOpened = false;

    public FileMenu(TabPane tabPane){
        super("File", tabPane);
        Menu newTab = new Menu("New Tab");
        MenuItem cashierTab = new MenuItem("Cashier Tab");
        MenuItem transactionsTab = new MenuItem("Transactions Tab");
        newTab.getItems().addAll(cashierTab,transactionsTab);

        this.getItems().addAll(newTab);
        cashierTab.setOnAction(event -> {
            CashierPage newPage = new CashierPage();
            Tab newPaneTab = new Tab("Cashier", newPage);
            tabPane.getTabs().add(newPaneTab);
            tabPane.getSelectionModel().select(newPaneTab);
        });

        transactionsTab.setOnAction(event->{
            Pane tmPage = new TransactionManagerPage();
            Tab newPaneTab = new Tab("Transactions Tab",tmPage);
            tabPane.getTabs().add(newPaneTab);
            tabPane.getSelectionModel().select(newPaneTab);
        });

        MenuItem settingsTab = new MenuItem("Settings");
        this.getItems().add(settingsTab);
        settingsTab.setOnAction(event -> {
            if (!settingsTabOpened) {
                GridPane newPage = new SettingsPage();
                Tab newPaneTab = new Tab("Settings", newPage);
                tabPane.getTabs().add(newPaneTab);
                tabPane.getSelectionModel().select(newPaneTab);
                settingsTabOpened = true;

                newPaneTab.setOnClosed(closeEvent -> {
                    settingsTabOpened = false;
                });
            } else {
                // settings tab already opened, select it
                for (Tab tab : tabPane.getTabs()) {
                    if (tab.getText().equals("Settings")) {
                        tabPane.getSelectionModel().select(tab);
                        break;
                    }
                }
            }
        });

        MenuItem saveAllData = new MenuItem("Save All Data");
        this.getItems().add(saveAllData);
        saveAllData.setOnAction(event -> {
            SystemPointOfSales.getInstance().getSettings().saveAllDataStore();
            System.out.println("Saving all data stores...");
        });
    }
}


