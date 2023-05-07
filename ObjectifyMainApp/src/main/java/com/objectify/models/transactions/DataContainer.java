package com.objectify.models.transactions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
@XmlRootElement(name = "DataContainer")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataContainer implements Serializable {
    private String productName;
    private double productPrice;
    private int productCount;
    
    public DataContainer(){
        
    }

    public DataContainer(String productName, double productPrice, Integer value) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCount = value;
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

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
}
