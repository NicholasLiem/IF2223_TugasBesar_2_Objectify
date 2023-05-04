package com.objectify.provider;

import com.objectify.datastore.Settings;
import com.objectify.interfaces.SettingsProvider;

public class SettingsProviderImplementation implements SettingsProvider {

    private final Settings settings;

    public SettingsProviderImplementation(Settings settings) {
        this.settings = settings;
    }

    @Override
    public Settings getSettings() {
        return this.settings;
    }
}
