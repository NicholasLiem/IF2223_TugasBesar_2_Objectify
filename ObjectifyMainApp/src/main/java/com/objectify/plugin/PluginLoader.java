package com.objectify.plugin;

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
        Constructor<?> pluginConstructor = pluginClass.getConstructor();
        Object pluginInstance = pluginConstructor.newInstance();

        Plugin plugin = (Plugin) pluginInstance;
        System.out.println("Plugin " + plugin.getName() + " has been successfully loaded!");
        plugin.onEnable(systemPOS);
    }

}