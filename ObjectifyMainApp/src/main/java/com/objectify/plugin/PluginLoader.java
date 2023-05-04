package com.objectify.plugin;

import com.objectify.datastore.Settings;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginLoader {
    public void loadPlugin(String jarFilePath, Settings settings) throws Exception {
        URL jarUrl = new File(jarFilePath).toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[] {jarUrl});

        Class<?> pluginClass = classLoader.loadClass("com.objectify.Main");

        Object pluginInstance = pluginClass.newInstance();

        Class<?> spiClass = classLoader.loadClass("com.objectify.interfaces.Plugin");
        Object spiInstance = spiClass.newInstance();

        Method loadMethod = pluginClass.getDeclaredMethod("load", Settings.class, spiClass);
        loadMethod.invoke(pluginInstance, settings, spiInstance);
    }
}