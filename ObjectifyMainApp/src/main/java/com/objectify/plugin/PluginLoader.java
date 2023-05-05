package com.objectify.plugin;

import com.objectify.controllers.App;
import com.objectify.datastore.SystemPointOfSales;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginLoader {
    private SystemPointOfSales systemPOS;
    public PluginLoader(){
        systemPOS = SystemPointOfSales.getInstance();
    }
    public void loadPlugin(String jarFilePath, String mainClass) throws Exception {
        URL jarUrl = new File(jarFilePath).toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[] {jarUrl});

        Class<?> pluginClass = classLoader.loadClass(mainClass);
        Constructor<?> pluginConstructor = pluginClass.getConstructor(String.class);
        Object pluginInstance = pluginConstructor.newInstance("Plugin");

        Plugin plugin = (Plugin) pluginInstance;
        plugin.onEnable(systemPOS);
    }

}