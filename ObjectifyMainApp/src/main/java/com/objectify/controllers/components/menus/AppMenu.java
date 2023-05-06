package com.objectify.controllers.components.menus;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public abstract class AppMenu extends Menu {
    
    protected TabPane tabPane;
    
    public AppMenu(String name, TabPane tabPane) {
        super(name);
        this.tabPane = tabPane;
    }

    public void addItem(MenuItem menuItem, Tab tab) {
        this.getItems().add(menuItem);
        menuItem.setOnAction(event -> {
            this.tabPane.getTabs().add(tab);
            this.tabPane.getSelectionModel().select(tab);
        });
    }
    
}
