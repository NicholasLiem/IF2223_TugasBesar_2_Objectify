package com.objectify.controllers.pages;

import com.objectify.datastore.SystemPointOfSales;
import com.objectify.exceptions.ItemNotFoundException;
import com.objectify.models.entities.*;
import com.objectify.models.items.Product;
import com.objectify.models.items.ShoppingCart;
import com.objectify.models.items.StorageManager;
import com.objectify.models.transactions.Bill;
import com.objectify.models.transactions.BillManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CashierPage extends GridPane {

    private Map<Integer, Integer> cart = new HashMap<Integer, Integer>();
    private User user;

    private void finalizePopup() {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Finalize Bill");

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> popup.close());

        Button saveButton = new Button("Save");
        Label label = new Label("Enter description:");
        TextField inputField = new TextField();
        inputField.setPromptText("Description");
        saveButton.setDefaultButton(true);
        CheckBox usePointsCheckbox = new CheckBox("Use points");
        String inputText = inputField.getText();
        saveButton.setOnAction(event -> {
            BillManager billManager = SystemPointOfSales.getInstance().getBillManager();
            StorageManager storageManager = SystemPointOfSales.getInstance().getStorageManager();
            boolean found = false;
            for (Entry<Integer, Integer> entry : this.cart.entrySet()) {
                try {
                    if (entry.getValue() > storageManager.searchById(entry.getKey()).getProductStock()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Item out of stock");
                        alert.setHeaderText("Item out of stock");
                        alert.setContentText(storageManager.searchById(entry.getKey()).getProductName()
                                + "is currently out of stock");
                        alert.showAndWait();
                        found = true;
                        popup.close();
                        break;
                    }
                } catch (ItemNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            if (!found) {
                Bill bill = new Bill(this.user, new ShoppingCart(this.cart));
                if (billManager.getBills().contains(bill)) {
                    billManager.removeBill(bill);
                }
                try {
                    bill.pay(usePointsCheckbox.isSelected(), inputText);
                } catch (ItemNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            popup.close();
        });

        HBox buttonBox = new HBox(10, cancelButton, saveButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        Label pointsLabel = new Label("Current Points: None");
        pointsLabel.setTextAlignment(TextAlignment.CENTER);
        if (this.user instanceof Member) {
            pointsLabel.setText("Current Points: " + String.valueOf(((Member) user).getPoints()));
        }
        if (this.user instanceof VIP) {
            pointsLabel.setText("Current Points: " + String.valueOf(((VIP) user).getPoints()));
        }
        usePointsCheckbox.setDisable(this.user instanceof Customer);
        VBox layout = new VBox(20, label, inputField, pointsLabel, usePointsCheckbox, buttonBox);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 200);
        popup.setScene(scene);
        popup.showAndWait();
    }

    private ArrayList<User> filterUsers(String searchQuery) {
        UserManager userManager = SystemPointOfSales.getInstance().getUserManager();
        ArrayList<User> userList = userManager.getListOfUsers();
        ArrayList<User> filteredList = new ArrayList<>();
        for (User user : userList) {
            if (user instanceof VIP && ((VIP) user).getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                filteredList.add(user);
            } else if (user instanceof Member
                    && ((Member) user).getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                filteredList.add(user);
            } else if (user instanceof Customer
                    && String.valueOf(((Customer) user).getUserID()).contains(searchQuery)) {
                filteredList.add(user);
            }
        }
        return filteredList;
    }

    private void rebuildScrollPane(VBox itemList, ArrayList<User> userList) {
        itemList.getChildren().removeIf(node -> node instanceof HBox);
        for (User user : userList) {
            String displayText = new String();
            if (user instanceof VIP) {
                displayText = "Username: " + ((VIP) user).getName();
            }

            if (user instanceof Member) {
                displayText = "Username: " + ((Member) user).getName();
            }

            if (user instanceof Customer) {
                displayText = "User ID: " + ((Customer) user).getUserID();
            }
            HBox itemRow = new HBox();
            Label userIdLabel = new Label(displayText);
            itemRow.setSpacing(100);
            Button selectButton = new Button("Select");
            selectButton.setOnAction(event -> {
                this.user = user;
                Stage popupStage = (Stage) selectButton.getScene().getWindow();
                popupStage.close();
            });
            itemRow.getChildren().addAll(userIdLabel, selectButton);
            itemRow.setSpacing(10);
            itemRow.setAlignment(Pos.CENTER_LEFT);
            itemList.getChildren().add(itemRow);
        }
    }

    private void selectUser() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Select User");

        VBox itemList = new VBox();
        itemList.setSpacing(10);
        itemList.setPadding(new Insets(10));

        // Create search bar
        TextField searchField = new TextField();
        searchField.setPromptText("Search users");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<User> filteredUsers = filterUsers(newValue);
            rebuildScrollPane(itemList, filteredUsers);
        });

        // Add search bar to top of item list
        itemList.getChildren().add(searchField);

        // Add all users to the list
        UserManager userManager = SystemPointOfSales.getInstance().getUserManager();
        ArrayList<User> userList = userManager.getListOfUsers();
        rebuildScrollPane(itemList, userList);

        ScrollPane scrollPane = new ScrollPane(itemList);
        scrollPane.setFitToWidth(true);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        scrollPane.setPadding(new Insets(10));

        VBox popupLayout = new VBox();
        popupLayout.getChildren().addAll(scrollPane);
        popupLayout.setPadding(new Insets(10));

        Scene popupScene = new Scene(popupLayout, 300, 300);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    private void saveBill() {
        Bill bill = new Bill(this.user, new ShoppingCart(this.cart));
        BillManager billManager = SystemPointOfSales.getInstance().getBillManager();
        billManager.addBill(bill);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Save Bill");
        alert.setHeaderText("Bill Saved");
        alert.setContentText("New bill has been stored");
        alert.showAndWait();
    }

    private void selectBill() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Select User");

        VBox itemList = new VBox();
        itemList.setSpacing(10);
        itemList.setPadding(new Insets(10));

        // Add some sample items to the list
        BillManager billManager = SystemPointOfSales.getInstance().getBillManager();
        ArrayList<Bill> userList = billManager.getBills();
        for (Bill bill : userList) {
            String displayText = new String();
            if (bill.getUser() instanceof VIP) {
                displayText = "User " + ((VIP) bill.getUser()).getName() + "'s bill";
            }

            if (bill.getUser() instanceof Member) {
                displayText = "User " + ((Member) bill.getUser()).getName() + "'s bill";
            }

            if (bill.getUser() instanceof Customer) {
                displayText = "User ID " + ((Customer) bill.getUser()).getUserID() + "'s bill number";
            }
            HBox itemRow = new HBox();
            Label userIdLabel = new Label(displayText);
            itemRow.setSpacing(100);
            Button selectButton = new Button("Select");
            selectButton.setOnAction(event -> {
                this.user = bill.getUser();
                this.cart = bill.getCart().getCartItems();
                popupStage.close();
            });
            itemRow.getChildren().addAll(userIdLabel, selectButton);
            itemRow.setSpacing(10);
            itemRow.setAlignment(Pos.CENTER_LEFT);
            itemList.getChildren().add(itemRow);
        }

        ScrollPane scrollPane = new ScrollPane(itemList);
        scrollPane.setFitToWidth(true);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        scrollPane.setPadding(new Insets(10));

        VBox popupLayout = new VBox();
        popupLayout.getChildren().addAll(scrollPane);
        popupLayout.setPadding(new Insets(10));

        Scene popupScene = new Scene(popupLayout, 300, 300);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    private void fillCartList(VBox cartList, ScrollPane itemListScrollPane, HBox chargeBox, VBox bill) {
        VBox.setVgrow(cartList, Priority.ALWAYS);
        for (Entry<Integer, Integer> entry : this.cart.entrySet()) {
            HBox itemRow = new HBox();
            HBox itemQty = new HBox();
            Button upQuantityButton = new Button("+");
            upQuantityButton.setPadding(new Insets(5, 10, 5, 10));
            Button decQuantityButton = new Button("-");
            decQuantityButton.setPadding(new Insets(5, 10, 5, 10));
            Text qty = new Text(entry.getValue().toString());
            qty.setTextAlignment(TextAlignment.CENTER);
            HBox.setHgrow(itemQty, Priority.ALWAYS);
            itemQty.setSpacing(20);
            itemQty.setAlignment(Pos.CENTER);
            itemQty.getChildren().addAll(decQuantityButton, qty, upQuantityButton);
            itemRow.setAlignment(Pos.CENTER);
            HBox span = new HBox();
            HBox.setHgrow(span, Priority.ALWAYS);
            StorageManager storageManager = SystemPointOfSales.getInstance().getStorageManager();
            try {
                Text prodName = new Text(storageManager.searchById(entry.getKey()).getProductName());
                prodName.setWrappingWidth(200);
                itemRow.getChildren().addAll(prodName, span, itemQty);
                cartList.getChildren().add(itemRow);
            } catch (ItemNotFoundException e) {
                throw new RuntimeException(e);
            }

            upQuantityButton.setOnMouseClicked((event) -> {
                int newNum = Integer.parseInt(qty.getText()) + 1;
                qty.setText(String.valueOf(newNum));
                try {
                    upQuantityButton.setDisable(
                            Integer.parseInt(qty.getText()) == storageManager.searchById(entry.getKey())
                                    .getProductStock());
                } catch (ItemNotFoundException e) {
                    throw new RuntimeException(e);
                }
                entry.setValue(newNum);
                ShoppingCart newCart = new ShoppingCart(cart);
                chargeBox.getChildren().remove(0);
                double newValue = 0;
                try {
                    newValue = SystemPointOfSales.getInstance().getSettings().getCalculator().calculate(newCart.value());
                } catch (ItemNotFoundException e) {
                    throw new RuntimeException(e);
                }
                chargeBox.getChildren().add(new Label("Charge " + newValue));
                itemListScrollPane.requestLayout();
                bill.requestLayout();
            });
            decQuantityButton.setOnMouseClicked((event) -> {
                int newNum = Integer.parseInt(qty.getText()) - 1;
                if (newNum == 0) {
                    cartList.getChildren().remove(itemRow);
                    this.cart.remove(entry.getKey());
                } else {
                    qty.setText(String.valueOf(newNum));
                    entry.setValue(newNum);
                }
                ShoppingCart newCart = new ShoppingCart(cart);
                chargeBox.getChildren().remove(0);
                double newValue = 0;
                try {
                    newValue = SystemPointOfSales.getInstance().getSettings().getCalculator().calculate(newCart.value());
                } catch (ItemNotFoundException e) {
                    throw new RuntimeException(e);
                }
                chargeBox.getChildren().add(new Label("Charge " + newValue));
                itemListScrollPane.requestLayout();
                bill.requestLayout();
            });
            qty.textProperty().addListener((obs, oldText, newText) -> {
                upQuantityButton.setDisable(newText.equals("99"));
                decQuantityButton.setDisable(newText.equals("0"));
            });
        }
    }

    public CashierPage() {
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);

        StorageManager sm = SystemPointOfSales.getInstance().getStorageManager();

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

        Screen screen = Screen.getPrimary();
        Rectangle2D screenSize = screen.getVisualBounds();

        // fill bill
        VBox bill = new VBox();
        bill.setPadding(new Insets(10));
        bill.setAlignment(Pos.CENTER);
        bill.setPrefWidth(screenSize.getWidth() * 0.4);
        bill.setPrefHeight(screenSize.getHeight() * 0.7);
        bill.setSpacing(10);
        bill.getStyleClass().add("mainBoxes");
        VBox.setVgrow(bill, Priority.ALWAYS);

        // Show current charge
        HBox chargeBox = new HBox(new Label("Charge 0"));
        chargeBox.setPadding(new Insets(20));
        chargeBox.setAlignment(Pos.CENTER);
        chargeBox.getStyleClass().add("billButtons");
        HBox.setHgrow(chargeBox, Priority.ALWAYS);

        // Item list
        VBox cartList = new VBox();
        cartList.setSpacing(10);
        ScrollPane itemListScrollPane = new ScrollPane(cartList);
        itemListScrollPane.setFitToHeight(true);
        itemListScrollPane.setFitToWidth(true);
        itemListScrollPane.setPadding(new Insets(10));
        VBox.setVgrow(itemListScrollPane, Priority.ALWAYS);
        VBox.setVgrow(cartList, Priority.ALWAYS);
        for (Entry<Integer, Integer> entry : this.cart.entrySet()) {
            HBox itemRow = new HBox();
            HBox itemQty = new HBox();
            Button upQuantityButton = new Button("+");
            upQuantityButton.setPadding(new Insets(5, 10, 5, 10));
            Button decQuantityButton = new Button("-");
            decQuantityButton.setPadding(new Insets(5, 10, 5, 10));
            Text qty = new Text(entry.getValue().toString());
            qty.setTextAlignment(TextAlignment.CENTER);
            HBox.setHgrow(itemQty, Priority.ALWAYS);
            itemQty.setSpacing(20);
            itemQty.setAlignment(Pos.CENTER);
            itemQty.getChildren().addAll(decQuantityButton, qty, upQuantityButton);
            itemRow.setAlignment(Pos.CENTER);
            HBox span = new HBox();
            HBox.setHgrow(span, Priority.ALWAYS);
            StorageManager storageManager = SystemPointOfSales.getInstance().getStorageManager();
            try {
                Text prodName = new Text(storageManager.searchById(entry.getKey()).getProductName());
                prodName.setWrappingWidth(200);
                itemRow.getChildren().addAll(prodName, span, itemQty);
                cartList.getChildren().add(itemRow);
            } catch (ItemNotFoundException e) {
                throw new RuntimeException(e);
            }

            upQuantityButton.setOnMouseClicked((event) -> {
                int newNum = Integer.parseInt(qty.getText()) + 1;
                qty.setText(String.valueOf(newNum));
                entry.setValue(newNum);
                ShoppingCart newCart = new ShoppingCart(cart);
                chargeBox.getChildren().remove(0);
                double newValues = 0;
                try {
                    newValues = SystemPointOfSales.getInstance().getSettings().getCalculator().calculate(newCart.value());
                } catch (ItemNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                chargeBox.getChildren().add(new Label("Charge " + newValues));
                itemListScrollPane.requestLayout();
                bill.requestLayout();
            });
            decQuantityButton.setOnMouseClicked((event) -> {
                int newNum = Integer.parseInt(qty.getText()) - 1;
                if (newNum == 0) {
                    cartList.getChildren().remove(itemRow);
                    this.cart.remove(entry.getKey());
                } else {
                    qty.setText(String.valueOf(newNum));
                    entry.setValue(newNum);
                }
                ShoppingCart newCart = new ShoppingCart(cart);
                chargeBox.getChildren().remove(0);
                double newValues = 0;
                try {
                    newValues = SystemPointOfSales.getInstance().getSettings().getCalculator().calculate(newCart.value());
                } catch (ItemNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                chargeBox.getChildren().add(new Label("Charge " + newValues));
                itemListScrollPane.requestLayout();
                bill.requestLayout();
            });
            qty.textProperty().addListener((obs, oldText, newText) -> {
                upQuantityButton.setDisable(newText.equals("99"));
                decQuantityButton.setDisable(newText.equals("0"));
            });
        }

        StorageManager storage = SystemPointOfSales.getInstance().getStorageManager();
        ArrayList<Product> products = storage.getProducts();

        // save, finalize bill
        HBox saveNFinalizeRow = new HBox();

        VBox saveBill = new VBox(new Text("Save Bill"));
        saveBill.setAlignment(Pos.CENTER);
        saveBill.getStyleClass().add("billButtons");
        saveBill.setOnMouseClicked((event) -> {
            if (this.user == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No user assigned");
                alert.setHeaderText("No user assigned");
                alert.setContentText("Please select a user before saving");
                alert.showAndWait();
            } else {
                saveBill();
                this.cart = new HashMap<Integer, Integer>();
                this.user = null;
                cartList.getChildren().clear();
                fillCartList(cartList, itemListScrollPane, chargeBox, saveBill);
            }
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
            if (this.user == null) {
                this.user = new Customer();
                UserManager userManager = SystemPointOfSales.getInstance().getUserManager();
                userManager.addUser(this.user);
            }
            finalizePopup();
            this.cart = new HashMap<Integer, Integer>();
            this.user = null;
            cartList.getChildren().clear();
            fillCartList(cartList, itemListScrollPane, chargeBox, finalizeBillButton);
        });
        finalizeBillButton.setOnMouseEntered((event) -> {
            finalizeBillButton.setCursor(Cursor.HAND);
        });
        finalizeBillButton.setOnMouseExited((event) -> {
            finalizeBillButton.setCursor(Cursor.DEFAULT);
        });
        SystemPointOfSales.getInstance().getSettings().saveAllDataStore();
        HBox.setHgrow(finalizeBillButton, Priority.ALWAYS);
        finalizeBillButton.setPadding(new Insets(20));

        saveNFinalizeRow.getChildren().addAll(saveBill, finalizeBillButton);

        // show all bills and choose customer row
        HBox addCustomerRow = new HBox();
        HBox.setHgrow(addCustomerRow, Priority.SOMETIMES);

        VBox openBills = new VBox(new Text("Bills"));
        openBills.setAlignment(Pos.CENTER);
        openBills.getStyleClass().add("billButtons");
        openBills.setOnMouseClicked((event) -> {
            selectBill();
            cartList.getChildren().clear();
            fillCartList(cartList, itemListScrollPane, chargeBox, openBills);
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
            selectUser();
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
        // Product list
        FlowPane productFlowPane = new FlowPane();
        productFlowPane.setPadding(new Insets(10));
        productFlowPane.setHgap(40);
        productFlowPane.setVgap(20);

        // Search bar
        TextField searchField = new TextField();
        searchField.setPromptText("Search products");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            productFlowPane.getChildren().clear();
            for (Product prod : products) {
                if (prod.getProductName().toLowerCase().contains(newValue.toLowerCase())) {
                    File file = new File(prod.getProductImagePath());
                    Image image = new Image(file.toURI().toString());
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    Label imageLabel = new Label(prod.getProductName());
                    VBox imageBox = new VBox(imageView, imageLabel);
                    imageBox.setSpacing(10);
                    imageBox.setAlignment(Pos.CENTER);
                    imageBox.setOnMouseClicked(event -> {
                        if (this.cart.keySet().contains(prod.getIdProduct())) {
                            this.cart.replace(prod.getIdProduct(), this.cart.get(prod.getIdProduct()) + 1);
                            fillCartList(cartList, itemListScrollPane, chargeBox, openBills);
                        } else {
                            HBox itemRow = new HBox();
                            HBox itemQty = new HBox();
                            Button upQuantityButton = new Button("+");
                            upQuantityButton.setPadding(new Insets(5, 10, 5, 10));
                            Button decQuantityButton = new Button("-");
                            decQuantityButton.setPadding(new Insets(5, 10, 5, 10));
                            Text qty = new Text("1");
                            qty.setTextAlignment(TextAlignment.CENTER);
                            HBox.setHgrow(itemQty, Priority.ALWAYS);
                            itemQty.setSpacing(20);
                            itemQty.setAlignment(Pos.CENTER);
                            itemQty.getChildren().addAll(decQuantityButton, qty, upQuantityButton);
                            itemRow.setAlignment(Pos.CENTER);
                            HBox span = new HBox();
                            HBox.setHgrow(span, Priority.ALWAYS);
                            Text prodName = new Text(prod.getProductName());
                            prodName.setWrappingWidth(200);
                            itemRow.getChildren().addAll(prodName, span, itemQty);
                            cartList.getChildren().add(itemRow);

                            this.cart.put(prod.getIdProduct(), 1);

                            upQuantityButton.setOnMouseClicked((e) -> {
                                int newNum = Integer.parseInt(qty.getText()) + 1;
                                qty.setText(String.valueOf(newNum));
                                upQuantityButton.setDisable(Integer.parseInt(qty.getText()) == prod.getProductStock());
                                this.cart.replace(prod.getIdProduct(), newNum);
                                ShoppingCart newCart = new ShoppingCart(cart);
                                chargeBox.getChildren().remove(0);
                                double newValues = 0;
                                try {
                                    newValues = SystemPointOfSales.getInstance().getSettings().getCalculator().calculate(newCart.value());
                                } catch (ItemNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                }
                                chargeBox.getChildren().add(new Label("Charge " + newValues));
                                itemListScrollPane.requestLayout();
                                bill.requestLayout();
                            });
                            decQuantityButton.setOnMouseClicked((e) -> {
                                int newNum = Integer.parseInt(qty.getText()) - 1;
                                if (newNum == 0) {
                                    cartList.getChildren().remove(itemRow);
                                    this.cart.remove(prod.getIdProduct());
                                } else {
                                    qty.setText(String.valueOf(newNum));
                                    this.cart.replace(prod.getIdProduct(), newNum);
                                }
                                ShoppingCart newCart = new ShoppingCart(cart);
                                chargeBox.getChildren().remove(0);
                                double newValues = 0;
                                try {
                                    newValues = SystemPointOfSales.getInstance().getSettings().getCalculator().calculate(newCart.value());
                                } catch (ItemNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                }
                                chargeBox.getChildren().add(new Label("Charge " + newValues));
                                itemListScrollPane.requestLayout();
                                bill.requestLayout();
                            });
                            qty.textProperty().addListener((obs, oldText, newText) -> {
                                upQuantityButton.setDisable(newText.equals("99"));
                                decQuantityButton.setDisable(newText.equals("0"));
                            });
                            ShoppingCart newCart = new ShoppingCart(cart);
                            chargeBox.getChildren().remove(0);
                            double newValues = 0;
                            try {
                                newValues = SystemPointOfSales.getInstance().getSettings().getCalculator().calculate(newCart.value());
                            } catch (ItemNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                            chargeBox.getChildren().add(new Label("Charge " + newValues));
                            itemListScrollPane.requestLayout();
                            bill.requestLayout();
                        }
                    });
                    imageBox.setOnMouseEntered(event -> {
                        imageBox.setCursor(Cursor.HAND);
                    });
                    imageBox.setOnMouseExited(event -> {
                        imageBox.setCursor(Cursor.DEFAULT);
                    });
                    productFlowPane.getChildren().add(imageBox);
                }
            }
        });

        // Add products to flow pane
        for (Product prod : products) {
            Integer productId = prod.getIdProduct();
            File file = new File(prod.getProductImagePath());
            Image image = new Image(file.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            Label imageLabel = new Label(prod.getProductName());
            VBox imageBox = new VBox(imageView, imageLabel);
            imageBox.setSpacing(10);
            imageBox.setAlignment(Pos.CENTER);
            imageBox.setOnMouseClicked(event -> {
                try {
                    if (this.cart.keySet().contains(productId)) {
                        this.cart.replace(productId, this.cart.get(productId) + 1);
                        itemListScrollPane.requestLayout();
                    } else {
                        HBox itemRow = new HBox();
                        HBox itemQty = new HBox();
                        Button upQuantityButton = new Button("+");
                        upQuantityButton.setPadding(new Insets(5, 10, 5, 10));
                        Button decQuantityButton = new Button("-");
                        decQuantityButton.setPadding(new Insets(5, 10, 5, 10));
                        Text qty = new Text("1");
                        qty.setTextAlignment(TextAlignment.CENTER);
                        HBox.setHgrow(itemQty, Priority.ALWAYS);
                        itemQty.setSpacing(20);
                        itemQty.setAlignment(Pos.CENTER);
                        itemQty.getChildren().addAll(decQuantityButton, qty, upQuantityButton);
                        itemRow.setAlignment(Pos.CENTER);
                        HBox span = new HBox();
                        HBox.setHgrow(span, Priority.ALWAYS);
                        Text prodName = new Text(sm.searchById(productId).getProductName());
                        prodName.setWrappingWidth(200);
                        itemRow.getChildren().addAll(prodName, span, itemQty);
                        cartList.getChildren().add(itemRow);

                        this.cart.put(productId, 1);

                        upQuantityButton.setOnMouseClicked((e) -> {
                            int newNum = Integer.parseInt(qty.getText()) + 1;
                            qty.setText(String.valueOf(newNum));
                            this.cart.replace(productId, newNum);
                            upQuantityButton.setDisable(Integer.parseInt(qty.getText()) == prod.getProductStock());
                            ShoppingCart newCart = new ShoppingCart(cart);
                            chargeBox.getChildren().remove(0);
                            double newValue = 0;
                            try {
                                newValue = SystemPointOfSales.getInstance().getSettings().getCalculator().calculate(newCart.value());
                            } catch (ItemNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                            chargeBox.getChildren().add(new Label("Charge " + newValue));
                            itemListScrollPane.requestLayout();
                            bill.requestLayout();
                        });
                        decQuantityButton.setOnMouseClicked((e) -> {
                            int newNum = Integer.parseInt(qty.getText()) - 1;
                            if (newNum == 0) {
                                cartList.getChildren().remove(itemRow);
                                this.cart.remove(productId);
                            } else {
                                qty.setText(String.valueOf(newNum));
                                this.cart.replace(productId, newNum);
                            }
                            ShoppingCart newCart = new ShoppingCart(cart);
                            chargeBox.getChildren().remove(0);
                            double newValue = 0;
                            try {
                                newValue = SystemPointOfSales.getInstance().getSettings().getCalculator().calculate(newCart.value());
                            } catch (ItemNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                            chargeBox.getChildren().add(new Label("Charge " + newValue));
                            itemListScrollPane.requestLayout();
                            bill.requestLayout();
                        });
                        qty.textProperty().addListener((obs, oldText, newText) -> {
                            upQuantityButton.setDisable(newText.equals("99"));
                            decQuantityButton.setDisable(newText.equals("0"));
                        });
                        ShoppingCart newCart = new ShoppingCart(cart);
                        chargeBox.getChildren().remove(0);
                        double newValue = 0;
                        try {
                            newValue = SystemPointOfSales.getInstance().getSettings().getCalculator().calculate(newCart.value());
                        } catch (ItemNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        chargeBox.getChildren().add(new Label("Charge " + newValue));
                        itemListScrollPane.requestLayout();
                        bill.requestLayout();
                    }
                } catch (ItemNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            imageBox.setOnMouseEntered(event -> {
                imageBox.setCursor(Cursor.HAND);
            });
            imageBox.setOnMouseExited(event -> {
                imageBox.setCursor(Cursor.DEFAULT);
            });
            productFlowPane.getChildren().add(imageBox);
        }
        ScrollPane productsScrollPane = new ScrollPane(
                productFlowPane);
        productsScrollPane.setFitToHeight(true);
        productsScrollPane.setFitToWidth(true);
        productsScrollPane.setPadding(new Insets(10));
        productsScrollPane.getStyleClass().add("mainBoxes");
        VBox leftStack = new VBox(searchField, productsScrollPane);
        leftStack.setSpacing(10);
        HBox.setHgrow(leftStack, Priority.ALWAYS);
        VBox.setVgrow(leftStack, Priority.ALWAYS);

        productsScrollPane.setPrefHeight(screenSize.getHeight() * 0.7);
        productsScrollPane.setPrefWidth(screenSize.getWidth() * 0.4);
        HBox.setHgrow(productsScrollPane, Priority.ALWAYS);

        bill.getChildren().addAll(addCustomerRow, itemListScrollPane, saveNFinalizeRow, chargeBox);

        row.getChildren().addAll(leftStack, bill);
        this.getChildren().add(row);
    }

}
