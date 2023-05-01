package com.objectify.models.items;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "Product")
@XmlAccessorType (XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class Product implements Serializable {
    private static final long serialVersionUID = 730501466045521531L;
    
    private int productStock;
    private String productName;
    private double productPrice;
    private double productBuyPrice;

    @XmlElement(name = "ProductCategory")
    private Category category;
    private String productImagePath;
}
