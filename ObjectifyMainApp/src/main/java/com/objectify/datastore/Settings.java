package com.objectify.datastore;

import java.util.HashMap;
import java.util.Map;

public class Settings {
    private HashMap<String, Double> currencies;
    private String currentCurrency;

    public Settings() {
        this.currencies = new HashMap<>();
        // Set default currency
        this.addCurrency("IDR", 1.0);
        this.setCurrentCurrency("IDR");
    }

    public HashMap<String, Double> getCurrencies() {
        return currencies;
    }

    public void addCurrency(String currency, double exchangeRate) {
        currencies.put(currency, exchangeRate);
    }

    public double getExchangeRate(String currency) {
        Double exchangeRate = currencies.get(currency);
        if (exchangeRate == null) {
            throw new IllegalArgumentException("Currency not found: " + currency);
        }
        return exchangeRate;
    }

    public void setCurrentCurrency(String currencyName){
        this.currentCurrency = currencyName;
    }

    public String getCurrentCurrency(){
        return this.currentCurrency;
    }
}
