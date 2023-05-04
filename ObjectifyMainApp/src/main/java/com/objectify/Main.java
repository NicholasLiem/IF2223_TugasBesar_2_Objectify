package com.objectify;

import com.objectify.controllers.App;
import com.objectify.datastore.Settings;
import com.objectify.plugin.PluginLoader;
import javafx.application.Application;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args){
        Application.launch(App.class, args);
    }
}
