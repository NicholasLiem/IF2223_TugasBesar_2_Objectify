package com.objectify.controllers.pages;

import com.objectify.datastore.Settings;
import com.objectify.datastore.SystemPointOfSales;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class SettingsPage extends GridPane {

    public SettingsPage() {
        setPadding(new Insets(10));
        setHgap(10);
        setVgap(10);

        SystemPointOfSales.getInstance().getSettings().setCurrency("IDR");
        add(new Label("Currency: " + SystemPointOfSales.getInstance().getSettings().getCurrency()), 0, 0);
    }
}
