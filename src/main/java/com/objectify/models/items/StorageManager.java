package com.objectify.models.items;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement(name = "StorageManagerList")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor @Getter @Setter
public class StorageManager implements Serializable {
    private static final long serialVersionUID = 1265615619191872709L;
    
    @XmlElement(name = "Products")
    private ArrayList<Product> products = new ArrayList<>();

    public void addProduct(Product product){
        this.products.add(product);
    }
    public void removeProduct(Product product){
        this.products.remove(product);
    }
}
