package com.objectify.controllers.pages;

import com.objectify.datastore.SettingBuilder;
import com.objectify.datastore.SystemPointOfSales;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


public class SettingsPage extends GridPane {
    private ComboBox<String> currencyComboBox;

    public SettingsPage() {
        setPadding(new Insets(10));
        setHgap(10);
        setVgap(10);
 
        for (SettingBuilder<?> ui : SystemPointOfSales.getInstance().getSettings().getUiConfig()) {
            add(new Label(ui.getLabel()), 0, 0);
            add((Node) ui.build(), 1, 0);
        }
    }
}
