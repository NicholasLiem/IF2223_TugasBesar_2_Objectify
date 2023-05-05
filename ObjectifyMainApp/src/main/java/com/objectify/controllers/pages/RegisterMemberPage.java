package com.objectify.controllers.pages;

import com.objectify.models.entities.*;
import com.objectify.models.transactions.TransactionHistory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
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

        HBox container = new HBox();
        container.setMaxWidth(Double.MAX_VALUE);
        container.setSpacing(30);
        container.setPadding(new Insets(30,0,0,80));

        VBox labelCol = new VBox();
        labelCol.setSpacing(40);
        labelCol.setMaxHeight(Double.MAX_VALUE);
        Label membership = new Label("Membership Type: ");
        this.nameLabel = new Label("Name:");
        this.phoneNumberLabel = new Label("Phone Number:");
        this.pointsLabel = new Label("Points:");
        this.nameLabel.getStyleClass().add("sub-title");
        phoneNumberLabel.getStyleClass().add("sub-title");
        pointsLabel.getStyleClass().add("sub-title");
        labelCol.getChildren().addAll(membership,nameLabel,phoneNumberLabel,pointsLabel);

        VBox fieldCol = new VBox();
        fieldCol.setSpacing(40);
        fieldCol.setMaxHeight(Double.MAX_VALUE);
        ComboBox<String> membershipComboBox = new ComboBox<>();
        membershipComboBox.getItems().addAll("Customer", "Member", "VIP");
        membershipComboBox.setValue("Customer");
        membership.getStyleClass().add("sub-title");
        membershipComboBox.setPadding(new Insets(3));
        membershipComboBox.getStyleClass().add("combo-box");
        this.nameField = new TextField();
        this.phoneNumberField = new TextField();
        this.pointsField = new TextField();
        nameField.setPrefWidth(400);
        fieldCol.getChildren().addAll(membershipComboBox,nameField,phoneNumberField,pointsField);

        container.getChildren().addAll(labelCol,fieldCol);

        forms.getChildren().addAll(titleContainer,container);
        forms.getStyleClass().add("forms");
        forms.setPadding(new Insets(20,0,0,30));
        forms.setPrefWidth(0.6*Region.USE_COMPUTED_SIZE); // 60% width

        VBox userPicture = new VBox();
        userPicture.getStyleClass().add("pict-container");
        userPicture.setPadding(new Insets(30,0,0,0));
        userPicture.setAlignment(Pos.TOP_CENTER);
        userPicture.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        userPicture.getChildren().add(new Label("Hello"));
        userPicture.setPrefWidth(0.4*Region.USE_COMPUTED_SIZE); // 40% width

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
        submitButton.getStyleClass().add("submit-btn");
        submitButton.setOnAction(event -> {
            String selectedMembership = membershipComboBox.getValue();
            UserManager userManager = UserManager.getInstance();

            switch (selectedMembership) {
                case "Customer":
                    Customer customer = new Customer();
                    customer.setActivationStatus(true);
                    System.out.println("Masuk ke customer");
                    System.out.println(customer);
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
        HBox buttonBox = new HBox();
        buttonBox.setPrefWidth(Double.MAX_VALUE);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(30,0,0,0));
        buttonBox.getChildren().add(submitButton);
        forms.getChildren().add(buttonBox);
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