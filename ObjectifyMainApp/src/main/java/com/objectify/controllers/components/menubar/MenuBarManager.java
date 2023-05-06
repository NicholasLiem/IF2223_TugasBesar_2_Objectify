package com.objectify.controllers.components.menubar;

import com.objectify.controllers.components.menus.AdminMenu;
import com.objectify.controllers.components.menus.AppMenu;
import com.objectify.controllers.components.menus.FileMenu;
import com.objectify.controllers.components.menus.PluginMenu;
import com.objectify.exceptions.ItemNotFoundException;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;

import java.util.HashMap;

public class MenuBarManager {
    private MenuBar menuBar;
    private TabPane tabPane;
    private HashMap<String, AppMenu> menus;

    public MenuBarManager(TabPane tabPane) {
        menuBar = new MenuBar();
        this.tabPane = tabPane;
        menus = new HashMap<>();
        initialiseMenus();
    }

    public void addMenu(AppMenu menu) {
        menuBar.getMenus().add(menu);
        menus.put(menu.getText(), menu);
    }

    public void removeMenu(AppMenu menu) {
        menuBar.getMenus().remove(menu);
        menus.remove(menu.getText());
    }

    public void addMenuItem(Menu menu, MenuItem menuItem) {
        menu.getItems().add(menuItem);
    }

    public void removeMenuItem(AppMenu menu, MenuItem menuItem) {
        menu.getItems().remove(menuItem);
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public void initialiseMenus(){
        FileMenu fm = new FileMenu(this.tabPane);
        AdminMenu am = new AdminMenu(this.tabPane);
        PluginMenu pm = new PluginMenu(this.tabPane);
        addMenu(fm);
        addMenu(am);
        addMenu(pm);
    }
    
    public AppMenu getMenu(String name) throws ItemNotFoundException {
        if (menus.get(name) == null) {
            throw new ItemNotFoundException();
        }
        return menus.get(name);
    }

}