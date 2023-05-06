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

import java.nio.file.Paths;

public class App extends Application {

    private final MainScene mainScene = new MainScene();

    @Override
    public void start(Stage primaryStage) throws Exception {
        SystemPointOfSales.getInstance().setApp(this);
        
        String currentWorkingDirectory = System.getProperty("user.dir");
        String pluginJarFilePath = Paths.get(currentWorkingDirectory, "plugins").toString();

        SystemPointOfSales.getInstance().getPluginLoader().loadPlugins(pluginJarFilePath);
        LandingScene landingScene = new LandingScene(primaryStage, mainScene);

        primaryStage.setScene(landingScene);
        primaryStage.setTitle("Objectify Manager");
        primaryStage.show();
    }

    public MainScene getMainScene() {
        return this.mainScene;
    }
}