package com.objectify.controllers.components.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;


public class CustomAlert extends Alert {

    public CustomAlert(AlertType alertType) {
        super(alertType);
        initStyle();
    }

    public CustomAlert(AlertType alertType, String contentText, ButtonType... buttons) {
        super(alertType, contentText, buttons);
        initStyle();
    }

    private void initStyle() {
//        getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/path/to/custom.css")).toExternalForm());
        getDialogPane().getStyleClass().add("custom-alert");
        initStyle(StageStyle.TRANSPARENT);
        setHeaderText(null);
        setGraphic(null);
    }
}
