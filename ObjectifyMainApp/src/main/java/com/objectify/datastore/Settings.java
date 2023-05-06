package com.objectify.datastore;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Settings {
    private final String settingsPath = "ObjectifyMainApp/src/resources/settings/";
    private Map<String, Object> additionalProperties;
    
    private final List<SettingBuilder<?>> uiConfig;

    public Settings(){
        this.additionalProperties = new HashMap<>();
        this.uiConfig = new ArrayList<>();
    }

    public String getSettingsPath(){
        return this.settingsPath;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public List<SettingBuilder<?>> getUiConfig() {
        return uiConfig;
    }
}