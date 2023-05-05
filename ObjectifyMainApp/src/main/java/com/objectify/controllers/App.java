package com.objectify.controllers;

import com.objectify.controllers.scenes.LandingScene;
import com.objectify.datastore.Settings;
import com.objectify.models.entities.UserManager;
import com.objectify.models.items.CategoryManager;
import com.objectify.models.items.StorageManager;
import com.objectify.models.transactions.TransactionManager;
import com.objectify.plugin.PluginLoader;
import javafx.application.Application;
import com.objectify.controllers.scenes.MainScene;
import javafx.stage.Stage;

public class App extends Application {

    private Settings settings = Settings.getInstance();
    private UserManager userManager = UserManager.getInstance();
    private CategoryManager categoryManager = CategoryManager.getInstance();
    private StorageManager storageManager = StorageManager.getInstance();

    private TransactionManager transactionManager = TransactionManager.getInstance();
    private MainScene mainScene = new MainScene();
    @Override
    public void start(Stage primaryStage) throws Exception {
        PluginLoader pluginLoader = new PluginLoader(this);
        String pluginName = "CurrencyPlugin-v1.0.jar";
        String currentWorkingDirectory = System.getProperty("user.dir");
        String pluginJarFilePath = currentWorkingDirectory + "\\CurrencyPlugin\\target\\" + pluginName;

        pluginLoader.loadPlugin(pluginJarFilePath, "com.objectify.CurrencyPlugin.Main");
        MainScene mainScene = new MainScene();
        LandingScene landingScene = new LandingScene(primaryStage, mainScene);

        primaryStage.setScene(landingScene);
        primaryStage.setTitle("Objectify Manager");
        primaryStage.show();
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

    public MainScene getMainScene() {
        return mainScene;
    }
}