package com.objectify.controllers.pages;

import com.objectify.models.entities.*;
import com.objectify.models.transactions.Transaction;
import com.objectify.models.transactions.TransactionHistory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
        Member customer1 = new Member(1,false,new TransactionHistory(),"Nathania","08129",12);

        Member customer3 = new Member(3,false,new TransactionHistory(),"Sofyan","08129",12);
        Member customer2 = new Member(2,false,new TransactionHistory(),"Nicholas","08129",13);
        Member customer4 = new Member(4,false, new TransactionHistory(),"Hosea","0818",20);
        Member customer5 = new Member(5,false, new TransactionHistory(),"cust5","0818",20);
        Member customer6 = new Member(6,false,new TransactionHistory(),"member6","21123",20);

        TransactionHistory th1 = new TransactionHistory();
        VIP vip1 = new VIP(100,true,th1, "Hahaha","0812",12);
        VIP vip2 = new VIP(99,true,th1, "heheheh","0812",20);
        VIP vip3 = new VIP(98,true,th1, "vip3","0811",13);
        VIP vip4 = new VIP(97,true,th1, "vip4","0810",12);
        VIP vip5 = new VIP(101,true,th1,"Hahahaha","08128",12);
        VIP vip6 = new VIP(102,true,th1,"hhehehehehehheh","08123123",12);

        um.addUser(customer1);
        um.addUser(vip1);um.addUser(vip3);um.addUser(vip4);um.addUser(customer4);um.addUser(customer5);um.addUser(customer6);
        um.addUser(vip2);um.addUser(customer1);um.addUser(customer2);um.addUser(customer3);um.addUser(vip5);um.addUser(vip6);
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
        userPicture.setSpacing(30);
        userPicture.setPadding(new Insets(20,0,0,30));
        userPicture.getStyleClass().add("pict-container");
        userPicture.setAlignment(Pos.TOP_CENTER);
        userPicture.prefWidthProperty().bind(row.widthProperty().multiply(0.4).subtract(30));
        Label userTitle = new Label("Our users");
        userTitle.getStyleClass().add("title");
        userPicture.getChildren().add(userTitle);

        VBox allMembers = new VBox();
        allMembers.setMaxHeight(Double.MAX_VALUE);
        allMembers.setSpacing(20);
        VBox vipContainer = new VBox();
        vipContainer.setMaxWidth(Double.MAX_VALUE);
        vipContainer.setSpacing(20);
        Label vipLabel = new Label("VIP");
        vipLabel.getStyleClass().add("title");
        vipContainer.getChildren().add(vipLabel);

        VBox memberContainer = new VBox();
        memberContainer.setMaxWidth(Double.MAX_VALUE);
        memberContainer.setSpacing(20);
        Label memberLabel = new Label("Member");
        memberLabel.getStyleClass().add("title");
        memberContainer.getChildren().add(memberLabel);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("background");
        scrollPane.setContent(allMembers);
        scrollPane.setPadding(new Insets(12));
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #E9EFFD;");
        scrollPane.setPrefWidth(userPicture.getPrefWidth());
        scrollPane.setPrefHeight(userPicture.getPrefHeight());
        Integer i = 0;
        for(User user : this.listOfUsers){
            if(i > 8){
                break;
            }
            HBox dataUser = new HBox();
            if(user instanceof  Member){
                String name = ((Member)user).getName();
                Text userName = new Text(name);
                userName.getStyleClass().add("member-name");
                userName.setOnMouseClicked(event ->{
//                    VBox untuk spesific user
                    VBox spesificUser = new VBox();
                    spesificUser.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
                    spesificUser.setAlignment(Pos.CENTER);

//                    Nama username
                    Text titleUserName = new Text(name);

                    HBox containAll = new HBox();
                    containAll.setSpacing(30);
                    containAll.setMaxWidth(Double.MAX_VALUE);
                    containAll.setPadding(new Insets(20,0,0,50));

                    VBox labelCol = new VBox();
                    labelCol.setMaxHeight(Double.MAX_VALUE);
                    labelCol.setSpacing(20);

                    VBox userValue = new VBox();
                    userValue.setMaxHeight(Double.MAX_VALUE);
                    userValue.setSpacing(20);

                    Label phoneNumber = new Label("Phone Number : ");
                    phoneNumber.getStyleClass().add("sub-title");
                    Text phoneNumberValue = new Text(((Member) user).getPhoneNumber());
                    phoneNumberValue.getStyleClass().add("user-data");

                    Label userPoints = new Label("Points : ");
                    userPoints.getStyleClass().add("sub-title");
                    String points = ((Member) user).getPhoneNumber().toString();                    Text userPointsValue = new Text(points);
                    userPointsValue.getStyleClass().add("user-data");

                    labelCol.getChildren().addAll(phoneNumber,userPoints);
                    userValue.getChildren().addAll(phoneNumberValue,userPointsValue);

                    titleUserName.getStyleClass().add("member-name-title");
                    containAll.getChildren().addAll(labelCol,userValue);

                    Button returnBack = new Button("Back");
                    returnBack.setOnAction(backToPage ->{
                        userPicture.getChildren().clear();
                        userPicture.getChildren().add(scrollPane);
                    });

                    spesificUser.getChildren().addAll(titleUserName,containAll,returnBack);
                    userPicture.getChildren().clear();
                    userPicture.getChildren().add(spesificUser);
                });
                dataUser.getChildren().add(userName);
                vipContainer.getChildren().add(dataUser);
            }
            if (user instanceof VIP){
                String name = ((VIP)user).getName();
                Text userName = new Text(name);
                userName.getStyleClass().add("member-name");
                userName.setOnMouseClicked(event ->{
//                    VBox untuk spesific user
                    VBox spesificUser = new VBox();
                    spesificUser.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
                    spesificUser.setAlignment(Pos.CENTER);

//                    Nama username
                    Text titleUserName = new Text(name);

                    HBox containAll = new HBox();
                    containAll.setSpacing(30);
                    containAll.setMaxWidth(Double.MAX_VALUE);
                    containAll.setPadding(new Insets(20,0,0,50));

                    VBox labelCol = new VBox();
                    labelCol.setMaxHeight(Double.MAX_VALUE);
                    labelCol.setSpacing(20);

                    VBox userValue = new VBox();
                    userValue.setMaxHeight(Double.MAX_VALUE);
                    userValue.setSpacing(20);

                    Label phoneNumber = new Label("Phone Number : ");
                    phoneNumber.getStyleClass().add("sub-title");
                    Text phoneNumberValue = new Text(((VIP) user).getPhoneNumber());
                    phoneNumberValue.getStyleClass().add("user-data");

                    Label userPoints = new Label("Points : ");
                    userPoints.getStyleClass().add("sub-title");
                    String points = ((VIP) user).getPhoneNumber().toString();
                    Text userPointsValue = new Text(points);
                    userPointsValue.getStyleClass().add("user-data");

                    labelCol.getChildren().addAll(phoneNumber,userPoints);
                    userValue.getChildren().addAll(phoneNumberValue,userPointsValue);

                    titleUserName.getStyleClass().add("member-name-title");
                    containAll.getChildren().addAll(labelCol,userValue);

                    Button returnBack = new Button("Back");
                    returnBack.setOnAction(backToPage ->{
                        userPicture.getChildren().clear();
                        userPicture.getChildren().add(scrollPane);
                    });

                    spesificUser.getChildren().addAll(titleUserName,containAll,returnBack);
                    userPicture.getChildren().clear();
                    userPicture.getChildren().add(spesificUser);
                });
                dataUser.getChildren().add(userName);
                vipContainer.getChildren().add(dataUser);
            }
        }

        allMembers.getChildren().addAll(vipContainer,memberContainer);
        allMembers.setPadding(new Insets(10));
        allMembers.setStyle("-fx-background-color: #E9EFFD;");

        userPicture.getChildren().addAll(scrollPane);

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
        fieldCol.getChildren().addAll(membershipComboBox,this.nameField,this.phoneNumberField,this.pointsField);
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