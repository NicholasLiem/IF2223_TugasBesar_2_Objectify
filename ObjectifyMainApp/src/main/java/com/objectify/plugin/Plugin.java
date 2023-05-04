package com.objectify.plugin;

import com.objectify.controllers.App;

public abstract class Plugin {

    public String name;
    public Plugin(String name){
        this.name = name;
    }

    abstract public void onLoad(App appContext);
    abstract public void onDisable();

}
