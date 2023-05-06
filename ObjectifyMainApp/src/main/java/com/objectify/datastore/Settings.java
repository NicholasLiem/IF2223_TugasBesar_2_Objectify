package com.objectify.datastore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Settings {
    private final String settingsPath = "ObjectifyMainApp/src/resources/settings/";
    private final Map<String, Object> metadata;
    private Map<String, Object> additionalProperties;

    @JsonIgnore
    private BillCalculator calculator;
    
    @JsonIgnore
    private final List<SettingComponentProvider> components;

    public Settings(){
        this.additionalProperties = new HashMap<>();
        this.components = new ArrayList<>();
        this.metadata = new HashMap<>();
        this.calculator = value -> value;
    }

    public void setCalculator(BillCalculator calculator) {
        this.calculator = calculator;
    }

    public BillCalculator getCalculator() {
        return calculator;
    }

    public String getSettingsPath(){
        return this.settingsPath;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public List<SettingComponentProvider> getComponents() {
        return components;
    }
}
