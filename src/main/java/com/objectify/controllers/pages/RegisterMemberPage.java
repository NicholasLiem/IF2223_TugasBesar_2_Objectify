package com.objectify.controllers.pages;

import com.objectify.models.entities.*;
import com.objectify.models.transactions.TransactionHistory;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

public class RegisterMemberPage extends GridPane {

    private Label nameLabel;
    private Label phoneNumberLabel;
    private Label pointsLabel;

    private TextField nameField;
    private TextField phoneNumberField;
    private TextField pointsField;

    public RegisterMemberPage() {
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);


        // Create radio buttons for membership types
        ComboBox<String> membershipComboBox = new ComboBox<>();
        membershipComboBox.getItems().addAll("Customer", "Member", "VIP");
        membershipComboBox.setValue("Customer");
        add(new Label("Membership Type:"), 0, 0);
        add(membershipComboBox, 1, 0);

        // Create labels for member fields
        nameLabel = new Label("Name:");
        phoneNumberLabel = new Label("Phone Number:");
        pointsLabel = new Label("Points:");

        // Create text fields for member fields
        nameField = new TextField();
        phoneNumberField = new TextField();
        pointsField = new TextField();

        // Add member fields to grid pane
        add(nameLabel, 0, 1);
        add(nameField, 1, 1);
        add(phoneNumberLabel, 0, 2);
        add(phoneNumberField, 1, 2);
        add(pointsLabel, 0, 3);
        add(pointsField, 1, 3);

        // Set font size for labels and fields
        nameLabel.setFont(Font.font(12));
        phoneNumberLabel.setFont(Font.font(12));
        pointsLabel.setFont(Font.font(12));
        nameField.setFont(Font.font(12));
        phoneNumberField.setFont(Font.font(12));
        pointsField.setFont(Font.font(12));

        // Set fixed row heights
        this.getRowConstraints().clear();
        RowConstraints row1 = new RowConstraints(30);
        RowConstraints row2 = new RowConstraints(30);
        RowConstraints row3 = new RowConstraints(30);
        RowConstraints row4 = new RowConstraints(30);
        this.getRowConstraints().addAll(row1, row2, row3, row4);

        membershipComboBox.setOnAction(event -> {
            String membershipType = membershipComboBox.getValue();
            if (membershipType.equals("Customer")) {
                nameField.setDisable(true);
                phoneNumberField.setDisable(true);
                pointsField.setDisable(true);
            } else {
                nameField.setDisable(false);
                phoneNumberField.setDisable(false);
                pointsField.setDisable(false);
            }
        });

        Button submitButton = new Button("Create Member");
        submitButton.setOnAction(event -> {
            String selectedMembership = membershipComboBox.getValue();
            UserManager userManager = UserManager.getInstance();

            switch (selectedMembership) {
                case "Customer":
                    Customer customer = new Customer();
                    customer.setActivationStatus(true);

                    userManager.addUser(customer);
                    break;
                case "Member":
                    TransactionHistory th = new TransactionHistory();
                    int points = parsePointsField();
                    if (points == -1) {
                        return;
                    }
                    Member member = new Member(123, true, th, nameField.getText(), phoneNumberField.getText(), points);

                    userManager.addUser(member);
                    break;
                case "VIP":
                    TransactionHistory thNew = new TransactionHistory();
                    int pointsNew = parsePointsField();
                    if (pointsNew == -1) {
                        return;
                    }
                    VIP vip = new VIP(123, true, thNew, nameField.getText(), phoneNumberField.getText(), pointsNew);

                    userManager.addUser(vip);
                    break;
            }
            for (User u: userManager.getListOfUsers()){
                System.out.println(u.toString());
            }
            clearFields();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Member created");
            alert.setContentText("The new member has been created successfully");
            alert.showAndWait();
        });
        this.add(submitButton, 0, 4, 3, 1);
    }

    // Helper method to clear input fields
    private void clearFields() {
        nameField.clear();
        phoneNumberField.clear();
        pointsField.clear();
    }

    private int parsePointsField() {
        int points = 0;
        try {
            points = Integer.parseInt(pointsField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Points must be a number");
            alert.showAndWait();
            return -1;
        }
        return points;
    }
}