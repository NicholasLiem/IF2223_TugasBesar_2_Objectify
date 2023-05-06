package com.objectify.CurrencyPlugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.objectify.datastore.Settings;
import com.objectify.datastore.SystemPointOfSales;
import com.objectify.datastore.ComboBoxBuilder;
import com.objectify.plugin.Plugin;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main extends Plugin {
    private List<Currency> currencies;

    public Main() {};

    @Override
    public void onEnable(SystemPointOfSales spos) {
        loadCurrencies();
        try {
            Path settingsPath = Paths.get(spos.getSettings().getSettingsPath() + "Settings.json");
            InputStream input = new FileInputStream(settingsPath.toFile());
            ObjectMapper mapper = new ObjectMapper();
            Settings settings = mapper.readValue(input, Settings.class);

            // Check if Currency.json file exists, and if not, create it with default values
            Path currencyPath = Paths.get(spos.getSettings().getSettingsPath() + "Currency.json");
            if (!currencyPath.toFile().exists()) {
                ObjectNode currencyNode = mapper.createObjectNode();
                for (Currency currency : currencies) {
                    currencyNode.put(currency.getName(), currency.getExchangeRate());
                }
                OutputStream currencyOutput = new FileOutputStream(currencyPath.toFile());
                mapper.writerWithDefaultPrettyPrinter().writeValue(currencyOutput, currencyNode);
            }

            // Update the CurrencyDefault value with the first currency in the list
            settings.getAdditionalProperties().put("Currencies", mapper.createObjectNode()
                    .put("CurrencySettings", spos.getSettings().getSettingsPath() + "Currency.json")
                    .put("CurrencyDefault", currencies.get(0).getName())
                    .put("CurrencyExchangeRate", 1));


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

    public void loadCurrencies() {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = getClass().getResourceAsStream("/Currencies.json");

        try {
            currencies = mapper.readValue(inputStream, CurrencyList.class).getCurrencies();
        } catch (IOException e) {
            e.printStackTrace();
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
