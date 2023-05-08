package com.objectify.controllers.pages;

import com.objectify.datastore.interfaces.InputControl;
import com.objectify.datastore.SystemPointOfSales;
import com.objectify.plugin.PluginLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;


public class SettingsPage extends GridPane {
    public SettingsPage() {
        setPadding(new Insets(10));
        setHgap(10);
        setVgap(10);
        Label selectDatastore = new Label("Select Datastore: ");
        ComboBox<String> datastoreComboBox = new ComboBox<>();
        datastoreComboBox.getItems().addAll("JSON", "XML", "OBJ");
        datastoreComboBox.getSelectionModel().selectFirst();

        datastoreComboBox.setOnAction(event -> {
            String selectedDatastore = datastoreComboBox.getValue();
            switch (selectedDatastore) {
                case "JSON":
                    SystemPointOfSales.getInstance().getSettings().initialiseDataStores("JSON");
                    break;
                case "XML":
                    SystemPointOfSales.getInstance().getSettings().initialiseDataStores("XML");
                    break;
                case "OBJ":
                    SystemPointOfSales.getInstance().getSettings().initialiseDataStores("OBJ");
                    break;
                default:
                    SystemPointOfSales.getInstance().getSettings().initialiseDataStores("JSON");
                    break;
            }
            SystemPointOfSales.getInstance().getSettings().loadAllDataStore();
        });


        final String[] selectedPluginPath = {""};
        Label selectPlugin = new Label("Select Plugin: ");
        Button pluginChooserBtn = new Button("Select Plugin");
        pluginChooserBtn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Jar File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Jar Files", "*.jar"));
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
                File selectedFileOrDir = new File(selectedPluginPath[0]);
                if(selectedFileOrDir.isDirectory()) {
                    loader.loadPlugins(selectedPluginPath[0]);
                } else {
                    loader.loadPlugin(String.valueOf(selectedFileOrDir));
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

            // Refresh the SettingsPage to display the updated UI components
            getChildren().clear();
            add(selectPlugin, 0, 0);
            add(pluginChooserBtn, 1, 0);
            add(dirChooserBtn, 2, 0);
            add(loadPluginBtn, 3, 0);

            int count = 2;
            for (InputControl ui : SystemPointOfSales.getInstance().getSettings().getComponents()) {
                add(ui.getLabel(), 0, count);
                add(ui.getInputControl(), 1, count);
                count++;
            }
        });

        add(selectDatastore, 0, 1);
        add(datastoreComboBox, 1, 1);
        add(selectPlugin, 0, 0);
        add(pluginChooserBtn, 1, 0);
        add(dirChooserBtn, 2, 0);
        add(loadPluginBtn, 3, 0);


        int count = 2;
        for (InputControl ui : SystemPointOfSales.getInstance().getSettings().getComponents()) {
            add(ui.getLabel(), 0, count);
            add(ui.getInputControl(), 1, count);
            count++;
        }
    }
}
