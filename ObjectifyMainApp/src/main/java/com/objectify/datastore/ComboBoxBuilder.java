package com.objectify.datastore;

import com.fasterxml.jackson.annotation.JsonTypeName;
import javafx.scene.control.ComboBox;

@JsonTypeName("ComboBox")
public class ComboBoxBuilder extends SettingBuilder<ComboBox<String>> {
    
    private final String[] options;
    private final String value;

    public ComboBoxBuilder(String name, String label, String[] options, String value) {
        super(name, label);
        this.options = options;
        this.value = value;
    }

    @Override
    public ComboBox<String> build() {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setValue(value);
        for (String opt : options) {
            comboBox.getItems().add(opt);
        }
        return comboBox;
    }
}
