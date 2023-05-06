package com.objectify.PaymentPlugin;

public class Payment {
    private double discount;
    private double tax;
    private double serviceCharge;

    public Payment(){

    }

    public Payment(Double discount, Double tax, Double serviceCharge){
        this.discount = discount;
        this.tax = tax;
        this.serviceCharge = serviceCharge;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
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
}
