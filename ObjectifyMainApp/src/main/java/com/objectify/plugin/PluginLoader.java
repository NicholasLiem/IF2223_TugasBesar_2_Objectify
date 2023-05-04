package com.objectify.plugin;

import com.objectify.controllers.App;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginLoader {
    private App app;
    public PluginLoader(App app){
        this.app = app;
    }
    public void loadPlugin(String jarFilePath) throws Exception {
        URL jarUrl = new File(jarFilePath).toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[] {jarUrl});

        Class<?> pluginClass = classLoader.loadClass("com.objectify.CurrencyPlugin.Main");
        Constructor<?> pluginConstructor = pluginClass.getConstructor(String.class);
        Object pluginInstance = pluginConstructor.newInstance("Currency Plugin");

        Plugin plugin = (Plugin) pluginInstance;
        plugin.onEnable(this.app);
    }

}