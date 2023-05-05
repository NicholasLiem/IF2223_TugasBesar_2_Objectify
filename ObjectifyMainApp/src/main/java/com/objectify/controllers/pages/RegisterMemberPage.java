package com.objectify.controllers.pages;

import com.objectify.models.entities.*;
import com.objectify.models.transactions.TransactionHistory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RegisterMemberPage extends Pane {

    private Label nameLabel;
    private Label phoneNumberLabel;
    private Label pointsLabel;

    private TextField nameField;
    private TextField phoneNumberField;
    private TextField pointsField;

    public RegisterMemberPage() {
        Path cssPath = Paths.get("ObjectifyMainApp","src", "resources", "css", "registerMember.css");
        String cssUrl = cssPath.toUri().toString();
        this.getStylesheets().add(cssUrl);

        HBox row = new HBox();
        row.setSpacing(10);

        VBox forms = new VBox();
        //        Title label
        HBox titleContainer = new HBox();
        titleContainer.setMaxWidth(Double.MAX_VALUE);
        titleContainer.setAlignment(Pos.CENTER);
        Label title = new Label("Register Now");
        title.getStyleClass().add("title");
        titleContainer.getChildren().add(title);

//        Row untuk select membertype
        HBox firstLine = new HBox();
        firstLine.setSpacing(20);
        firstLine.setMaxWidth(Double.MAX_VALUE);
        ComboBox<String> membershipComboBox = new ComboBox<>();
        membershipComboBox.getItems().addAll("Customer", "Member", "VIP");
        membershipComboBox.setValue("Customer");
        Label membership = new Label("Membership Type: ");
        membership.getStyleClass().add("sub-title");
        firstLine.setAlignment(Pos.CENTER_LEFT);
        membershipComboBox.setPadding(new Insets(3));
        membershipComboBox.getStyleClass().add("combo-box");
        firstLine.setPadding(new Insets(20,0,0,50));
        firstLine.getChildren().addAll(membership,membershipComboBox);
        HBox.setHgrow(firstLine, Priority.ALWAYS); // tambahkan baris ini

//        Row kedua
        HBox secondLine = new HBox();
        secondLine.setSpacing(110);
        secondLine.setMaxWidth(Double.MAX_VALUE);

        Label nameLabel = new Label("Name:");
        nameLabel.getStyleClass().add("sub-title");
        secondLine.setAlignment(Pos.CENTER_LEFT);
        secondLine.setPadding(new Insets(20,0,0,50));
        TextField nameField = new TextField();
        secondLine.getChildren().addAll(nameLabel,nameField);
        nameField.setPrefWidth(450);
        HBox.setHgrow(secondLine, Priority.ALWAYS); // tambahkan baris ini

//        Masukin semua element ke dalam forms
        forms.getChildren().addAll(titleContainer,firstLine,secondLine);
        forms.getStyleClass().add("forms");
        forms.setPadding(new Insets(20,0,0,30));
        forms.setPrefWidth(0.5*Region.USE_COMPUTED_SIZE); // 60% width

        VBox userPicture = new VBox();
        userPicture.getStyleClass().add("pict-container");
        userPicture.setPadding(new Insets(30,0,0,0));
        userPicture.setAlignment(Pos.TOP_CENTER);
        userPicture.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        userPicture.getChildren().add(new Label("Hello"));
        userPicture.setPrefWidth(0.5*Region.USE_COMPUTED_SIZE); // 40% width

        // Set the grow priority for the userPicture VBox to Priority.ALWAYS
        HBox.setHgrow(forms, Priority.ALWAYS);
        HBox.setHgrow(userPicture, Priority.ALWAYS);

        row.getChildren().addAll(forms,userPicture);
        row.getStyleClass().add("forms");

        BorderPane mainContainer = new BorderPane();
        Label bottomLabel = new Label("Bottom");
        mainContainer.setBottom(bottomLabel);
        mainContainer.setCenter(row);
        mainContainer.prefWidthProperty().bind(this.widthProperty());
        mainContainer.prefHeightProperty().bind(this.heightProperty());
        // set row width to 100% so that forms and userPicture split 60-40
        row.setPrefWidth(Region.USE_COMPUTED_SIZE);
        row.setMaxWidth(Double.MAX_VALUE);

        this.getChildren().addAll(mainContainer);
//
        // Create labels for member fields

        Label phoneNumberLabel = new Label("Phone Number:");
        Label pointsLabel = new Label("Points:");
//
//        // Create text fields for member fields
        TextField phoneNumberField = new TextField();
        TextField pointsField = new TextField();
//
//        // Add member fields to grid pane
//        add(nameLabel, 0, 1);
//        add(nameField, 1, 1);
//        add(phoneNumberLabel, 0, 2);
//        add(phoneNumberField, 1, 2);
//        add(pointsLabel, 0, 3);
//        add(pointsField, 1, 3);
//
//        // Set font size for labels and fields
//        nameLabel.setFont(Font.font(12));
//        phoneNumberLabel.setFont(Font.font(12));
//        pointsLabel.setFont(Font.font(12));
//        nameField.setFont(Font.font(12));
//        phoneNumberField.setFont(Font.font(12));
//        pointsField.setFont(Font.font(12));

        // Set fixed row heights
//        this.getRowConstraints().clear();
//        RowConstraints row1 = new RowConstraints(30);
//        RowConstraints row2 = new RowConstraints(30);
//        RowConstraints row3 = new RowConstraints(30);
//        RowConstraints row4 = new RowConstraints(30);
//        this.getRowConstraints().addAll(row1, row2, row3, row4);

//        membershipComboBox.setOnAction(event -> {
//            String membershipType = membershipComboBox.getValue();
//            if (membershipType.equals("Customer")) {
//                nameField.setDisable(true);
//                phoneNumberField.setDisable(true);
//                pointsField.setDisable(true);
//            } else {
//                nameField.setDisable(false);
//                phoneNumberField.setDisable(false);
//                pointsField.setDisable(false);
//            }
//        });
//
//        Button submitButton = new Button("Create Member");
//        submitButton.setOnAction(event -> {
//            String selectedMembership = membershipComboBox.getValue();
//            UserManager userManager = UserManager.getInstance();
//
//            switch (selectedMembership) {
//                case "Customer":
//                    Customer customer = new Customer();
//                    customer.setActivationStatus(true);
//
//                    userManager.addUser(customer);
//                    break;
//                case "Member":
//                    TransactionHistory th = new TransactionHistory();
//                    int points = parsePointsField();
//                    if (points == -1) {
//                        return;
//                    }
//                    Member member = new Member(123, true, th, nameField.getText(), phoneNumberField.getText(), points);
//
//                    userManager.addUser(member);
//                    break;
//                case "VIP":
//                    TransactionHistory thNew = new TransactionHistory();
//                    int pointsNew = parsePointsField();
//                    if (pointsNew == -1) {
//                        return;
//                    }
//                    VIP vip = new VIP(123, true, thNew, nameField.getText(), phoneNumberField.getText(), pointsNew);
//
//                    userManager.addUser(vip);
//                    break;
//            }
//            for (User u: userManager.getListOfUsers()){
//                System.out.println(u.toString());
//            }
//            clearFields();
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Success");
//            alert.setHeaderText("Member created");
//            alert.setContentText("The new member has been created successfully");
//            alert.showAndWait();
//        });

//        this.add(submitButton, 0, 4, 3, 1);
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