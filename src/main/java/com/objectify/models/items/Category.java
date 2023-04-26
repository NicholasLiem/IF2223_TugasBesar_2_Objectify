package com.objectify.models.items;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Category")
@XmlAccessorType(XmlAccessType.FIELD)
public class Category {
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