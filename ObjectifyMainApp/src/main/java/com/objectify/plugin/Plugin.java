package com.objectify.plugin;

import com.objectify.datastore.SystemPointOfSales;

public abstract class Plugin {

    public String name;
    public String mainClass;
    public Plugin(String name, String mainClass){
        this.name = name;
        this.mainClass = mainClass;
    }

    abstract public void onEnable(SystemPointOfSales systemPOS);
    abstract public void onDisable();

    public String getName(){
        return this.name;
    }

    public String getMainClass(){
        return this.mainClass;
    }
}
