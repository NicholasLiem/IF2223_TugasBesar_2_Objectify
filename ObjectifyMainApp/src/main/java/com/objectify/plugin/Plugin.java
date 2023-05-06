package com.objectify.plugin;


import com.objectify.datastore.SystemPointOfSales;

public abstract class Plugin {

    public String name;
    public Plugin(String name){
        this.name = name;
    }

    abstract public void onEnable(SystemPointOfSales spos);
    abstract public void onDisable();

    public String getName(){
        return this.name;
    }

}
