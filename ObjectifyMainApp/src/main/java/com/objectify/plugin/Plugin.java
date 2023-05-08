package com.objectify.plugin;


import com.objectify.datastore.SystemPointOfSales;

public abstract class Plugin {

    abstract public void onEnable(SystemPointOfSales spos);
    abstract public void onDisable(SystemPointOfSales spos);

}

