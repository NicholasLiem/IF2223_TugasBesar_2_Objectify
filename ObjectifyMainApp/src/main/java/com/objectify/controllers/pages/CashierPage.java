package com.objectify.controllers.pages;

import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import com.objectify.datastore.SystemPointOfSales;

public class CashierPage extends Pane {


    public CashierPage() {
        super();
        getChildren().add(new Label("" + SystemPointOfSales.getInstance().getSettings().getCalculator().calculate(10000)));
    }
}
