package com.objectify.controllers.components.menus;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class PluginMenu extends AppMenu {
    
    public PluginMenu(TabPane tabPane){
        super("Plugin", tabPane);
        MenuItem pluginSettings = new MenuItem("Settings");
        this.getItems().add(pluginSettings);
        pluginSettings.setOnAction(event -> {
            Pane pluginSettingsPage = new Pane();
            Tab pluginSettingsTab = new Tab("Plugin Settings", pluginSettingsPage);
            this.tabPane.getTabs().add(pluginSettingsTab);
            this.tabPane.getSelectionModel().select(pluginSettingsTab);
        });
    }
}
