package com.objectify.plugin;

import com.objectify.controllers.App;
import com.objectify.datastore.SystemPointOfSales;

public abstract class Plugin {

    public String name;
    public Plugin(String name){
        this.name = name;
    }

    abstract public void onEnable(SystemPointOfSales systemPOS);
    abstract public void onDisable();

}
