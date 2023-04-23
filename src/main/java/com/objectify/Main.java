package com.objectify;

import com.objectify.datastore.JSONAdapter;
import com.objectify.datastore.enums.JSONType;
import com.objectify.exceptions.ItemNotFoundException;
import com.objectify.models.entities.*;
import com.objectify.models.items.Category;
import com.objectify.models.items.CategoryManager;
import com.objectify.models.items.Product;
import com.objectify.models.items.StorageManager;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        JSONAdapter jdatastore = JSONAdapter.getInstance();

        StorageManager sm = new StorageManager();
        CategoryManager cm = new CategoryManager();

        Category category1 = new Category("OFFICE_SUPPLIES");
        cm.addCategory(category1);
        Product product1 = new Product(100, "Wooden Desk Organizer", 29.99, 15.00, category1, "images/wooden_desk_organizer.jpg");
        sm.addNewProducts(product1);

        Category category2 = new Category("ELECTRONICS");
        cm.addCategory(category2);
        Product product2 = new Product(50, "Bluetooth Headphones", 89.99, 50.00, category2, "images/bluetooth_headphones.jpg");
        sm.addNewProducts(product2);

        Category category3 = new Category("SPORTS");
        cm.addCategory(category3);
        Product product3 = new Product(200, "Yoga Mat", 39.99, 25.00, category3, "images/yoga_mat.jpg");
        sm.addNewProducts(product3);

        Category category4 = new Category("KITCHENWARE");
        cm.addCategory(category4);
        Product product4 = new Product(500, "Coffee Mug", 12.99, 6.00, category4, "images/coffee_mug.jpg");
        sm.addNewProducts(product4);

        Category category5 = new Category("ELECTRONICS");
        cm.addCategory(category5);
        Product product5 = new Product(100, "Fitness Tracker", 69.99, 40.00, category5, "images/fitness_tracker.jpg");
        sm.addNewProducts(product5);
//        Products sebelum diubah
        sm.showProducts();
//        Driver untuk edit products (edit product 2 jadi sama kayak product 3, tpi id nya tetep sama)
        sm.editProduct(product2.getIdProduct(),product3);
        sm.showProducts();

//        Driver untuk query search
        try{
            ArrayList<Product> results = sm.searchItemByName("Fitness");
            System.out.println("Hasil query search : ");
            for(Product product : results){
                System.out.println(product.getProductName());
            }
        }catch (ItemNotFoundException e){
            System.out.println(e.getErrorCode());
        }

        try{
            ArrayList<Product> results = sm.searchItemByName("Hahahahhahaha");
            System.out.println("Hasil query search : ");
            for(Product product : results){
                System.out.println(product.getProductName());
            }
        }catch (ItemNotFoundException e){
            System.out.println(e.getErrorCode());
        }

    }
}
