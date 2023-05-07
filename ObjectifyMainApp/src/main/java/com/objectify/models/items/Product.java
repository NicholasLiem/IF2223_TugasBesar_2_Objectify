package com.objectify.models.items;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "Product")
@XmlAccessorType (XmlAccessType.FIELD)
public class Product implements Serializable {
    private static final long serialVersionUID = 730501466045521531L;

    @JsonIgnore
    private static int totalProducts = 0;
    private int idProduct;
    private int productStock;
    private String productName;
    private double productPrice;
    private double productBuyPrice;

    @XmlElement(name = "ProductCategory")
    private Category productCategory;
    private String productImagePath;

    public Product(){

    }
    public Product(int productStock, String productName, double productPrice, double productBuyPrice, Category productCategory, String productImagePath) {
        this.idProduct = totalProducts+1;
        this.productStock = productStock;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productBuyPrice = productBuyPrice;
        this.productCategory = productCategory;
        this.productImagePath = productImagePath;
        totalProducts++;
    }

    public  int getIdProduct(){return this.idProduct;}

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

    @XmlElement(name = "ProductCategory")
    public Category getProductCategory() {
        return this.productCategory;
    }

    public void setCategory(Category category) {
        this.productCategory = category;
    }

    public String getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productStock=" + productStock +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productBuyPrice=" + productBuyPrice +
                ", category=" + productCategory +
                ", productImagePath='" + productImagePath + '\'' +
                '}';
    }
}
