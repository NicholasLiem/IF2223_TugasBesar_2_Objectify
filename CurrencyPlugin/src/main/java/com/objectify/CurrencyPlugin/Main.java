package com.objectify.CurrencyPlugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.objectify.datastore.SystemPointOfSales;
import com.objectify.plugin.Plugin;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Main extends Plugin {
    private List<Currency> currencies;

    public Main() {
        super("Currency Plugin", "com.objectify.CurrencyPlugin.Main");
    }

    @Override
    public void onEnable(SystemPointOfSales spos) {
        loadCurrencies();
        for(Currency c: currencies){
            spos.getSettings().addCurrency(c.getName(), c.getExchangeRate());
        }

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
