package com.objectify.models.items;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement(name = "CategoryManager")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryManager implements Serializable {
    private static final long serialVersionUID = -4058286834197067403L;

    @XmlElement(name = "Categories")
    private ArrayList<Category> categories;

    public CategoryManager(){

        this.categories = new ArrayList<>();

    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void removeCategory(Category category){
        this.categories.remove(category);
    }

    public void setCategories(ArrayList<Category> categories){
        this.categories = categories;
    }

    public Category searchCategory(String category){
        for(Category c : this.categories){
            if(c.getName() == category){
                return c;
            }
        }
        return null;
    }
}