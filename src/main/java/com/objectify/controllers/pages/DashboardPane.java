package com.objectify.controllers.pages;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class DashboardPane extends StackPane {
    public DashboardPane() {
        Text dashboardText = new Text("Main Dashboard");
        getChildren().add(dashboardText);
    }
}