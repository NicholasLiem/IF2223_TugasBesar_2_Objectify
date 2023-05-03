package com.objectify.controllers.components.menus;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

public class AdminMenu extends Menu {

    public AdminMenu(TabPane tabPane){
        super("Admin");
        Menu newTab = new Menu("Admin Page");
        MenuItem productTab =  new MenuItem("Product Manager Tab");
        MenuItem memberManagerTab = new MenuItem("Member Manager Tab");
        newTab.getItems().addAll(productTab, memberManagerTab);

        this.getItems().addAll(newTab);
        productTab.setOnAction(event -> {
            Pane newPage = new Pane();
            Tab newPaneTab = new Tab("Product Manager Tab", newPage);
            tabPane.getTabs().add(newPaneTab);
            tabPane.getSelectionModel().select(newPaneTab);
        });

        memberManagerTab.setOnAction(event -> {
            Pane newPage = new Pane();
            Tab newPaneTab = new Tab("Member Manager Tab", newPage);
            tabPane.getTabs().add(newPaneTab);
            tabPane.getSelectionModel().select(newPaneTab);
        });
    }
}
