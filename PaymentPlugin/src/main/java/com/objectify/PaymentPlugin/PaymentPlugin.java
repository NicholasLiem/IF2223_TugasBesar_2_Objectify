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

    public PaymentPlugin() {
    }

    @Override
    public void onEnable(SystemPointOfSales spos) {
        try {
            Path settingsPath = Paths.get(spos.getSettings().getSettingsPath());
            InputStream input = new FileInputStream(settingsPath.toFile());
            ObjectMapper mapper = new ObjectMapper();
            Settings settings = mapper.readValue(input, Settings.class);
            ObjectNode paymentPluginNode = mapper.createObjectNode();
            paymentPluginNode.put("taxPercent", 10);
            paymentPluginNode.put("serviceChargePercent", 5);
            settings.getAdditionalProperties().put("Payments", paymentPluginNode);

            // Add discounts to additionalProperties
            ObjectNode discountsNode = mapper.createObjectNode();
            discountsNode.put("A", 5);
            discountsNode.put("B", 10);
            discountsNode.put("C", 15);
            paymentPluginNode.set("discounts", discountsNode);

            OutputStream output = new FileOutputStream(settingsPath.toFile());
            mapper.writerWithDefaultPrettyPrinter().writeValue(output, settings);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Payment plugin" + " has been enabled!");

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
        try {
            Path settingsPath = Paths.get(spos.getSettings().getSettingsPath());
            InputStream input = new FileInputStream(settingsPath.toFile());
            ObjectMapper mapper = new ObjectMapper();
            Settings settings = mapper.readValue(input, Settings.class);

            // Remove PaymentPlugin from additionalProperties
            settings.getAdditionalProperties().remove("Payments");

            OutputStream output = new FileOutputStream(settingsPath.toFile());
            mapper.writerWithDefaultPrettyPrinter().writeValue(output, settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Payment plugin" + " has been disabled!");
    }
}
