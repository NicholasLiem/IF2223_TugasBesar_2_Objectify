package com.objectify.CurrencyPlugin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.objectify.datastore.Settings;
import com.objectify.datastore.SystemPointOfSales;
import com.objectify.datastore.ComboBoxBuilder;
import com.objectify.plugin.Plugin;

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
    private List<Currency> currencies;

    public CurrencyPlugin() {};

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

            String[] options = new String[currencies.size()];
            for (int i = 0; i < options.length; i++) {
                options[i] = currencies.get(i).getName();
            }
            settings.getUiConfig().add(new ComboBoxBuilder("currency", "Currency", options, options[0]));

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
                currencies = currencyList;
            } catch (IOException e) {
                e.printStackTrace();
                currencies = null;
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
            currencies = currencyList;
        } catch (IOException e) {
            e.printStackTrace();
            currencies = null;
        }
    }



    public static class CurrencyList {
        private List<Currency> currencies;

        public List<Currency> getCurrencies() {
            return currencies;
        }

        public void setCurrencies(List<Currency> currencies) {
            this.currencies = currencies;
        }
    }
}
