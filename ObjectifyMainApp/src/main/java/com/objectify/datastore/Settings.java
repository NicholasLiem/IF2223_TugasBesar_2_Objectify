package com.objectify.datastore;

public class Settings {
    private String currency;
    private double tax;

    private double discount;
    private double serviceCharge;

    public Settings() {
        this.currency = "IDR";
        this.tax = 0.1;
        this.serviceCharge = 0.05;
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
}
