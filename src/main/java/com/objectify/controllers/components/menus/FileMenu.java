package com.objectify.controllers.components.menus;

import com.objectify.controllers.pages.RegisterMemberPage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class FileMenu extends Menu {

    public FileMenu(TabPane tabPane){
        super("File");
        Menu newTab = new Menu("New Tab");
        MenuItem cashierTab = new MenuItem("Cashier Tab");
//        MenuItem productTab =  new MenuItem("Product Manager Tab");
        MenuItem registerMemberTab = new MenuItem("Register Member Tab");
//        MenuItem memberManagerTab = new MenuItem("Member Manager Tab");
        MenuItem billHistoryTab = new MenuItem("Bill History Tab");
        newTab.getItems().addAll(cashierTab, registerMemberTab, billHistoryTab);

        this.getItems().addAll(newTab);
        cashierTab.setOnAction(event -> {
            Pane newPage = new Pane();
            Tab newPaneTab = new Tab("Cashier Tab", newPage);
            tabPane.getTabs().add(newPaneTab);
            tabPane.getSelectionModel().select(newPaneTab);
        });

        registerMemberTab.setOnAction(event -> {
            GridPane rmPage = new RegisterMemberPage();
            Tab newPaneTab = new Tab("Register Member Tab", rmPage);
            tabPane.getTabs().add(newPaneTab);
            tabPane.getSelectionModel().select(newPaneTab);
        });

        billHistoryTab.setOnAction(event -> {
            Pane newPage = new Pane();
            Tab newPaneTab = new Tab("Bill History Tab", newPage);
            tabPane.getTabs().add(newPaneTab);
            tabPane.getSelectionModel().select(newPaneTab);
        });

        // add event handler for openMenuItem if needed
    }
}

