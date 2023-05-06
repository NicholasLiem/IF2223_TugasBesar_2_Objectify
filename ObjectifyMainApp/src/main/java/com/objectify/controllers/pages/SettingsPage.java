package com.objectify.controllers.pages;

import com.objectify.datastore.SettingBuilder;
import com.objectify.datastore.SystemPointOfSales;
import com.objectify.plugin.Plugin;
import com.objectify.plugin.PluginLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;


public class SettingsPage extends GridPane {
    public SettingsPage() {
        setPadding(new Insets(10));
        setHgap(10);
        setVgap(10);

        final String[] selectedPluginPath = {""};
        Label selectPlugin = new Label("Select Plugin: ");
        Button pluginChooserBtn = new Button("Select Plugin");
        pluginChooserBtn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Jar File");
            File selectedFile = fileChooser.showOpenDialog(getScene().getWindow());
            if (selectedFile != null) {
                selectedPluginPath[0] = selectedFile.getAbsolutePath();
            }
        });

        Button dirChooserBtn = new Button("Select Folder");
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Open Folder");

        dirChooserBtn.setOnAction(event -> {
            File selectedDir = dirChooser.showDialog(getScene().getWindow());
            if (selectedDir != null) {
                selectedPluginPath[0] = selectedDir.getAbsolutePath();
            }
        });

        Button loadPluginBtn = new Button("Load Plugin");
        loadPluginBtn.setOnAction(event -> {
            if (selectedPluginPath[0].isEmpty()) {
                // No directory selected
                return;
            }

            // Load plugins from the selected directory or file
            PluginLoader loader = new PluginLoader();
            try {
                File selectedPlugin = new File(selectedPluginPath[0]);
                if(selectedPlugin.isDirectory()) {
                    loader.loadPlugins(selectedPluginPath[0]);
                } else {
                    loader.loadPlugins(selectedPlugin.getParent());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Plugins loaded successfully");
            alert.showAndWait();

            // Refresh the SettingsPage to display the updated UI components
            getChildren().clear();
            add(selectPlugin, 0, 0);
            add(pluginChooserBtn, 1, 0);
            add(dirChooserBtn, 2, 0);
            add(loadPluginBtn, 3, 0);

            for (SettingBuilder<?> ui : SystemPointOfSales.getInstance().getSettings().getUiConfig()) {
                add(new Label(ui.getLabel()), 0, 2);
                add((Node) ui.build(), 1, 2);
            }
        });

        add(selectPlugin, 0, 0);
        add(pluginChooserBtn, 1, 0);
        add(dirChooserBtn, 2, 0);
        add(loadPluginBtn, 3, 0);


        for (SettingBuilder<?> ui : SystemPointOfSales.getInstance().getSettings().getUiConfig()) {
            add(new Label(ui.getLabel()), 0, 2);
            add((Node) ui.build(), 1, 2);
        }
    }
}
