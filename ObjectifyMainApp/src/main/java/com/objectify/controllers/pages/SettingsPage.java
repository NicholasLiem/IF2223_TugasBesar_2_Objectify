package com.objectify.controllers.pages;

import com.objectify.datastore.Settings;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class SettingsPage extends GridPane {

    public SettingsPage() {
        setPadding(new Insets(10));
        setHgap(10);
        setVgap(10);

        Settings settings = Settings.getInstance();
        add(new Label("Currency: " + settings.getCurrency()), 0, 0);
    }
}
