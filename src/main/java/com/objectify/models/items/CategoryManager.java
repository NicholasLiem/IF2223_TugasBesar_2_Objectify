package com.objectify.models.items;

import java.util.ArrayList;
import java.util.List;

public class CategoryManager {
    private static List<Category> categories = new ArrayList<Category>();

    public static List<Category> getCategories() {
        return categories;
    }

    public static void addCategory(Category category) {
        categories.add(category);
    }

    public static void removeCategory(Category category){
        categories.remove(category);
    }
}