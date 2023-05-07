package com.objectify.PaymentPlugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.objectify.datastore.interfaces.InputControl;
import com.objectify.datastore.Settings;
import com.objectify.datastore.SystemPointOfSales;
import com.objectify.plugin.Plugin;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PaymentPlugin extends Plugin {
    private static final String PAYMENT_SETTINGS_PATH = "Payment.json";
    private static final String SETTINGS_PATH = "Settings.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    private Payment payment;

    public PaymentPlugin() {};

    @Override
    public void onEnable(SystemPointOfSales spos) {
        loadPayment(spos);
        Settings settings = spos.getSettings();
        try {
            // Check if Payment.json file exists, and if not, create it with default values
            Path paymentPath = Paths.get(settings.getSettingsPath() + PAYMENT_SETTINGS_PATH);
            if (!paymentPath.toFile().exists()) {
                ObjectNode paymentNode = mapper.createObjectNode();
                OutputStream paymentOutput = new FileOutputStream(paymentPath.toFile());
                mapper.writerWithDefaultPrettyPrinter().writeValue(paymentOutput, paymentNode);
            }

            settings.getAdditionalProperties().put("PaymentSystem", mapper.createObjectNode()
                    .put("PaymentSettings", paymentPath.toString()));

            settings.getComponents().add(new InputControl() {
                @Override
                public Label getLabel() {
                    return new Label("Tax");
                }
                @Override
                public Node getInputControl() {
                    TextField numberTextField = new TextField();
                    numberTextField.setText(Double.toString(payment.getTax()));
                    spos.getSettings().addCalculator("TaxCalculator", value -> value * (1 + payment.getTax()));
                    numberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (!newValue.matches("\\d*\\.?\\d*")) {
                            numberTextField.setText(oldValue);
                        } else {
                            payment.setTax(Double.parseDouble(newValue));
                            spos.getSettings().addCalculator("TaxCalculator", value -> value * (1 + payment.getTax()));
                        }
                    });
                    return numberTextField;
                }
            });

            settings.getComponents().add(new InputControl() {
                @Override
                public Label getLabel() {
                    return new Label("Service Charge");
                }
                @Override
                public Node getInputControl() {
                    TextField numberTextField = new TextField();
                    numberTextField.setText(Double.toString(payment.getTax()));
                    spos.getSettings().addCalculator("ServiceChargeCalculator", value -> value * (1 + payment.getServiceCharge()));
                    numberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (!newValue.matches("\\d*\\.?\\d*")) {
                            numberTextField.setText(oldValue);
                        } else {
                            payment.setTax(Double.parseDouble(newValue));
                            spos.getSettings().addCalculator("ServiceChargeCalculator", value -> value * (1 + payment.getServiceCharge()));
                        }
                    });
                    return numberTextField;
                }
            });

//            settings.getBillComponents().add(new InputControl() {
//                @Override
//                public Label getLabel() {
//                    return new Label("Discount");
//                }
//                @Override
//                public Node getInputControl() {
//                    TextField numberTextField = new TextField();
//                    numberTextField.setText(Double.toString(payment.getDiscount()));
//                    spos.getSettings().addCalculator("DiscountCalculator", value -> value * (1 - payment.getDiscount()));
//                    numberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
//                        if (!newValue.matches("\\d*\\.?\\d*")) {
//                            numberTextField.setText(oldValue);
//                        } else {
//                            payment.setTax(Double.parseDouble(newValue));
//                            spos.getSettings().addCalculator("DiscountCalculator", value -> value * (1 - payment.getDiscount()));
//                        }
//                    });
//                    return numberTextField;
//                }
//            });


            Path settingsPath = Paths.get(settings.getSettingsPath() + SETTINGS_PATH);
            OutputStream output = new FileOutputStream(settingsPath.toFile());
            mapper.writerWithDefaultPrettyPrinter().writeValue(output, settings);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Payment Plugin" + " has been enabled!");

        // Register a shutdown hook to run the onDisable method when the program closes
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                onDisable(spos);
            }
        }));
    }

    @Override
    public void onDisable(SystemPointOfSales spos) {
        System.out.println("Payment Plugin" + " has been disabled!");

        // Write the current Payment object to the JSON file
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            Path paymentPath = Paths.get(spos.getSettings().getSettingsPath(), PAYMENT_SETTINGS_PATH);
//            OutputStream output = new FileOutputStream(paymentPath.toFile());
//            mapper.writerWithDefaultPrettyPrinter().writeValue(output, payment);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    public void loadPayment(SystemPointOfSales spos) {
        ObjectMapper mapper = new ObjectMapper();

        Path currencyPath = Paths.get(spos.getSettings().getSettingsPath() + PAYMENT_SETTINGS_PATH);
        if (!currencyPath.toFile().exists()) {
            // Currency file doesn't exist, create it with default values
            try (InputStream inputStream = getClass().getResourceAsStream("/Payment.json")) {
                Payment defaultPayment = mapper.readValue(inputStream, Payment.class);
                OutputStream currencyOutput = new FileOutputStream(currencyPath.toFile());
                mapper.writerWithDefaultPrettyPrinter().writeValue(currencyOutput, defaultPayment);
                this.payment = defaultPayment;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        // Currency file exists, read values from it
        try (InputStream input = new FileInputStream(currencyPath.toFile())) {
            Payment currencyNode = mapper.readValue(input, Payment.class);
            this.payment = currencyNode;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
