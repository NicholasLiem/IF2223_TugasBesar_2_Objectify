package com.objectify.plugin;


public abstract class Plugin {

    public String name;
    public Plugin(String name){
        this.name = name;
    }

    abstract public void onEnable();
    abstract public void onDisable();

    public String getName(){
        return this.name;
    }

}
