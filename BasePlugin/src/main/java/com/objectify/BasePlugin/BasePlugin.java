package com.objectify.BasePlugin;

import com.objectify.controllers.components.menubar.MenuBarManager;
import com.objectify.datastore.Command;
import com.objectify.datastore.SystemPointOfSales;
import com.objectify.exceptions.AppNotFoundException;
import com.objectify.exceptions.InvalidArgumentsException;
import com.objectify.exceptions.ItemNotFoundException;
import com.objectify.plugin.Plugin;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;

public class BasePlugin extends Plugin {

    private final Command newMenuItem;

    public BasePlugin() {
        newMenuItem = (spos, args) -> {
            String menuName = null;
            try {
                MenuBarManager mbManager = spos
                        .getApp()
                        .getMainScene()
                        .getMbManager();
                menuName = (String) args[0];
                String itemName = (String) args[1];
                Tab tab = (Tab) args[2];
                mbManager.getMenu(menuName).addItem(new MenuItem(itemName), tab);
            } catch (AppNotFoundException e) {
                throw new RuntimeException(e);
            } catch (ClassCastException | ArrayIndexOutOfBoundsException e) {
                throw new InvalidArgumentsException("expected arguments (String menuName, String itemName, Tab tab)");
            } catch (ItemNotFoundException e) {
                throw new InvalidArgumentsException("no menu with name `" + menuName + "`");
            }
        };
    }

    @Override
    public void onEnable(SystemPointOfSales spos) {
        spos.registerCommand("BasePlugin.NewMenuItem", newMenuItem);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> onDisable(spos)));
    }

    @Override
    public void onDisable(SystemPointOfSales spos) {

    }
}