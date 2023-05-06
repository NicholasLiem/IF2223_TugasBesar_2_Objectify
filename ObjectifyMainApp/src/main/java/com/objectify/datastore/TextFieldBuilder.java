package com.objectify.datastore;

import com.fasterxml.jackson.annotation.JsonTypeName;
import javafx.scene.control.TextField;

@JsonTypeName("Text")
public class TextFieldBuilder extends SettingBuilder<TextField> {
    
    private String value;

    public TextFieldBuilder(String name, String label, String value) {
        super(name, label);
        this.value = value;
    }

    @Override
    public TextField build() {
        TextField textField = new TextField();
        textField.setText(value);
        return textField;
    }

    public String getValue() {
        return value;
    }
}
