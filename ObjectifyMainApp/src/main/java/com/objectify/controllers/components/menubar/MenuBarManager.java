package com.objectify.controllers.components.menubar;

import com.objectify.controllers.components.menus.AdminMenu;
import com.objectify.controllers.components.menus.FileMenu;
import com.objectify.controllers.components.menus.PluginMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;

public class MenuBarManager {
    private MenuBar menuBar;
    private TabPane tabPane;

    public MenuBarManager(TabPane tabPane) {
        menuBar = new MenuBar();
        this.tabPane = tabPane;
        initialiseMenus();
    }

    public void addMenu(Menu menu) {
        menuBar.getMenus().add(menu);
    }

    public void removeMenu(Menu menu) {
        menuBar.getMenus().remove(menu);
    }

    public void addMenuItem(Menu menu, MenuItem menuItem) {
        menu.getItems().add(menuItem);
    }

    public void removeMenuItem(Menu menu, MenuItem menuItem) {
        menu.getItems().remove(menuItem);
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public void initialiseMenus(){
        FileMenu fm = new FileMenu(this.tabPane);
        AdminMenu am = new AdminMenu(this.tabPane);
        PluginMenu pm = new PluginMenu(this.tabPane);
        menuBar.getMenus().addAll(fm, am, pm);
    }
}