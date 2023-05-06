package com.objectify.datastore;


import java.util.HashMap;
import java.util.Map;

public class Settings {
    private final String settingsPath = "ObjectifyMainApp/src/resources/settings/Settings.json";
    private Map<String, Object> additionalProperties;

    public Settings(){
        this.additionalProperties = new HashMap<>();
    }

    public String getSettingsPath() {
        return settingsPath;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}