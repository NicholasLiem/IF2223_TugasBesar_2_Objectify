package com.objectify.controllers.pages;

import com.objectify.datastore.Settings;
import com.objectify.datastore.SystemPointOfSales;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.HashMap;

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

        // Populate currency combo box with available currencies
        HashMap<String, Double> currencies = SystemPointOfSales.getInstance().getSettings().getCurrencies();
        for (HashMap.Entry<String, Double> Entry : currencies.entrySet()) {
            currencyComboBox.getItems().add(Entry.getKey());
        }

        // Set the default currency in the combo box
        Settings settings = SystemPointOfSales.getInstance().getSettings();
        String defaultCurrency = settings.getCurrentCurrency();
        if (defaultCurrency != null && !defaultCurrency.isEmpty()) {
            currencyComboBox.setValue(defaultCurrency);
        }

        // Handle currency selection changes
        currencyComboBox.setOnAction(event -> {
            String selectedCurrency = currencyComboBox.getValue();
            if (selectedCurrency != null && !selectedCurrency.isEmpty()) {
                settings.setCurrentCurrency(selectedCurrency);
            }
        });
    }
}
