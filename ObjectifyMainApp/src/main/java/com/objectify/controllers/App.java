package com.objectify.controllers;

import com.objectify.controllers.scenes.LandingScene;
import com.objectify.datastore.Settings;
import com.objectify.datastore.SystemPointOfSales;
import com.objectify.models.entities.UserManager;
import com.objectify.models.items.CategoryManager;
import com.objectify.models.items.StorageManager;
import com.objectify.models.transactions.TransactionManager;
import com.objectify.plugin.PluginLoader;
import javafx.application.Application;
import com.objectify.controllers.scenes.MainScene;
import javafx.stage.Stage;

public class App extends Application {

    private MainScene mainScene = new MainScene();
    @Override
    public void start(Stage primaryStage) throws Exception {
        SystemPointOfSales systemPointOfSales = SystemPointOfSales.getInstance();
        systemPointOfSales.setApp(this);
        PluginLoader pluginLoader = new PluginLoader();
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

    public MainScene getMainScene() {
        return mainScene;
    }
}