package com.objectify.controllers.pages;

import com.objectify.models.items.Category;
import com.objectify.models.items.CategoryManager;
import com.objectify.models.items.Product;
import com.objectify.models.items.StorageManager;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

public class ProductManagerPage extends GridPane {

    private Label nameLabel;
    private Label stockLabel;
    private Label priceLabel;
    private Label buyPriceLabel;
    private Label categoryLabel;
    private Label imagePathLabel;

    private TextField nameField;
    private TextField stockField;
    private TextField priceField;
    private TextField buyPriceField;
    private ComboBox<Category> categoryComboBox;
    private TextField imagePathField;

    public ProductManagerPage() {
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);

        // Create labels for product fields
        nameLabel = new Label("Name:");
        stockLabel = new Label("Stock:");
        priceLabel = new Label("Price:");
        buyPriceLabel = new Label("Buy Price:");
        categoryLabel = new Label("Category:");
        imagePathLabel = new Label("Image Path:");

        // Create text fields for product fields
        nameField = new TextField();
        stockField = new TextField();
        priceField = new TextField();
        buyPriceField = new TextField();
        categoryComboBox = new ComboBox<>();
        imagePathField = new TextField();

        // Add categories to combo box
        CategoryManager categoryManager = CategoryManager.getInstance();
        categoryComboBox.getItems().addAll(categoryManager.getCategories());

        // Add product fields to grid pane
        add(nameLabel, 0, 0);
        add(nameField, 1, 0);
        add(stockLabel, 0, 1);
        add(stockField, 1, 1);
        add(priceLabel, 0, 2);
        add(priceField, 1, 2);
        add(buyPriceLabel, 0, 3);
        add(buyPriceField, 1, 3);
        add(categoryLabel, 0, 4);
        add(categoryComboBox, 1, 4);
        add(imagePathLabel, 0, 5);
        add(imagePathField, 1, 5);

        // Set font size for labels and fields
        nameLabel.setFont(Font.font(12));
        stockLabel.setFont(Font.font(12));
        priceLabel.setFont(Font.font(12));
        buyPriceLabel.setFont(Font.font(12));
        categoryLabel.setFont(Font.font(12));
        imagePathLabel.setFont(Font.font(12));
        nameField.setFont(Font.font(12));
        stockField.setFont(Font.font(12));
        priceField.setFont(Font.font(12));
        buyPriceField.setFont(Font.font(12));
        imagePathField.setFont(Font.font(12));

        // Set fixed row heights
        this.getRowConstraints().clear();
        RowConstraints row1 = new RowConstraints(30);
        RowConstraints row2 = new RowConstraints(30);
        RowConstraints row3 = new RowConstraints(30);
        RowConstraints row4 = new RowConstraints(30);
        RowConstraints row5 = new RowConstraints(30);
        RowConstraints row6 = new RowConstraints(30);
        this.getRowConstraints().addAll(row1, row2, row3, row4, row5, row6);

        Button submitButton = new Button("Add Product");
        submitButton.setOnAction(event -> {
            StorageManager productManager = StorageManager.getInstance();

            // Parse input fields
            String name = nameField.getText();
            int stock = 0;
            double price = 0.0;
            double buyPrice = 0.0;
            Category category = categoryComboBox.getValue();
            String imagePath = imagePathField.getText();

            try {
                stock = Integer.parseInt(stockField.getText());
                price = Double.parseDouble(priceField.getText());
                buyPrice = Double.parseDouble(buyPriceField.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid input");
                alert.setContentText("Stock, price, and buy price must be numbers");
                alert.showAndWait();
                return;
            }

            // Create new product
            Product product = new Product(stock, name, price, buyPrice, category, imagePath);

            // Add product to product manager
            productManager.addNewProducts(product);

            // Clear input fields
            clearFields();

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Product added");
            alert.setContentText("The new product has been added successfully");
            alert.showAndWait();
        });
        this.add(submitButton, 0, 6, 3, 1);
    }

    // Helper method to clear input fields
    private void clearFields() {
        nameField.clear();
        stockField.clear();
        priceField.clear();
        buyPriceField.clear();
        categoryComboBox.setValue(null);
        imagePathField.clear();
    }
}