package com.objectify.CurrencyPlugin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.objectify.datastore.Settings;
import com.objectify.datastore.interfaces.InputControl;
import com.objectify.datastore.SystemPointOfSales;
import com.objectify.plugin.Plugin;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class CurrencyPlugin extends Plugin {
    private static final String CURRENCY_SETTINGS_PATH = "Currency.json";
    private static final String SETTINGS_PATH = "Settings.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    private final List<Currency> currencies;

    public CurrencyPlugin() {
        currencies = new ArrayList<>();
    };

    @Override
    public void onEnable(SystemPointOfSales spos) {
        loadCurrencies(spos);
        Settings settings = spos.getSettings();
        try {
            // Check if Currency.json file exists, and if not, create it with default values
            Path currencyPath = Paths.get(settings.getSettingsPath() + CURRENCY_SETTINGS_PATH);
            if (!currencyPath.toFile().exists()) {
                ObjectNode currencyNode = mapper.createObjectNode();
                for (Currency currency : currencies) {
                    currencyNode.put(currency.getName(), currency.getExchangeRate());
                }
                OutputStream currencyOutput = new FileOutputStream(currencyPath.toFile());
                mapper.writerWithDefaultPrettyPrinter().writeValue(currencyOutput, currencyNode);
            }

            // Update the CurrencyDefault value with the first currency in the list
            settings.getAdditionalProperties().put("CurrencySystem", mapper.createObjectNode()
                    .put("CurrencySettings", currencyPath.toString())
                    .put("CurrencyDefault", currencies.get(0).getName())
                    .put("CurrencyExchangeRate", 1));

            settings.getComponents().add(new InputControl() {
                @Override
                public Label getLabel() {
                    return new Label("Currency");
                }
                @Override
                public Node getInputControl() {
                    ComboBox<String> comboBox = new ComboBox<>();
                    comboBox.setValue("IDR");
                    final Currency curr = currencies.stream().filter(item -> item.getName().equals("IDR")).findFirst().orElse(currencies.get(0));
                    spos.getSettings().addCalculator("CurrencyCalculator", value -> value * curr.getExchangeRate());
                    for (Currency c : currencies) {
                        comboBox.getItems().add(c.getName());
                    }
                    comboBox.setOnAction(event -> {
                        final String name = comboBox.getValue();
                        final Currency c = currencies.stream().filter(item -> item.getName().equals(name)).findFirst().orElse(currencies.get(0));
                        spos.getSettings().addCalculator("CurrencyCalculator", value -> value * c.getExchangeRate());
                    });
                    return comboBox;
                }
            });

            Path settingsPath = Paths.get(settings.getSettingsPath() + SETTINGS_PATH);
            OutputStream output = new FileOutputStream(settingsPath.toFile());
            mapper.writerWithDefaultPrettyPrinter().writeValue(output, settings);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Currencies Plugin" + " has been enabled!");

        // Register a shutdown hook to run the onDisable method when the program closes
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                onDisable(spos);
            }
        }));
    }


    @Override
    public void onDisable(SystemPointOfSales spos) {
        spos.getSettings().removeCalculator("CurrencyCalculator");
        System.out.println("Currencies Plugin" + " has been disabled!");
    }

    public void loadCurrencies(SystemPointOfSales spos) {
        ObjectMapper mapper = new ObjectMapper();

        Path currencyPath = Paths.get(spos.getSettings().getSettingsPath() + CURRENCY_SETTINGS_PATH);
        if (!currencyPath.toFile().exists()) {
            // Currency file doesn't exist, create it with default values
            try (InputStream inputStream = getClass().getResourceAsStream("/Currencies.json")) {
                List<Currency> currencyList = mapper.readValue(inputStream, CurrencyList.class).getCurrencies();
                ObjectNode currencyNode = mapper.createObjectNode();
                for (Currency currency : currencyList) {
                    currencyNode.put(currency.getName(), currency.getExchangeRate());
                }
                OutputStream currencyOutput = new FileOutputStream(currencyPath.toFile());
                mapper.writerWithDefaultPrettyPrinter().writeValue(currencyOutput, currencyNode);
                currencies.clear();
                currencies.addAll(currencyList);
            } catch (IOException e) {
                e.printStackTrace();
                currencies.clear();
            }
            return;
        }

        // Currency file exists, read values from it
        try (InputStream input = new FileInputStream(currencyPath.toFile())) {
            ObjectNode currencyNode = mapper.readValue(input, ObjectNode.class);
            List<Currency> currencyList = new ArrayList<>();
            Iterator<Map.Entry<String, JsonNode>> fields = currencyNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String name = field.getKey();
                double exchangeRate = parseDouble(field.getValue().asText());
                Currency currency = new Currency(name, exchangeRate);
                currencyList.add(currency);
            }
            currencies.clear();
            currencies.addAll(currencyList);
        } catch (IOException e) {
            e.printStackTrace();
            currencies.clear();
        }
    }



    public static class CurrencyList {
        private List<Currency> currencies;

        public List<Currency> getCurrencies() {
            return currencies;
        }

        public void setCurrencies(List<Currency> currencies) {
            this.currencies.clear();
            this.currencies.addAll(currencies);
        }
    }
}
