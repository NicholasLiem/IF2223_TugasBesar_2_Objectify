package com.objectify.controllers;

import com.objectify.controllers.scenes.LandingScene;
import com.objectify.controllers.scenes.MainScene;
import com.objectify.datastore.SystemPointOfSales;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    private final MainScene mainScene = new MainScene();

    @Override
    public void start(Stage primaryStage) throws Exception {
        SystemPointOfSales.getInstance().setApp(this);

//        PluginLoader pluginLoader = new PluginLoader();
//        String currentWorkingDirectory = System.getProperty("user.dir");
//        String pluginJarFilePath = Paths.get(currentWorkingDirectory, "PaymentPlugin", "target").toString();
//        pluginLoader.loadPlugins(pluginJarFilePath);
//
//        pluginJarFilePath = Paths.get(currentWorkingDirectory, "CurrencyPlugin", "target").toString();
//        pluginLoader.loadPlugins(pluginJarFilePath);

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