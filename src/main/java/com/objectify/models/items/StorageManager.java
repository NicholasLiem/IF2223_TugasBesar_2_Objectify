package com.objectify.models.items;

import com.objectify.models.entities.UserManager;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement(name = "StorageManagerList")
@XmlAccessorType(XmlAccessType.FIELD)
public class StorageManager implements Serializable {
    private static final long serialVersionUID = 1265615619191872709L;
    private static StorageManager instance;
    @XmlElement(name = "Products")
    private ArrayList<Product> products;

    public static synchronized StorageManager getInstance() {
        if (instance == null) {
            instance = new StorageManager();
        }
        return instance;
    }

    private StorageManager(){
        this.products = new ArrayList<>();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product){
        this.products.add(product);
    }

    public void removeProduct(Product product){
        this.products.remove(product);
    }
}
