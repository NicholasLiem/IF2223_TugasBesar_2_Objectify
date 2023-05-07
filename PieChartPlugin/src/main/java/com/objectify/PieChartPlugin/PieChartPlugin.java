package com.objectify.PieChartPlugin;

import com.objectify.datastore.SystemPointOfSales;
import com.objectify.exceptions.InvalidArgumentsException;
import com.objectify.exceptions.ItemNotFoundException;
import com.objectify.plugin.Plugin;
import javafx.scene.control.Tab;

public class PieChartPlugin extends Plugin {

    @Override
    public void onEnable(SystemPointOfSales spos) {
        System.out.println("PieChartPlugin enabled");
        var pane = new PieChartPane(spos);
        var tab = new Tab("Chart 1", pane);
        try {
            spos.executeCommand("BasePlugin.NewMenuItem", "Plugin", "Chart 2", tab);
        } catch (InvalidArgumentsException e) {
            e.printStackTrace();
        } catch (ItemNotFoundException e) {
            throw new RuntimeException("Base Plugin is not yet loaded");
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> onDisable(spos)));
    }

    @Override
    public void onDisable(SystemPointOfSales spos) {

    }
}
