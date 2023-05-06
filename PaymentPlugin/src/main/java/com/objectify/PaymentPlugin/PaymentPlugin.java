package com.objectify.PaymentPlugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.objectify.datastore.Settings;
import com.objectify.datastore.SystemPointOfSales;
import com.objectify.plugin.Plugin;

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

            // Tinggal pake payments yang udah di load this.payments;

//            String[] options = new String[currencies.size()];
//            for (int i = 0; i < options.length; i++) {
//                options[i] = currencies.get(i).getName();
//            }
//            settings.getUiConfig().add(new ComboBoxBuilder("currency", "Currency", options, options[0]));

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
        try {
            ObjectMapper mapper = new ObjectMapper();
            Path paymentPath = Paths.get(spos.getSettings().getSettingsPath(), PAYMENT_SETTINGS_PATH);
            OutputStream output = new FileOutputStream(paymentPath.toFile());
            mapper.writerWithDefaultPrettyPrinter().writeValue(output, payment);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadPayment(SystemPointOfSales spos) {
        ObjectMapper mapper = new ObjectMapper();

        Path paymentPath = Paths.get(spos.getSettings().getSettingsPath(), "Payment.json");
        if (!paymentPath.toFile().exists()) {
            // Payment file doesn't exist, create it with default values
            try (InputStream inputStream = getClass().getResourceAsStream("/Payment.json")) {
                Payment defaultPayment = new Payment(1.0, 0.1, 0.1 );
                OutputStream paymentOutput = new FileOutputStream(paymentPath.toFile());
                mapper.writerWithDefaultPrettyPrinter().writeValue(paymentOutput, defaultPayment);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        // Payment file exists, read values from it
        try (InputStream input = new FileInputStream(paymentPath.toFile())) {
            this.payment = mapper.readValue(input, Payment.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
