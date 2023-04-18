package com.objectify.models.items;

public class Product {
    private int productStock;
    private String productName;
    private double productPrice;
    private double productBuyPrice;
    private Category productCategory;
    private String productImagePath;

    public Product(int productStock, String productName, double productPrice, double productBuyPrice, Category productCategory, String productImagePath) {
        this.productStock = productStock;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productBuyPrice = productBuyPrice;
        this.productCategory = productCategory;
        this.productImagePath = productImagePath;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getProductBuyPrice() {
        return productBuyPrice;
    }

    public void setProductBuyPrice(double productBuyPrice) {
        this.productBuyPrice = productBuyPrice;
    }

    public Category getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Category productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }
}