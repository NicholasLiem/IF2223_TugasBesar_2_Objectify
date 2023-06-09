package com.objectify.controllers.pages;

import com.objectify.datastore.SystemPointOfSales;
import com.objectify.models.entities.*;
import com.objectify.models.transactions.TransactionHistory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class RegisterMemberPage extends Pane {

    private Label nameLabel;
    private Label phoneNumberLabel;
    private Label pointsLabel;

    private Label imageLabel;
    private Label userTitle;

    private ComboBox<String> membershipComboBox;
    private ComboBox<String> activationStatusComboBox;
    private TextField nameField;
    private TextField phoneNumberField;
    private TextField pointsField;

    private ComboBox<String> updatedMembershipComboBox;

    private TextField updatedNameField;
    private TextField updatedPhoneNumberField;
    private TextField updatedPointsField;
    private ArrayList<User> listOfUsers;

    private VBox userList;

    private  ScrollPane scrollPane;

    private int selectedUserId;
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

        this.userList = new VBox();
        userList.setSpacing(30);
        userList.setPadding(new Insets(20,30,20,30));
        userList.getStyleClass().add("pict-container");
        userList.setAlignment(Pos.TOP_CENTER);
        userList.prefWidthProperty().bind(row.widthProperty().multiply(0.4).subtract(30));
        this.userTitle = new Label("Our users");
        userTitle.getStyleClass().add("title");
        updateScroll();
//        Forms di sebelah kiri
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
        HBox.setHgrow(userList, Priority.ALWAYS);

//        Parent dari forms dan userList
        row.getChildren().addAll(forms,userList);
        row.getStyleClass().add("forms");

        BorderPane mainContainer = new BorderPane();
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

    private void clearUpdateFields(){
        this.updatedNameField.clear();
        this.updatedPhoneNumberField.clear();
        this.updatedPointsField.clear();
    }

    private int parsePointsField(String pointText) {
        int points = 0;
        try {
            points = Integer.parseInt(pointText);
        } catch (NumberFormatException e) {
            errorAlert("Error","Invalid input","Points must be a number");
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
            UserManager userManager = SystemPointOfSales.getInstance().getUserManager();

            switch (selectedMembership) {
                case "Customer":
                    Customer customer = new Customer();
                    customer.setActivationStatus(true);
                    userManager.addUser(customer);
                    break;
                case "Member":
                    TransactionHistory th = new TransactionHistory();
                    int points = parsePointsField(this.pointsField.getText());
                    if (points == -1) {
                        return;
                    }
                    Member member = new Member(true, th, nameField.getText(), phoneNumberField.getText(), points);
                    userManager.addUser(member);
                    break;
                case "VIP":
                    TransactionHistory thNew = new TransactionHistory();
                    int pointsNew = parsePointsField(this.pointsField.getText());
                    if (pointsNew == -1) {
                        return;
                    }
                    VIP vip = new VIP(true, thNew, nameField.getText(), phoneNumberField.getText(), pointsNew);
                    userManager.addUser(vip);
                    break;
            }
            SystemPointOfSales.getInstance().getSettings().saveAllDataStore();
            clearFields();
            updateScroll();
            setAlert("Customer created","New Customer Succesfully Created");
        });
        buttonBox.getChildren().add(button);
        return buttonBox;
    }

    private void updateScroll(){
        this.userList.getChildren().clear();
        VBox allMembers = new VBox();
        allMembers.setMaxHeight(Double.MAX_VALUE);
        allMembers.setSpacing(20);

        UserManager um = SystemPointOfSales.getInstance().getUserManager();
        this.listOfUsers = um.getListOfUsers();
        this.scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setContent(allMembers);
        scrollPane.setPadding(new Insets(12));
        scrollPane.setFitToWidth(true);

        for(User user : this.listOfUsers){
//            Container untuk menampung data sebuah User
            HBox dataUser = new HBox();
            dataUser.setMaxWidth(Double.MAX_VALUE);
            dataUser.getStyleClass().add("single-data");
            dataUser.setSpacing(10);
            if(user instanceof  Member){
                String name = ((Member)user).getName();
                Text userName = new Text(name);
                userName.getStyleClass().add("member-name");
                String number = ((Member)user).getPhoneNumber();
                String points = Integer.toString(((Member)user).getPoints());
                dataUser.getChildren().addAll(labelCol(user),valueCol(user));
                dataUser.setOnMouseClicked(event ->{
                    this.selectedUserId = user.getUserID();
                    this.userList.getChildren().clear();
                    this.userList.getChildren().add(selectedUser(name,"Member",number,points));
                });

            }
            if (user instanceof VIP){
                String name = ((VIP)user).getName();
                String number = ((VIP)user).getPhoneNumber();
                String points = Integer.toString(((VIP)user).getPoints());
                dataUser.getChildren().addAll(labelCol(user),valueCol(user));
                dataUser.setOnMouseClicked(event ->{
                    this.selectedUserId = user.getUserID();
                    this.userList.getChildren().clear();
                    this.userList.getChildren().addAll(selectedUser(name,"VIP",number,points));
                });
            }

            if(user instanceof Customer){
                Integer id = user.getUserID();
                Text user_id = new Text(id.toString());
                user_id.getStyleClass().add("member-name");
                dataUser.getChildren().addAll(labelCol(user),valueCol(user));
                dataUser.setOnMouseClicked(event -> {
                    this.selectedUserId = user.getUserID();
                    userList.getChildren().clear();
                    userList.getChildren().addAll(selectedUser(" ","Customer"," "," "));
                });
            }
            allMembers.getChildren().add(dataUser);
        }

        allMembers.setPadding(new Insets(10));
        allMembers.setStyle("-fx-background-color: #E9EFFD;");
        
        userList.getChildren().addAll(userTitle,scrollPane);
    }

    private void setAlert(String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private  void errorAlert(String title,String header,String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private VBox labelCol(User u){
        VBox labelCol = new VBox();
        Label idLabel = new Label("Id : ");
        Label typeLabel = new Label("Type");
        idLabel.getStyleClass().add("sub-title");
        typeLabel.getStyleClass().add("sub-title");
        labelCol.getChildren().addAll(idLabel,typeLabel);
        if( u instanceof  Customer){
            return labelCol;
        }else{
            Label nameLabel = new Label("Name : ");
            nameLabel.getStyleClass().add("sub-title");
            labelCol.getChildren().add(nameLabel);
            return labelCol;
        }

    }
    public VBox valueCol(User u){
        VBox valueCol = new VBox();
        valueCol.setSpacing(4);
        Text id = new Text(Integer.toString( u.getUserID()));
        Text type = new Text(u.getType());
        id.getStyleClass().add("content");
        type.getStyleClass().add("content");
        valueCol.getChildren().addAll(id,type);
        if(u instanceof  Customer){
            return valueCol;
        }

        if(u instanceof  Member){
            Text name = new Text(((Member) u).getName());
            name.getStyleClass().add("content");
            valueCol.getChildren().add(name);
            return valueCol;
        }

        else{
            Text name = new Text (((VIP) u).getName());
            valueCol.getChildren().add(name);
            name.getStyleClass().add("content");
            return  valueCol;
        }
    }
    private VBox selectedUser(String oldName, String type, String oldNumber, String oldPoints){

        this.updatedNameField = new TextField();
        this.updatedPhoneNumberField = new TextField();
        this.updatedPointsField = new TextField();
        this.updatedMembershipComboBox = new ComboBox<>();
        this.updatedMembershipComboBox.getItems().addAll("Customer", "Member", "VIP");
        this.activationStatusComboBox = new ComboBox<>();
        this.activationStatusComboBox.getItems().addAll("Active","Not Active");

        this.updatedNameField.setText(oldName);
        this.updatedPhoneNumberField.setText(oldNumber);
        this.updatedPointsField.setText(oldPoints);
        this.updatedMembershipComboBox.setValue(type);
        this.activationStatusComboBox.setValue("Active");

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
        Label statusLabel = new Label("Activation Status : ");
        membershipLabel.getStyleClass().add("sub-title");
        phoneNumberLabel.getStyleClass().add("sub-title");
        nameLabel.getStyleClass().add("sub-title");
        pointsLabel.getStyleClass().add("sub-title");
        statusLabel.getStyleClass().add("sub-title");
        labelCol.getChildren().addAll(membershipLabel,nameLabel,phoneNumberLabel,pointsLabel,statusLabel);

        VBox valueCol = new VBox();
        valueCol.setSpacing(20);
        valueCol.setMaxHeight(Double.MAX_VALUE);
        valueCol.getChildren().addAll(this.updatedMembershipComboBox,this.updatedNameField,this.updatedPhoneNumberField,this.updatedPointsField,this.activationStatusComboBox);
        Button returnBack = new Button("Back");
        returnBack.setOnAction(backToPage ->{
            userList.getChildren().clear();
            userList.getChildren().addAll(this.userTitle,this.scrollPane);
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
            UserManager userManager = SystemPointOfSales.getInstance().getUserManager();
            User currentUser = userManager.getUser(this.selectedUserId);
            String currentMembership = currentUser.getType();
            switch (currentMembership) {
                case "Customer":
                    if(selectedMembership == "Customer"){

                        String active = this.activationStatusComboBox.getValue();
                        boolean status = false;
                        if(active == "Active"){
                            status = true;
                        }
                        userManager.updateCustomer(this.selectedUserId,status);
                        break;
                    }
                    User selected = userManager.getUser(this.selectedUserId);
                    TransactionHistory temp = selected.getTransactionHistory();
                    if(temp.getTransactions().size() < 1){
                        errorAlert("Error","Upgrade failed","You need at least one transaction to upgrade");
                        return;
                    };
                    if(selectedMembership == "Member"){
                        String newName = this.updatedNameField.getText().trim();
                        String newPhoneNumber = this.updatedPhoneNumberField.getText().trim();
                        String pointsField = this.updatedPointsField.getText().trim();
                        String active = this.activationStatusComboBox.getValue();
                        boolean status = false;
                        if(active == "Active"){
                            status = true;
                        }
                        int points = parsePointsField(pointsField);
                        if(points == -1){
                            return;
                        }
                        userManager.upgradeCustomer((Customer)currentUser,status,newName,newPhoneNumber,points,"Member");
                        break;
                    }
                    if(selectedMembership == "VIP"){
                        String newName = this.updatedNameField.getText().trim();
                        String newPhoneNumber = this.updatedPhoneNumberField.getText().trim();
                        String pointsField = this.updatedPointsField.getText().trim();
                        String active = this.activationStatusComboBox.getValue();
                        boolean status = false;
                        if(active == "Active"){
                            status = true;
                        }
                        int points = parsePointsField(pointsField);
                        if(points == -1){
                            return;
                        }
                        userManager.displayUsers();
                        userManager.upgradeCustomer((Customer) currentUser,status,newName,newPhoneNumber,points,"VIP");
                        userManager.displayUsers();
                        break;
                    }
                    break;
                case "Member":
                    if(selectedMembership == "Customer"){
                        errorAlert("Error","Upgrade failed","Member can't be downgraded to customer");
                        return;
                    }
                    if(selectedMembership == "Member"){
                        String newName = this.updatedNameField.getText().trim();
                        String newPhone = this.updatedPhoneNumberField.getText().trim();
                        String newPoints = this.updatedPointsField.getText().trim();
                        String status = this.activationStatusComboBox.getValue();
                        boolean active = false;
                        if(status == "Active"){
                            active= true;
                        }
                        int points = parsePointsField(newPoints);
                        if(points == -1){
                            return;
                        }
                        userManager.updateMember(this.selectedUserId,active,newName,newPhone,points);
                        break;
                    }
                    if(selectedMembership == "VIP"){
                        String newName = this.updatedNameField.getText().trim();
                        String newPhone = this.updatedPhoneNumberField.getText().trim();
                        String newPoints = this.updatedPointsField.getText().trim();
                        String status = this.activationStatusComboBox.getValue();
                        boolean active = false;
                        if(status == "Active"){
                            active= true;
                        }
                        int points = parsePointsField(newPoints);
                        if(points == -1){
                            return;
                        }
                        userManager.upgradeMember((Member)currentUser,active,newName,newPhone,points);
                    }
                    break;
                case "VIP":
                    if(selectedMembership == "Customer"){
                        errorAlert("Error","Upgrade failed","Member can't be downgraded to customer");
                        return;
                    }
                    if(selectedMembership == "Member"){
                        String newName = this.updatedNameField.getText().trim();
                        String newPhone = this.updatedPhoneNumberField.getText().trim();
                        String newPoints = this.updatedPointsField.getText().trim();
                        String status = this.activationStatusComboBox.getValue();
                        boolean active = false;
                        if(status == "Active"){
                            active= true;
                        }
                        int points = parsePointsField(newPoints);
                        if(points == -1){
                            return;
                        }
                        userManager.convertVIP((VIP) currentUser,active,newName,newPhone,points);
                        break;
                    }
                    if(selectedMembership == "VIP"){
                        String newName = this.updatedNameField.getText().trim();
                        String newPhone = this.updatedPhoneNumberField.getText().trim();
                        String newPoints = this.updatedPointsField.getText().trim();
                        String status = this.activationStatusComboBox.getValue();
                        boolean active = false;
                        if(status == "Active"){
                            active= true;
                        }
                        int points = parsePointsField(newPoints);
                        if(points == -1){
                            return;
                        }
                        userManager.updateVIP(this.selectedUserId,active,newName,newPhone,points);
                    }
                    TransactionHistory thNew = new TransactionHistory();
                    int pointsNew = parsePointsField(this.updatedPointsField.getText());
                    if (pointsNew == -1) {
                        return;
                    }
                    VIP vip = new VIP(true, thNew, nameField.getText(), phoneNumberField.getText(), pointsNew);

                    userManager.addUser(vip);
                    break;
            }
            clearUpdateFields();
            updateScroll();
            setAlert("Customer Updated","Customer's Data is Succesfully Updated");
        });
        return button;
    }

    private boolean validCustomer(){
        
        return true;
    }
}
