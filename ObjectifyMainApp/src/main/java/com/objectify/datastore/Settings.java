package com.objectify.datastore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.objectify.datastore.enums.BillCalculator;
import com.objectify.datastore.enums.InputControl;

public class Settings {
    private final String settingsPath = "ObjectifyMainApp/src/resources/settings/";
    private Map<String, Object> additionalProperties;

    @JsonIgnore
    private final HashMap<String, BillCalculator> calculators;
    
    @JsonIgnore
    private final List<InputControl> components;

    public Settings(){
        this.additionalProperties = new HashMap<>();
        this.components = new ArrayList<>();
        this.calculators = new HashMap<>();
        this.calculators.put("Default", value -> value);
    }

    @JsonIgnore

    public BillCalculator getCalculator() {
        BillCalculator calculator = calculators.get("Default");
        for (final Map.Entry<String, BillCalculator> entry : calculators.entrySet()) {
            final BillCalculator temp = calculator;
            calculator = value -> entry.getValue().calculate(temp.calculate(value));
        }
        return calculator;
    }

    public void addCalculator(String s, BillCalculator bc){
        this.calculators.put(s, bc);
    }

    public void removeCalculator(String s){
        this.calculators.remove(s);
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

    public List<InputControl> getComponents() {
        return components;
    }
}
