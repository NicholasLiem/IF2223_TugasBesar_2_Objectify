package com.objectify.models.items;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "Category")
@XmlAccessorType(XmlAccessType.FIELD)
public class Category implements Serializable {
    private static final long serialVersionUID = 4383290787610306325L;
    
    private String name;

    public Category(){

    }

    public Category(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}