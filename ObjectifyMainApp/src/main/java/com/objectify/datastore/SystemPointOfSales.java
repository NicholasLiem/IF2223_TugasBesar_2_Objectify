package com.objectify.datastore;

import com.objectify.controllers.App;
import com.objectify.exceptions.AppNotFoundException;
import com.objectify.exceptions.InvalidArgumentsException;
import com.objectify.exceptions.ItemNotFoundException;
import com.objectify.models.entities.UserManager;
import com.objectify.models.items.CategoryManager;
import com.objectify.models.items.StorageManager;
import com.objectify.models.transactions.BillManager;
import com.objectify.models.transactions.TransactionManager;
import com.objectify.plugin.PluginLoader;

import java.util.HashMap;

public class SystemPointOfSales {

    private final Settings settings;
    private final UserManager userManager;
    private final CategoryManager categoryManager;
    private final StorageManager storageManager;

    private final BillManager billManager;
    private final TransactionManager transactionManager;

    //    private final PluginLoader pluginLoader;
    private final HashMap<String, Command> commands;

    private App app;

    private SystemPointOfSales(){
        this.billManager = new BillManager();
        this.settings = new Settings();
        this.userManager = new UserManager();
        this.categoryManager = new CategoryManager();
        this.storageManager = new StorageManager();
        this.transactionManager = new TransactionManager();
        this.commands = new HashMap<>();
//        this.pluginLoader = new PluginLoader();
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

    public BillManager getBillManager(){
        return billManager;
    }

//    public PluginLoader getPluginLoader() {
//        return pluginLoader;
//    }
    
    public void registerCommand(String name, Command command) {
        this.commands.put(name, command);
    }
    
    public void executeCommand(String name, Object... args) throws ItemNotFoundException, InvalidArgumentsException {
        if (this.commands.get(name) == null) {
            throw new ItemNotFoundException();
        }
        this.commands.get(name).executeCommand(this, args);
    }
}
