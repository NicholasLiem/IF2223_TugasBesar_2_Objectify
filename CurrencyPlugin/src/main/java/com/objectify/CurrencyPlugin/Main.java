package com.objectify.CurrencyPlugin;

import com.objectify.controllers.App;
import com.objectify.plugin.Plugin;

public class Main extends Plugin {

    public Main(String name) {
        super(name);
    }

    @Override
    public void onEnable(App appContext) {
        appContext.getSettings().setCurrency("USD");
        System.out.println("Enabled");
    }

    @Override
    public void onDisable() {
        System.out.println("Disabled");
    }
}
