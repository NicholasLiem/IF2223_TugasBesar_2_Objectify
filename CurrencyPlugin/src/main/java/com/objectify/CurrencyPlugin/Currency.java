package com.objectify.CurrencyPlugin;

public class Currency {
    private String name;
    private double exchangeRate;

    public Currency(){
    }

    public Currency(String name, double exchangeRate) {
        this.name = name;
        this.exchangeRate = exchangeRate;
    }

    public String getName() {
        return name;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    @Override
    public String toString() {
        return "Currency{name=" + name + ",exchangeRate=" + exchangeRate + "}";
    }
}
