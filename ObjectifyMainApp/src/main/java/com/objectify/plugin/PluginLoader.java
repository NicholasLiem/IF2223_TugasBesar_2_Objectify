package com.objectify.plugin;

import com.objectify.datastore.SystemPointOfSales;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.jar.JarFile;

public class PluginLoader {
    public static HashSet<Plugin> plugins = new HashSet<>();

    public void loadPlugins(String pluginsFolder) throws Exception {
        File pluginDirectory = new File(pluginsFolder);
        File[] files = pluginDirectory.listFiles((dir, name) -> name.endsWith(".jar"));
        Arrays.sort(files, Comparator.comparing(File::getName));

        if (files != null && files.length > 0){
            ArrayList<URL> urls = new ArrayList<>(files.length);
            for (File file : files){
                URL url = file.toURI().toURL();
                urls.add(url);
            }
            URLClassLoader urlClassLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]));
            for (File file : files){
                JarFile jar = new JarFile(file);
                jar.stream().forEach(jarEntry -> {
                    if(jarEntry.getName().endsWith(".class")){
                        try {
                            Class<?> cls = urlClassLoader.loadClass(jarEntry.getName().replaceAll("/", ".").replace(".class", ""));
                            if(Plugin.class.isAssignableFrom(cls)){
                                Constructor<?> constructor = cls.getConstructor();
                                Plugin plugin = (Plugin) constructor.newInstance();
                                plugins.add(plugin);
                                plugin.onEnable(SystemPointOfSales.getInstance());
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        }
    }
}
