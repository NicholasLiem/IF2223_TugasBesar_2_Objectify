package com.objectify.controllers.components.menus;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class PluginMenu extends Menu {
    public PluginMenu(TabPane tabPane){
        super("Plugin");
        MenuItem pluginSettings = new MenuItem("Settings");
        this.getItems().add(pluginSettings);
        pluginSettings.setOnAction(event -> {
            Pane pluginSettingsPage = new Pane();
            Tab pluginSettingsTab = new Tab("Plugin Settings", pluginSettingsPage);
            tabPane.getTabs().add(pluginSettingsTab);
            tabPane.getSelectionModel().select(pluginSettingsTab);
        });
    }
}
