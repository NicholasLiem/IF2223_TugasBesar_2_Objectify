package com.objectify.controllers.pages;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


public class SettingsPage extends GridPane {
    private ComboBox<String> currencyComboBox;

    public SettingsPage() {
        setPadding(new Insets(10));
        setHgap(10);
        setVgap(10);

        // Create currency combo box
        currencyComboBox = new ComboBox<>();
        add(new Label("Currency:"), 0, 0);
        add(currencyComboBox, 1, 0);
    }
}
