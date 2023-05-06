package com.objectify.datastore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Settings {
    private static Settings instance = null;
    private String currency;
    private double tax;

    private double discount;
    private double serviceCharge;
    
    private final List<SettingBuilder<?>> uiSetup;

    private Settings() {
        this.currency = "IDR";
        this.tax = 0.1;
        this.serviceCharge = 0.05;
        
        this.uiSetup = new ArrayList<>();
    }

    public static synchronized Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
    
    public void addUiSetup(SettingBuilder<?> settingBuilder) {
        uiSetup.add(settingBuilder);
    }
    
    public List<SettingBuilder<?>> getUiSetup() {
        return uiSetup;
    }
}
