package com.objectify.models.items;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Product")
@XmlAccessorType (XmlAccessType.FIELD)
public class Product {
    private int productStock;
    private String productName;
    private double productPrice;
    private double productBuyPrice;

    @XmlElement(name = "ProductCategory")
    private Category category;
    private String productImagePath;

    public Product(){

    }
    public Product(int productStock, String productName, double productPrice, double productBuyPrice, Category category, String productImagePath) {
        this.productStock = productStock;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productBuyPrice = productBuyPrice;
        this.category = category;
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
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }
}
