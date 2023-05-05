package com.objectify.controllers.components.menus;

import com.objectify.controllers.pages.ProductManagerPage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class AdminMenu extends Menu {

    public AdminMenu(TabPane tabPane){
        super("Admin");
        Menu newTab = new Menu("Admin Page");
        MenuItem productTab =  new MenuItem("Product Manager");
        MenuItem memberManagerTab = new MenuItem("Member Manager");
        newTab.getItems().addAll(productTab, memberManagerTab);

        this.getItems().addAll(newTab);
        productTab.setOnAction(event -> {
            GridPane storageManager = new ProductManagerPage();
            Tab newPaneTab = new Tab("Product Manager", storageManager);
            tabPane.getTabs().add(newPaneTab);
            tabPane.getSelectionModel().select(newPaneTab);
        });

        memberManagerTab.setOnAction(event -> {
            Pane newPage = new Pane();
            Tab newPaneTab = new Tab("Member Manager", newPage);
            tabPane.getTabs().add(newPaneTab);
            tabPane.getSelectionModel().select(newPaneTab);
        });
    }
}
