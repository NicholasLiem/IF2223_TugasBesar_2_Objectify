package com.objectify.CurrencyPlugin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.objectify.datastore.Settings;
import com.objectify.datastore.SystemPointOfSales;
import com.objectify.datastore.ComboBoxBuilder;
import com.objectify.plugin.Plugin;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class Main extends Plugin {
    private List<Currency> currencies;

    public Main() {};

    @Override
    public void onEnable(SystemPointOfSales spos) {
        loadCurrencies(spos);
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

            String[] options = new String[currencies.size()];
            for (int i = 0; i < options.length; i++) {
                options[i] = currencies.get(i).getName();
            }
            spos.getSettings().getUiConfig().add(new ComboBoxBuilder("currency", "Currency", options, options[0]));


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
        List<Currency> currencyList;

        // Check if Currency.json file exists, and if not, create it with default values
        Path currencyPath = Paths.get(spos.getSettings().getSettingsPath() + "Currency.json");
        if (currencyPath.toFile().exists()) {
            try {
                InputStream input = new FileInputStream(currencyPath.toFile());
                ObjectNode currencyNode = mapper.readValue(input, ObjectNode.class);
                currencyList = new ArrayList<>();
                Iterator<Map.Entry<String, JsonNode>> fields = currencyNode.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> field = fields.next();
                    String name = field.getKey();
                    double exchangeRate = parseDouble(field.getValue().asText());
                    Currency currency = new Currency(name, exchangeRate);
                    currencyList.add(currency);
                }
            } catch (IOException e) {
                e.printStackTrace();
                currencyList = null;
            }
        } else {
            InputStream inputStream = getClass().getResourceAsStream("/Currencies.json");
            try {
                currencyList = mapper.readValue(inputStream, CurrencyList.class).getCurrencies();
                ObjectNode currencyNode = mapper.createObjectNode();
                for (Currency currency : currencyList) {
                    currencyNode.put(currency.getName(), currency.getExchangeRate());
                }
                OutputStream currencyOutput = new FileOutputStream(currencyPath.toFile());
                mapper.writerWithDefaultPrettyPrinter().writeValue(currencyOutput, currencyNode);
            } catch (IOException e) {
                e.printStackTrace();
                currencyList = null;
            }
        }

        currencies = currencyList;
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
