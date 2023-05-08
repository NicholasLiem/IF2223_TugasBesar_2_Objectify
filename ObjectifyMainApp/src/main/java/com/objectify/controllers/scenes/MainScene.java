package com.objectify.controllers.scenes;

import com.objectify.controllers.components.menubar.MenuBarManager;
import com.objectify.controllers.pages.ProductManagerPage;
import com.objectify.controllers.pages.RegisterMemberPage;
import com.objectify.datastore.SystemPointOfSales;
import com.objectify.datastore.adapter.JSONAdapter;
import com.objectify.datastore.enums.ProductDataStore;
import com.objectify.datastore.enums.UserDataStore;
import com.objectify.datastore.interfaces.DataStore;
import com.objectify.exceptions.InvalidArgumentsException;
import com.objectify.models.entities.UserManager;
import com.objectify.models.items.*;
import com.objectify.models.transactions.Transaction;
import com.objectify.models.transactions.TransactionManager;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

import javax.xml.crypto.Data;
import java.util.HashMap;

public class MainScene extends Scene {
    
    private final MenuBarManager mbManager;
    private final TabPane tabPane;
    
    public MainScene() {
        super(new BorderPane());

        tabPane = new TabPane();
        mbManager = new MenuBarManager(tabPane);

        MenuBar menuBar = mbManager.getMenuBar();

        BorderPane root = (BorderPane) getRoot();
        root.setCenter(tabPane);
        root.setTop(menuBar);

        SystemPointOfSales.getInstance().getUserManager();
        RegisterMemberPage registeMemberPage = new RegisterMemberPage();
        root.setCenter(registeMemberPage);

        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == null) {
                root.setCenter(registeMemberPage);
            } else {
                root.setCenter(tabPane);
            }
        });

        SystemPointOfSales.getInstance().registerCommand("NewTab", (spos, args) -> {
            try {
                Tab tab = (Tab) args[0];
                tabPane.getTabs().add(tab);
                tabPane.getSelectionModel().select(tab);
            } catch (ClassCastException e) {
                throw new InvalidArgumentsException("expected arguments (Tab tab)");
            }
        });
    }
    
    public MenuBarManager getMbManager() {
        return mbManager;
    }
}
