package com.objectify.controllers.pages;

import com.objectify.models.entities.*;
import com.objectify.models.transactions.Transaction;
import com.objectify.models.transactions.TransactionHistory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class RegisterMemberPage extends Pane {

    private Label nameLabel;
    private Label phoneNumberLabel;
    private Label pointsLabel;

    private Label imageLabel;

    private TextField nameField;
    private TextField phoneNumberField;
    private TextField pointsField;

    private ImageView userImage;

    private ArrayList<User> listOfUsers;


    public RegisterMemberPage() {
//        Dummy data doank
        UserManager um = UserManager.getInstance();
        Customer customer1 = new Customer(1,false,new TransactionHistory());
        TransactionHistory th1 = new TransactionHistory();
        VIP vip1 = new VIP(1,true,th1, "Hahaha","0812",12);
        um.addUser(customer1);
        um.addUser(vip1);
        this.listOfUsers = um.getListOfUsers();

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

        VBox userPicture = new VBox();
        userPicture.getStyleClass().add("pict-container");
        userPicture.setAlignment(Pos.TOP_CENTER);
        userPicture.prefWidthProperty().bind(row.widthProperty().multiply(0.5).subtract(30));


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
        this.imageLabel = new Label("Choose image : ");
        this.nameLabel.getStyleClass().add("sub-title");
        this.imageLabel.getStyleClass().add("sub-title");
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
        Button button = new Button("Pilih File");
        button.setOnAction(e -> {
            if(this.userImage != null){
                userPicture.getChildren().clear();
            }
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Pilih File");
            File selectedFile = fileChooser.showOpenDialog(null);
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
            );
            if (selectedFile != null && isImageFile(selectedFile)) {
                try {
                    // Load the image from the selected file using ImageIO
                    javafx.scene.image.Image image = new javafx.scene.image.Image(selectedFile.toURI().toURL().toString());

                    // Create an image view to display the image
                    javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
                    imageView.setFitHeight(500);
                    imageView.setFitWidth(300);
                    this.userImage = imageView;
                    userPicture.getChildren().add(this.userImage);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        fieldCol.getChildren().addAll(membershipComboBox,this.nameField,this.phoneNumberField,this.pointsField,button);
        container.getChildren().addAll(labelCol,fieldCol);

        forms.getChildren().addAll(titleContainer,container);
        forms.getStyleClass().add("forms");
        forms.setPadding(new Insets(20,0,0,30));
        forms.prefWidthProperty().bind(row.widthProperty().multiply(0.6));

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
    private boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".png") || name.endsWith(".jpg");
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