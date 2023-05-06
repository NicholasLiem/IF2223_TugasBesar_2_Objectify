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

    public Main(String name) {
        super(name);
    }

    @Override
    public void onEnable(SystemPointOfSales spos) {
        loadCurrencies();
        try {
            Path settingsPath = Paths.get(spos.getSettings().getSettingsPath());
            InputStream input = new FileInputStream(settingsPath.toFile());
            ObjectMapper mapper = new ObjectMapper();
            Settings settings = mapper.readValue(input, Settings.class);
            for (Currency currency : currencies) {
                ObjectNode currencyNode = mapper.createObjectNode();
                currencyNode.put("currencies", currency.getName());
                currencyNode.put("exchange_rate", currency.getExchangeRate());
                settings.getAdditionalProperties().put(currency.getName(), currencyNode);
            }
            OutputStream output = new FileOutputStream(settingsPath.toFile());
            mapper.writerWithDefaultPrettyPrinter().writeValue(output, settings);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] options = new String[currencies.size()];
        for (int i = 0; i < options.length; i++) {
            options[i] = currencies.get(i).getName();
        }
        spos.getSettings().getUiConfig().add(new ComboBoxBuilder("currency", "Currency", options, options[0]));

        System.out.println(this.getName() + " has been enabled!");

        // Register a shutdown hook to run the onDisable method when the program closes
        Runtime.getRuntime().addShutdownHook(new Thread(this::onDisable));
    }

    @Override
    public void onDisable() {
        System.out.println(this.getName() + " has been disabled!");
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
