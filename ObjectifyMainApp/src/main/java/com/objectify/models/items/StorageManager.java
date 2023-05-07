package com.objectify.models.items;
import com.objectify.exceptions.ItemNotFoundException;

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
    @XmlElement(name = "Products")
    private ArrayList<Product> listOfProducts;

    public StorageManager(){
        this.listOfProducts = new ArrayList<>();
    }
    public void addNewProducts(Product new_items){
        this.listOfProducts.add(new_items);

    }
    public ArrayList<Product> getProducts(){
        return this.listOfProducts;
    }
    public void editProduct(int product_id, Product new_products){
        for (Product products : this.listOfProducts){
            if(products.getIdProduct() == product_id){
                products.setProductStock(new_products.getProductStock());
                products.setProductName(new_products.getProductName());
                products.setProductPrice(new_products.getProductPrice());
                products.setProductBuyPrice(new_products.getProductBuyPrice());
                products.setCategory(new_products.getProductCategory());
                products.setProductImagePath(new_products.getProductImagePath());
            }
        }
    }


    public ArrayList<Product> searchItemByName(String name) throws ItemNotFoundException{
        ArrayList<Product>results = new ArrayList<>();
        for(Product products : this.listOfProducts){
            if(products.getProductName().toLowerCase().contains(name.toLowerCase())) {
                results.add(products);
            }
        }
        if(results.size() != 0){
            return results;
        }else{
//            Throw error kalau tidak terdapat item yang dicari
            throw new ItemNotFoundException();
        }
    };

    public ArrayList<Product> searchByCategory(String category) throws ItemNotFoundException{
        ArrayList<Product> results = new ArrayList<>();
        for(Product product :this.listOfProducts){
            if(product.getProductCategory().getName().toLowerCase().contains(category.toLowerCase())){
                results.add(product);
            }
        }
        if(results.size() != 0){
            return results;
        }else{
            throw new ItemNotFoundException();
        }
    };

    public Product searchById(Integer productId) throws ItemNotFoundException{
        for (Product product : this.listOfProducts){
            if (product.getIdProduct() == productId){
                return product;
            }
        }
        throw new ItemNotFoundException();
    }

    public ArrayList<Product> searchByPrice(String price) throws  ItemNotFoundException{
        ArrayList<Product> results = new ArrayList<>();
        for(Product product : this.listOfProducts){
            if(Double.toString(product.getProductPrice()).toLowerCase().contains(price.toLowerCase())){
                results.add(product);
            }
        }
        if(results.size() != 0){
            return results;
        }else{
            throw new ItemNotFoundException();
        }
    }

    public void showProducts(){
        for(Product product : this.listOfProducts){
            System.out.println("Products : ");
            System.out.println(product.getProductName());
            System.out.println(product.getIdProduct());
            System.out.println(product.getProductStock());
        }
    }

    public void removeProduct(Product product){
        this.listOfProducts.remove(product);
    }
    public void setProducts(ArrayList<Product> prod){
        this.listOfProducts = prod;
    }
}
