package com.objectify.datastore;

import javafx.scene.Node;
import javafx.scene.control.Label;

public interface SettingComponentProvider {
    Label getLabel();
    Node getInputControl();
}
