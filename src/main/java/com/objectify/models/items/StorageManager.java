package com.objectify.models.items;

import java.util.ArrayList;

public class StorageManager {
//    penambahan
    private ArrayList<Product> list_of_products;
    public  StorageManager(){
        this.list_of_products = new ArrayList<>();
    }
    public void addNewProducts(Product new_items){
        this.list_of_products.add(new_items);
    }
    public ArrayList<Product> getProducts(){
        return this.list_of_products;
    }
    public void editProduct(int product_id, Product new_products){
        for (Product products : this.list_of_products){
            if(products.get_id() == product_id){
                products.setProductStock(new_products.getProductStock());
                products.setProductName(new_products.getProductName());
                products.setProductPrice(new_products.getProductPrice());
                products.setProductBuyPrice(new_products.getProductBuyPrice());
                products.setProductCategory(new_products.getProductCategory());
                products.setProductImagePath(new_products.getProductImagePath());
            }
        }
    }

    public Product searchByName(String name){
        for(Product products : this.list_of_products){
            if(products.getProductName().contains(name.toLowerCase())){
                return products;
            }
        }

    };
}
