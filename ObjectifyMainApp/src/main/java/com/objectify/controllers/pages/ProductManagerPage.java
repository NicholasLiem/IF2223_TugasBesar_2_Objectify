package com.objectify.controllers.pages;

import com.objectify.datastore.SystemPointOfSales;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.objectify.models.items.Category;
import com.objectify.models.items.CategoryManager;
import com.objectify.models.items.Product;
import com.objectify.models.items.StorageManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.Cursor;
import javafx.stage.FileChooser;

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
        Path cssPath = Paths.get("ObjectifyMainApp","src", "resources", "css", "productManager.css");
        String cssUrl = cssPath.toUri().toString();
        this.getStylesheets().add(cssUrl);

//        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100);
        getColumnConstraints().add(columnConstraints);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100);
        getRowConstraints().add(rowConstraints);

        HBox row = new HBox();
        row.setSpacing(20);

        VBox forms = new VBox();
        forms.setSpacing(30);
        forms.setPadding(new Insets(20,20,0,0));

        // Create labels for product fields
        nameLabel = new Label("Name:");
        HBox.setHgrow(nameLabel, Priority.ALWAYS);
        stockLabel = new Label("Stock:");
        HBox.setHgrow(stockLabel, Priority.ALWAYS);
        priceLabel = new Label("Price:");
        HBox.setHgrow(priceLabel, Priority.ALWAYS);
        buyPriceLabel = new Label("Buy Price:");
        HBox.setHgrow(buyPriceLabel, Priority.ALWAYS);
        categoryLabel = new Label("Category:");
        HBox.setHgrow(categoryLabel, Priority.ALWAYS);
        imagePathLabel = new Label("Image Path:");
        HBox.setHgrow(imagePathLabel, Priority.ALWAYS);

        // Create text fields for product fields
        nameField = new TextField();
        HBox.setHgrow(nameField, Priority.ALWAYS);
        stockField = new TextField();
        HBox.setHgrow(stockField, Priority.ALWAYS);
        priceField = new TextField();
        HBox.setHgrow(priceField, Priority.ALWAYS);
        buyPriceField = new TextField();
        HBox.setHgrow(buyPriceField, Priority.ALWAYS);
        categoryComboBox = new ComboBox<>();
        HBox.setHgrow(categoryComboBox, Priority.ALWAYS);
        imagePathField = new TextField();
        HBox.setHgrow(imagePathField, Priority.ALWAYS);

        final String[] selectedImagePath = {""};
        Button imageChooserBtn = new Button("Select Image");
        imageChooserBtn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.bmp"));
            File selectedFile = fileChooser.showOpenDialog(getScene().getWindow());
            if (selectedFile != null) {
                selectedImagePath[0] = selectedFile.getAbsolutePath();
                imagePathField.setText(selectedImagePath[0]);
            }
        });

        HBox nameBox = new HBox(nameLabel, nameField);
        nameBox.setPrefWidth(350);
        nameBox.setSpacing(10);
        HBox stockBox = new HBox(stockLabel, stockField);
        stockBox.setPrefWidth(350);
        stockBox.setSpacing(10);
        HBox priceBox = new HBox(priceLabel, priceField);
        priceBox.setPrefWidth(350);
        priceBox.setSpacing(10);
        HBox buyPriceBox = new HBox(buyPriceLabel, buyPriceField);
        buyPriceBox.setPrefWidth(350);
        buyPriceBox.setSpacing(10);
        HBox categoryBox = new HBox(categoryLabel, categoryComboBox);
        categoryBox.setPrefWidth(350);
        categoryBox.setSpacing(10);
        HBox imagePathBox = new HBox(imagePathLabel, imagePathField, imageChooserBtn);
        imagePathBox.setPrefWidth(350);
        imagePathBox.setSpacing(10);

        forms.getChildren().addAll(nameBox, stockBox, priceBox, buyPriceBox, categoryBox, imagePathBox);

        // Add categories to combo box
        CategoryManager categoryManager = SystemPointOfSales.getInstance().getCategoryManager();
        categoryComboBox.getItems().addAll(categoryManager.getCategories());

        add(row,0,0);
        updateProductsScrollPane();
        row.getChildren().addAll(forms);

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

        submitButton();
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
    
    private void updateProductsScrollPane() {
        StorageManager pManager = SystemPointOfSales.getInstance().getStorageManager();
        int n = pManager.getProducts().size();
        FlowPane productsFlowPane = new FlowPane();
        productsFlowPane.setPadding(new Insets(10));
        productsFlowPane.setHgap(10);
        productsFlowPane.setVgap(10);
        productsFlowPane.getStyleClass().add("background");
        for (int i = 0; i < n; i++) {
            Product product = pManager.getProducts().get(i);
            String path = ".\\ObjectifyMainApp\\src\\resources\\images\\sample_image.png";
            if (product.getProductImagePath() != "") {
                path = product.getProductImagePath();
            }
            File file = new File(path);
            Image image = new Image(file.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            String productName = product.getProductName();
            Label imageLabel = new Label(productName);

            VBox imageBox = new VBox(imageView, imageLabel);
            imageLabel.getStyleClass().add("card-title");
            imageBox.setSpacing(20);
            imageBox.getStyleClass().add("products-card");
            imageBox.setAlignment(Pos.CENTER);
            imageBox.setOnMouseClicked(event -> {
                nameField.setText(productName);
                stockField.setText(String.valueOf(product.getProductStock()));
                priceField.setText(String.valueOf(product.getProductPrice()));
                buyPriceField.setText(String.valueOf(product.getProductBuyPrice()));
                categoryComboBox.getSelectionModel().select(product.getProductCategory());
                imagePathField.setText(product.getProductImagePath());
                updateButton(product);
            });
            imageBox.setOnMouseEntered(event -> {
                imageBox.setCursor(Cursor.HAND);
            });
            imageBox.setOnMouseExited(event -> {
                imageBox.setCursor(Cursor.DEFAULT);
            });
            productsFlowPane.getChildren().add(imageBox);
        }
        
        ScrollPane productsScrollPane = new ScrollPane(productsFlowPane);
        productsScrollPane.setFitToHeight(true);
        productsScrollPane.setFitToWidth(true);
        HBox.setHgrow(productsScrollPane, Priority.ALWAYS);
        
        // Replace the content of the existing ScrollPane with the updated FlowPane
        HBox row = (HBox) this.getChildren().get(0);
        if (row.getChildren().size() == 0) {
            row.getChildren().add(productsScrollPane);
        } else {
            row.getChildren().set(0, productsScrollPane);
        }
    }
    
    private void submitButton(){
        Button submitButton = new Button("Add Product");
        submitButton.setOnAction(event -> {
            StorageManager productManager = SystemPointOfSales.getInstance().getStorageManager();

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
    
            // Update scroll pane
            updateProductsScrollPane();
    
            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Product added");
            alert.setContentText("The new product has been added successfully");
            alert.showAndWait();
        });
        HBox row = (HBox) getChildren().get(0);
        VBox forms = (VBox) row.getChildren().get(1);
        row.getStyleClass().add("background");
        forms.getStyleClass().add("background");
        if (forms.getChildren().size() == 6) {
            forms.getChildren().add(submitButton);
        } else {
            forms.getChildren().set(6,submitButton);
        }
    }
    private void updateButton(Product product) {
        StorageManager pManager = SystemPointOfSales.getInstance().getStorageManager();
        // Update button
        Button updateProductButton = new Button("Update Product");
        updateProductButton.setOnAction(event -> {
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


            // Update product
            pManager.removeProduct(product);
            product.setProductName(name);
            product.setProductStock(stock);
            product.setProductPrice(price);
            product.setProductBuyPrice(buyPrice);
            product.setCategory(category);
            product.setProductImagePath(imagePath);

            // Add product to product manager
            pManager.addNewProducts(product);

            // Clear input fields
            clearFields();

            // Update scroll pane
            updateProductsScrollPane();

            // Update button
            submitButton();

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Product Updated");
            alert.setContentText("The product has been updated successfully");
            alert.showAndWait();
        });

        HBox row = (HBox) getChildren().get(0);
        VBox forms = (VBox) row.getChildren().get(1);
        HBox buttons = new HBox(updateProductButton);
        buttons.setSpacing(10);

        // Delete button
        if (product.getProductStock() == 0) {
            Button deleteButton = new Button("Delete Product");
            deleteButton.setOnAction(event -> {
                // Remove the product
                pManager.removeProduct(product);

                // Clear input fields
                clearFields();

                // Update scroll pane
                updateProductsScrollPane();

                // Update button
                submitButton();

                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Product Deleted");
                alert.setContentText("The product has been deleted successfully");
                alert.showAndWait();
            });
            buttons.getChildren().add(deleteButton);
        } 

        // Cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> {
            clearFields();
            submitButton();
        });
        buttons.getChildren().add(cancelButton);
        
        forms.getChildren().set(6,buttons);
    }
    
}