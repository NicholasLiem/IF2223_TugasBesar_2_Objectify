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
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class RegisterMemberPage extends Pane {

    private Label nameLabel;
    private Label phoneNumberLabel;
    private Label pointsLabel;

    private Label imageLabel;
    private ComboBox<String> membershipComboBox;

    private TextField nameField;
    private TextField phoneNumberField;
    private TextField pointsField;

    private ComboBox<String> updatedMembershipComboBox;

    private TextField updatedNameField;
    private TextField updatedPhoneNumberField;
    private TextField updatedPointsField;
    private ArrayList<User> listOfUsers;

    private VBox userPicture;

    private  ScrollPane scrollPane;
    private Label userTitle;
    public RegisterMemberPage() {
//        Dummy data doank
        UserManager um = UserManager.getInstance();

        Customer user1 = new Customer();
        Customer user2 = new Customer();
        Member customer1 = new Member(false,new TransactionHistory(),"Nathania","08129",12);

        Member customer3 = new Member(false,new TransactionHistory(),"Sofyan","08129",12);
        Member customer2 = new Member(false,new TransactionHistory(),"Nicholas","08129",13);
        Member customer4 = new Member(false, new TransactionHistory(),"Hosea","0818",20);
        Member customer5 = new Member(false, new TransactionHistory(),"cust5","0818",20);
        Member customer6 = new Member(false,new TransactionHistory(),"member6","21123",20);

        TransactionHistory th1 = new TransactionHistory();
        VIP vip1 = new VIP(true,th1, "Hahaha","0812",12);
        VIP vip2 = new VIP(true,th1, "heheheh","0812",20);
        VIP vip3 = new VIP(true,th1, "vip3","0811",13);
        VIP vip4 = new VIP(true,th1, "vip4","0810",12);
        VIP vip5 = new VIP(true,th1,"Hahahaha","08128",12);
        VIP vip6 = new VIP(true,th1,"hhehehehehehheh","08123123",12);

        um.addUser(customer1);um.addUser(user1);um.addUser(user2);
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

        this.userPicture = new VBox();
        userPicture.setSpacing(30);
        userPicture.setPadding(new Insets(20,0,0,30));
        userPicture.getStyleClass().add("pict-container");
        userPicture.setAlignment(Pos.TOP_CENTER);
        userPicture.prefWidthProperty().bind(row.widthProperty().multiply(0.4).subtract(30));
        this.userTitle = new Label("Our users");
        userTitle.getStyleClass().add("title");

//        Container untuk menampung semua nama atau id member
        VBox allMembers = new VBox();
        allMembers.setMaxHeight(Double.MAX_VALUE);
        allMembers.setSpacing(20);

//        Container untuk menampung semua VIP user
        VBox vipContainer = new VBox();
        vipContainer.setMaxWidth(Double.MAX_VALUE);
        vipContainer.setSpacing(20);
        Label vipLabel = new Label("VIP");
        vipLabel.getStyleClass().add("title");
        vipContainer.getChildren().add(vipLabel);

//        Container untuk menampung semua Member user
        VBox memberContainer = new VBox();
        memberContainer.setMaxWidth(Double.MAX_VALUE);
        memberContainer.setSpacing(20);
        Label memberLabel = new Label("Member");
        memberLabel.getStyleClass().add("title");
        memberContainer.getChildren().add(memberLabel);

//        Container untuk menampung semua Customer User

        VBox customerContainer = new VBox();
        customerContainer.setMaxWidth(Double.MAX_VALUE);
        customerContainer.setSpacing(20);
        Label customerLabel = new Label("User");
        customerLabel.getStyleClass().add("title");
        customerContainer.getChildren().add(customerLabel);

//        Scroll pane untuk membuat semua member jadi bisa discroll
        this.scrollPane = new ScrollPane();
        scrollPane.setContent(allMembers);
        scrollPane.setPadding(new Insets(12));
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #E9EFFD;");
        scrollPane.setPrefWidth(userPicture.getPrefWidth());
        scrollPane.setPrefHeight(userPicture.getPrefHeight());

//        Loop semua user di dalam this.listOfUsers untuk ditampailkan
        for(User user : this.listOfUsers){
//            Container untuk menampung data sebuah User
            HBox dataUser = new HBox();
            if(user instanceof  Member){
                String name = ((Member)user).getName();
                Text userName = new Text(name);
                userName.getStyleClass().add("member-name");
                String number = ((Member)user).getPhoneNumber();
                String points = Integer.toString(((Member)user).getPoints());
                dataUser.getChildren().add(userName);
                memberContainer.getChildren().add(dataUser);
                userName.setOnMouseClicked(event ->{
                    this.userPicture.getChildren().clear();
                    this.userPicture.getChildren().add(selectedUser(name,"Member",number,points));
                });

            }
            if (user instanceof VIP){
                String name = ((VIP)user).getName();
                Text userName = new Text(name);
                String number = ((VIP)user).getPhoneNumber();
                String points = Integer.toString(((VIP)user).getPoints());
                userName.getStyleClass().add("member-name");
                dataUser.getChildren().add(userName);
                vipContainer.getChildren().add(dataUser);
                userName.setOnMouseClicked(event ->{
                    this.userPicture.getChildren().clear();
                    this.userPicture.getChildren().addAll(selectedUser(name,"VIP",number,points));
                });
            }

            if(user instanceof Customer){
                Integer id = user.getUserID();
                Text user_id = new Text(id.toString());
                user_id.getStyleClass().add("member-name");
                dataUser.getChildren().add(user_id);
                customerContainer.getChildren().add(dataUser);
                user_id.setOnMouseClicked(event -> {
                    userPicture.getChildren().clear();
                    userPicture.getChildren().addAll(selectedUser(" ","Customer"," "," "));
                });

            }
        }

        allMembers.getChildren().addAll(vipContainer,memberContainer,customerContainer);
        allMembers.setPadding(new Insets(10));
        allMembers.setStyle("-fx-background-color: #E9EFFD;");

        userPicture.getChildren().addAll(userTitle,scrollPane);

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
        this.membershipComboBox = new ComboBox<>();
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
        forms.getChildren().add(submitButton());
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

    private HBox submitButton(){
        HBox buttonBox = new HBox();
        buttonBox.setPrefWidth(Double.MAX_VALUE);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(30,0,0,0));
        Button button = new Button("Create Member");
        button.getStyleClass().add("submit-btn");
        button.setOnAction(event -> {
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
                    Member member = new Member(true, th, nameField.getText(), phoneNumberField.getText(), points);

                    userManager.addUser(member);
                    break;
                case "VIP":
                    TransactionHistory thNew = new TransactionHistory();
                    int pointsNew = parsePointsField();
                    if (pointsNew == -1) {
                        return;
                    }
                    VIP vip = new VIP(true, thNew, nameField.getText(), phoneNumberField.getText(), pointsNew);

                    userManager.addUser(vip);
                    break;
            }
            for (User u: userManager.getListOfUsers()){
                System.out.println(u.toString());
            }
            clearFields();
            setAlert();
        });
        buttonBox.getChildren().add(button);
        return buttonBox;
    }
    private void setAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Member created");
        alert.setContentText("The new member has been created successfully");
        alert.showAndWait();
    }

    private VBox selectedUser(String oldName, String type, String oldNumber, String oldPoints){

        this.updatedNameField = new TextField();
        this.updatedPhoneNumberField = new TextField();
        this.updatedPointsField = new TextField();
        this.updatedMembershipComboBox = new ComboBox<>();
        this.updatedMembershipComboBox.getItems().addAll("Customer", "Member", "VIP");

        this.updatedNameField.setText(oldName);
        this.updatedPhoneNumberField.setText(oldNumber);
        this.updatedPointsField.setText(oldPoints);
        this.updatedMembershipComboBox.setValue(type);

        VBox spesificUser = new VBox();
        spesificUser.setSpacing(20);
        spesificUser.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        spesificUser.setAlignment(Pos.CENTER);

        HBox containAll = new HBox();
        containAll.setSpacing(30);
        containAll.setMaxWidth(Double.MAX_VALUE);
        containAll.setPadding(new Insets(20,0,0,50));
        if(oldName == " "){
            Text titleUserName = new Text("User");
            titleUserName.getStyleClass().add("title");
            spesificUser.getChildren().add(titleUserName);

        }else{
            Text titleUserName = new Text(oldName);
            titleUserName.getStyleClass().add("title");
            spesificUser.getChildren().add(titleUserName);
        }

        VBox labelCol = new VBox();
        labelCol.setMaxHeight(Double.MAX_VALUE);
        labelCol.setSpacing(20);

        Label membershipLabel = new Label("Membership Type : ");
        Label nameLabel = new Label("Name : ");
        Label phoneNumberLabel = new Label("Phone Number : ");
        Label pointsLabel = new Label("Points : ");
        membershipLabel.getStyleClass().add("sub-title");
        phoneNumberLabel.getStyleClass().add("sub-title");
        nameLabel.getStyleClass().add("sub-title");
        pointsLabel.getStyleClass().add("sub-title");
        labelCol.getChildren().addAll(membershipLabel,nameLabel,phoneNumberLabel,pointsLabel);

        VBox valueCol = new VBox();
        valueCol.setSpacing(20);
        valueCol.setMaxHeight(Double.MAX_VALUE);
        valueCol.getChildren().addAll(this.updatedMembershipComboBox,this.updatedNameField,this.updatedPhoneNumberField,this.updatedPointsField);
        Button returnBack = new Button("Back");
        returnBack.setOnAction(backToPage ->{
            userPicture.getChildren().clear();
            userPicture.getChildren().addAll(this.userTitle,this.scrollPane);
        });

        HBox boxContainer = new HBox();
        boxContainer.setSpacing(20);
        boxContainer.setMaxWidth(Double.MAX_VALUE);
        boxContainer.setPadding(new Insets(20,0,0,50));
        boxContainer.getChildren().addAll(updateButton(),returnBack);
        returnBack.getStyleClass().add("submit-btn");
        returnBack.setPrefWidth(100);
        containAll.getChildren().addAll(labelCol,valueCol);
        spesificUser.getChildren().addAll(containAll,boxContainer);
        return spesificUser;
    }

    private  Button updateButton(){
        Button button = new Button("Update");
        button.getStyleClass().add("submit-btn");
        button.setPrefWidth(100);
        button.setOnAction(event -> {
            String selectedMembership = this.updatedMembershipComboBox.getValue();
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
                    Member member = new Member(true, th, nameField.getText(), phoneNumberField.getText(), points);

                    userManager.addUser(member);
                    break;
                case "VIP":
                    TransactionHistory thNew = new TransactionHistory();
                    int pointsNew = parsePointsField();
                    if (pointsNew == -1) {
                        return;
                    }
                    VIP vip = new VIP(true, thNew, nameField.getText(), phoneNumberField.getText(), pointsNew);

                    userManager.addUser(vip);
                    break;
            }
            for (User u: userManager.getListOfUsers()){
                System.out.println(u.toString());
            }
            clearFields();
            setAlert();
        });
        return button;
    }
}