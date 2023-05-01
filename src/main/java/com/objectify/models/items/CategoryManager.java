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

@XmlRootElement(name = "CategoryManager")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor @Setter @Getter
public class CategoryManager implements Serializable {
    private static final long serialVersionUID = -4058286834197067403L;

    @XmlElement(name = "Categories")
    private ArrayList<Category> categories = new ArrayList<>();
    
    public void addCategory(Category category) {
        this.categories.add(category);
    }
    public void removeCategory(Category category){
        this.categories.remove(category);
    }
}