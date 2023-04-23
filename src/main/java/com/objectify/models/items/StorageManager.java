package com.objectify.models.items;

import com.objectify.exceptions.ItemNotFoundException;

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
            if(products.getIdProduct() == product_id){
                products.setProductStock(new_products.getProductStock());
                products.setProductName(new_products.getProductName());
                products.setProductPrice(new_products.getProductPrice());
                products.setProductBuyPrice(new_products.getProductBuyPrice());
                products.setProductCategory(new_products.getProductCategory());
                products.setProductImagePath(new_products.getProductImagePath());
            }
        }
    }


    public ArrayList<Product> searchItemByName(String name) throws ItemNotFoundException{
        ArrayList<Product>results = new ArrayList<>();
        for(Product products : this.list_of_products){
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
        for(Product product : this.list_of_products){
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

    public ArrayList<Product> searchByPrice(String price) throws  ItemNotFoundException{
        ArrayList<Product> results = new ArrayList<>();
        for(Product product : this.list_of_products){
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
        for(Product product : this.list_of_products){
            System.out.println("Products : ");
            System.out.println(product.getProductName());
            System.out.println(product.getIdProduct());
            System.out.println(product.getProductStock());
        }
    }
}
