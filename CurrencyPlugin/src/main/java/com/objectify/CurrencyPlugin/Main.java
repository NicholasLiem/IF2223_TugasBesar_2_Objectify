package com.objectify.CurrencyPlugin;

import com.objectify.datastore.SystemPointOfSales;
import com.objectify.plugin.Plugin;

public class Main extends Plugin {
    public Main() {
        super("Currency Plugin", "com.objectify.CurrencyPlugin.Main");
    }

    @Override
    public void onEnable(SystemPointOfSales spos) {
        spos.getSettings().setCurrency("USD");
        System.out.println(this.getName() + "has been enabled!");
    }

    @Override
    public void onDisable() {
        System.out.println(this.getName() + "has been disabled!");
    }
}
