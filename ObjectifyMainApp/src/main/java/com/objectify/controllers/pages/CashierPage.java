package com.objectify.controllers.pages;

import com.objectify.datastore.Settings;
import com.objectify.datastore.interfaces.BillCalculator;
import com.objectify.datastore.interfaces.InputControl;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import com.objectify.datastore.SystemPointOfSales;

import java.util.Map;

public class CashierPage extends GridPane {
    private final Settings settings;

    public CashierPage() {
        super();
        this.settings = new Settings();
        for (Map.Entry<String, BillCalculator> calculatorEntry : SystemPointOfSales.getInstance().getSettings().getCalculators().entrySet()){
            this.settings.addCalculator(calculatorEntry.getKey(), calculatorEntry.getValue());
        }
        this.add(new Label("" + this.settings.getCalculator().calculate(10000)), 1, 1);
//        int count = 2;
//        for (InputControl ui : SystemPointOfSales.getInstance().getSettings().getBillComponents()) {
//            add(ui.getLabel(), 0, count);
//            add(ui.getInputControl(), 1, count);
//            count++;
//        }
    }
}
