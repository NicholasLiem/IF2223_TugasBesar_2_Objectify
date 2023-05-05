package com.objectify.datastore;

import com.objectify.controllers.App;
import com.objectify.exceptions.AppNotFoundException;
import com.objectify.exceptions.CustomException;
import com.objectify.models.entities.UserManager;
import com.objectify.models.items.CategoryManager;
import com.objectify.models.items.StorageManager;
import com.objectify.models.transactions.BillManager;
import com.objectify.models.transactions.TransactionManager;

public class SystemPointOfSales {
    // Berfungsi sebagai penghubung antara GUI/Komponen lain dengan Data Store.
    // Kelas ini harus dapat membaca pengaturan dari kelas "Settings" dan menggunakan implementasi "DataStore" yang sesuai untuk membaca dan menulis data.
    // Pastikan bahwa GUI/Komponen lain hanya dapat mengakses Data Store melalui "SystemPointOfSales". File hanya boleh dibaca/ditulis oleh Data Store,
    // sehingga GUI/Komponen lain harus menggunakan "SystemPointOfSales" untuk membaca/menulis data.
    private Settings settings;
    private UserManager userManager;
    private CategoryManager categoryManager;
    private StorageManager storageManager;

    private BillManager billManager;
    private TransactionManager transactionManager;
    private App app;

    private SystemPointOfSales(){
        this.billManager = new BillManager();
        this.settings = new Settings();
        this.userManager = new UserManager();
        this.categoryManager = new CategoryManager();
        this.storageManager = new StorageManager();
        this.transactionManager = new TransactionManager();
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

    public App getApp() throws AppNotFoundException {
        if (this.app == null) {
            throw new AppNotFoundException();
        }
        return this.app;
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
