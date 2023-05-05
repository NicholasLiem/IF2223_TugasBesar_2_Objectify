package com.objectify.datastore;

import com.objectify.controllers.App;
import com.objectify.models.entities.UserManager;
import com.objectify.models.items.CategoryManager;
import com.objectify.models.items.StorageManager;
import com.objectify.models.transactions.TransactionManager;

public class SystemPointOfSales {
    // Berfungsi sebagai penghubung antara GUI/Komponen lain dengan Data Store.
    // Kelas ini harus dapat membaca pengaturan dari kelas "Settings" dan menggunakan implementasi "DataStore" yang sesuai untuk membaca dan menulis data.
    // Pastikan bahwa GUI/Komponen lain hanya dapat mengakses Data Store melalui "SystemPointOfSales". File hanya boleh dibaca/ditulis oleh Data Store,
    // sehingga GUI/Komponen lain harus menggunakan "SystemPointOfSales" untuk membaca/menulis data.
    private Settings settings = Settings.getInstance();
    private UserManager userManager = UserManager.getInstance();
    private CategoryManager categoryManager = CategoryManager.getInstance();
    private StorageManager storageManager = StorageManager.getInstance();

    private TransactionManager transactionManager = TransactionManager.getInstance();
    private App app;

    private SystemPointOfSales(){

    }

    private static SystemPointOfSales instance;
    public static synchronized SystemPointOfSales getInstance(){
        if (instance == null){
            instance = new SystemPointOfSales();
        }
        return instance;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Settings getSettings() {
        return settings;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public CategoryManager getCategoryManager() {
        return categoryManager;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }
}
