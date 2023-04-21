package com.objectify;

import com.objectify.datastore.JSONAdapter;
import com.objectify.datastore.enums.JSONType;
import com.objectify.models.entities.*;
import com.objectify.models.items.Category;
import com.objectify.models.items.CategoryManager;
import com.objectify.models.items.Product;
import com.objectify.models.items.StorageManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JSONAdapter jdatastore = JSONAdapter.getInstance();
        UserManager um = new UserManager();
        StorageManager sm = new StorageManager();
        CategoryManager cm = new CategoryManager();

        Category category1 = new Category("OFFICE_SUPPLIES");
        cm.addCategory(category1);
        Product product1 = new Product(100, "Wooden Desk Organizer", 29.99, 15.00, category1, "images/wooden_desk_organizer.jpg");
        sm.addProduct(product1);

        Category category2 = new Category("ELECTRONICS");
        cm.addCategory(category2);
        Product product2 = new Product(50, "Bluetooth Headphones", 89.99, 50.00, category2, "images/bluetooth_headphones.jpg");
        sm.addProduct(product2);

        Category category3 = new Category("SPORTS");
        cm.addCategory(category3);
        Product product3 = new Product(200, "Yoga Mat", 39.99, 25.00, category3, "images/yoga_mat.jpg");
        sm.addProduct(product3);

        Category category4 = new Category("KITCHENWARE");
        cm.addCategory(category4);
        Product product4 = new Product(500, "Coffee Mug", 12.99, 6.00, category4, "images/coffee_mug.jpg");
        sm.addProduct(product4);

        Category category5 = new Category("ELECTRONICS");
        cm.addCategory(category5);
        Product product5 = new Product(100, "Fitness Tracker", 69.99, 40.00, category5, "images/fitness_tracker.jpg");
        sm.addProduct(product5);

        jdatastore.writeData(JSONType.PRODUCTS.getFileName(), sm.getProducts());
        jdatastore.writeData(JSONType.CATEGORIES.getFileName(), cm.getCategories());
    }
}
