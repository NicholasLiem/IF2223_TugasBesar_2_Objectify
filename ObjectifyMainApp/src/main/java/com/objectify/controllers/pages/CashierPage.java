package com.objectify.controllers.pages;

import com.objectify.models.entities.*;
import com.objectify.models.items.*;
import javafx.geometry.Rectangle2D;
import com.objectify.models.transactions.TransactionHistory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.scene.Cursor;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class CashierPage extends GridPane {

    private Label charge;

    public CashierPage() {
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100);
        getColumnConstraints().add(columnConstraints);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100);
        getRowConstraints().add(rowConstraints);

        Path cssPath = Paths.get("ObjectifyMainApp", "src", "resources", "css", "cashierPage.css");
        String cssUrl = cssPath.toUri().toString();
        this.getStylesheets().add(cssUrl);

        HBox row = new HBox();
        row.setPadding(new Insets(10));
        row.setSpacing(10);

        StorageManager storage = StorageManager.getInstance();
        ArrayList<Product> products = storage.getProducts();

        // Product list
        FlowPane productFlowPane = new FlowPane();
        productFlowPane.setPadding(new Insets(10));
        productFlowPane.setHgap(20);
        productFlowPane.setVgap(20);
        for (int i = 0; i < 15; i++) {
            File file = new File(
                    ".\\ObjectifyMainApp\\src\\resources\\images\\image3.jpg");
            Image image = new Image(file.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            Label imageLabel = new Label("Product");
            VBox imageBox = new VBox(imageView, imageLabel);
            imageBox.setSpacing(10);
            imageBox.setAlignment(Pos.CENTER);
            imageBox.setOnMouseClicked(event -> {
                System.out.println("image box Clicked");
            });
            imageBox.setOnMouseEntered(event -> {
                imageBox.setCursor(Cursor.HAND);
            });
            imageBox.setOnMouseExited(event -> {
                imageBox.setCursor(Cursor.DEFAULT);
            });
            productFlowPane.getChildren().add(imageBox);
        }
        ScrollPane productsScrollPane = new ScrollPane(productFlowPane);
        productsScrollPane.setFitToHeight(true);
        productsScrollPane.setFitToWidth(true);
        productsScrollPane.setPadding(new Insets(10));
        productsScrollPane.getStyleClass().add("mainBoxes");

        Screen screen = Screen.getPrimary();
        Rectangle2D screenSize = screen.getVisualBounds();

        productsScrollPane.setPrefHeight(screenSize.getHeight() * 0.7);
        productsScrollPane.setPrefWidth(screenSize.getWidth() * 0.4);
        HBox.setHgrow(productsScrollPane, Priority.ALWAYS);

        // fill bill
        VBox bill = new VBox();
        bill.setPadding(new Insets(10));
        bill.setAlignment(Pos.CENTER);
        bill.setPrefWidth(screenSize.getWidth() * 0.4);
        bill.setPrefHeight(screenSize.getHeight() * 0.7);
        bill.setSpacing(10);
        bill.getStyleClass().add("mainBoxes");
        VBox.setVgrow(bill, Priority.ALWAYS);

        // show all bills and choose customer row
        HBox addCustomerRow = new HBox();
        HBox.setHgrow(addCustomerRow, Priority.SOMETIMES);

        VBox openBills = new VBox(new Text("Bills"));
        openBills.setAlignment(Pos.CENTER);
        openBills.getStyleClass().add("billButtons");
        openBills.setOnMouseClicked((event) -> {
            System.out.println("Open bills clicked");
        });
        openBills.setOnMouseEntered((event) -> {
            openBills.setCursor(Cursor.HAND);
        });
        openBills.setOnMouseExited((event) -> {
            openBills.setCursor(Cursor.DEFAULT);
        });
        HBox.setHgrow(openBills, Priority.ALWAYS);
        openBills.setPadding(new Insets(20));

        VBox addCustomerButton = new VBox(new Text("+Add Customer"));
        addCustomerButton.setAlignment(Pos.CENTER);
        addCustomerButton.getStyleClass().add("billButtons");
        addCustomerButton.setOnMouseClicked((event) -> {
            System.out.println("add customer clicked");
        });
        addCustomerButton.setOnMouseEntered((event) -> {
            addCustomerButton.setCursor(Cursor.HAND);
        });
        addCustomerButton.setOnMouseExited((event) -> {
            addCustomerButton.setCursor(Cursor.DEFAULT);
        });
        HBox.setHgrow(addCustomerButton, Priority.ALWAYS);
        addCustomerButton.setPadding(new Insets(20));

        addCustomerRow.getChildren().addAll(openBills, addCustomerButton);

        VBox cartList = new VBox();
        VBox.setVgrow(cartList, Priority.ALWAYS);
        for (int i = 0; i < 100; i++) {
            HBox itemRow = new HBox();
            HBox itemQty = new HBox();
            Button upQuantityButton = new Button("+");
            Button decQuantityButton = new Button("-");
            Text qty = new Text("1");
            qty.setTextAlignment(TextAlignment.CENTER);
            itemRow.setSpacing(400);
            HBox.setHgrow(itemQty, Priority.ALWAYS);
            itemQty.setSpacing(20);
            itemQty.setAlignment(Pos.CENTER);
            itemQty.getChildren().addAll(decQuantityButton, qty, upQuantityButton);
            itemRow.setAlignment(Pos.CENTER);
            itemRow.getChildren().addAll(new Text("Product"), itemQty);
            cartList.getChildren().add(itemRow);
        }
        cartList.setSpacing(10);
        ScrollPane itemListScrollPane = new ScrollPane(cartList);
        itemListScrollPane.setFitToHeight(true);
        itemListScrollPane.setFitToWidth(true);
        itemListScrollPane.setPadding(new Insets(10));

        // save, finalize bill
        HBox saveNFinalizeRow = new HBox();
        HBox.setHgrow(addCustomerRow, Priority.SOMETIMES);

        VBox saveBill = new VBox(new Text("Save Bill"));
        saveBill.setAlignment(Pos.CENTER);
        saveBill.getStyleClass().add("billButtons");
        saveBill.setOnMouseClicked((event) -> {
            System.out.println("Save bill clicked");
        });
        saveBill.setOnMouseEntered((event) -> {
            saveBill.setCursor(Cursor.HAND);
        });
        saveBill.setOnMouseExited((event) -> {
            saveBill.setCursor(Cursor.DEFAULT);
        });
        HBox.setHgrow(saveBill, Priority.ALWAYS);
        saveBill.setPadding(new Insets(20));

        VBox finalizeBillButton = new VBox(new Text("Finalize"));
        finalizeBillButton.setAlignment(Pos.CENTER);
        finalizeBillButton.getStyleClass().add("billButtons");
        finalizeBillButton.setOnMouseClicked((event) -> {
            System.out.println("Finalize clicked");
        });
        finalizeBillButton.setOnMouseEntered((event) -> {
            finalizeBillButton.setCursor(Cursor.HAND);
        });
        finalizeBillButton.setOnMouseExited((event) -> {
            finalizeBillButton.setCursor(Cursor.DEFAULT);
        });
        HBox.setHgrow(finalizeBillButton, Priority.ALWAYS);
        finalizeBillButton.setPadding(new Insets(20));

        saveNFinalizeRow.getChildren().addAll(saveBill, finalizeBillButton);

        // Show current charge
        HBox chargeBox = new HBox(new Text("Charge Rp" + this.charge));
        chargeBox.setPadding(new Insets(20));
        chargeBox.setAlignment(Pos.CENTER);
        chargeBox.getStyleClass().add("billButtons");
        HBox.setHgrow(chargeBox, Priority.ALWAYS);

        bill.getChildren().addAll(addCustomerRow, itemListScrollPane, saveNFinalizeRow, chargeBox);

        row.getChildren().addAll(productsScrollPane, bill);
        this.getChildren().add(row);
    }

    private void addItem() {

    }
}
