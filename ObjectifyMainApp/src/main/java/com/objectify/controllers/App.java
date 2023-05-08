package com.objectify.controllers;

import com.objectify.controllers.scenes.LandingScene;
import com.objectify.controllers.scenes.MainScene;
import com.objectify.datastore.SystemPointOfSales;
import com.objectify.models.items.Category;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    private final MainScene mainScene = new MainScene();

    @Override
    public void start(Stage primaryStage) {
        SystemPointOfSales.getInstance().setApp(this);
        LandingScene landingScene = new LandingScene(primaryStage, mainScene);

        /* Initalizing Datastore */
        System.out.println("Initializing data with JSON datastore (default)");
        SystemPointOfSales.getInstance().getSettings().initialiseDataStores("JSON");
        SystemPointOfSales.getInstance().getSettings().loadAllDataStore();
        initializeDefaultCategories();


        primaryStage.setScene(landingScene);
        primaryStage.setTitle("Objectify Manager");
        primaryStage.show();
    }

    public MainScene getMainScene() {
        return mainScene;
    }
    public void initializeDefaultCategories(){
        Category makanan = new Category("Makanan");
        Category minuman = new Category("Minuman");
        Category barang = new Category("Barang");
        Category cemilan = new Category("Cemilan");
        if (SystemPointOfSales.getInstance().getCategoryManager().getCategories().contains(makanan)){
            return;
        }
        // Set Default Category

        SystemPointOfSales.getInstance().getCategoryManager().addCategory(makanan);
        SystemPointOfSales.getInstance().getCategoryManager().addCategory(minuman);
        SystemPointOfSales.getInstance().getCategoryManager().addCategory(barang);
        SystemPointOfSales.getInstance().getCategoryManager().addCategory(cemilan);
    }
}