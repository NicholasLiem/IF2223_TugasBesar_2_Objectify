package com.objectify.controllers.pages;

import com.objectify.datastore.SystemPointOfSales;
import com.objectify.models.entities.*;
import com.objectify.models.transactions.Transaction;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TransactionManagerPage extends Pane {

    private HBox rows;
    private VBox userList;
    private VBox transactionsList;

    private ArrayList<Transaction> listOfTransactions;

    public TransactionManagerPage(){

        UserManager um = SystemPointOfSales.getInstance().getUserManager();
        Path cssPath = Paths.get("ObjectifyMainApp","src", "resources", "css", "transactionManager.css");
        String cssUrl = cssPath.toUri().toString();
        this.getStylesheets().add(cssUrl);

        this.rows = new HBox();
        rows.setSpacing(30);
        rows.getStyleClass().add("background");
        rows.setPadding(new Insets(10,0,10,10));

        this.transactionsList = new VBox();
        transactionsList.setMaxHeight(Double.MAX_VALUE);
        transactionsList.getStyleClass().add("transactions-list");

        HBox.setHgrow(this.transactionsList, Priority.ALWAYS);

        this.userList = new VBox();
        userList.setMaxHeight(Double.MAX_VALUE);
        this.userList.setPadding(new Insets(0,10,0,0));
        VBox.setVgrow(this.userList,Priority.ALWAYS);
        ScrollPane userData = new ScrollPane();
        userData.getStyleClass().add("user-scrollpane");
        userData.setMaxWidth(500);

        VBox allUserDatas = new VBox();
        allUserDatas.setSpacing(10);
        allUserDatas.setMaxHeight(Double.MAX_VALUE);
        userData.setFitToWidth(true);

        for(User u : um.getListOfUsers()){
            HBox aData = new HBox();
            aData.setMaxWidth(Double.MAX_VALUE);
            aData.getStyleClass().add("single-data");
            aData.setSpacing(10);
            aData.getChildren().addAll(labelCol(u),valueCol(u));
            aData.setAlignment(Pos.CENTER);
            aData.setOnMouseClicked(event->{
                this.listOfTransactions = u.getTransactionHistory().getTransactions();
                for(Transaction t : this.listOfTransactions){
                    HBox transaction = new HBox();
                    HBox.setHgrow(transaction,Priority.ALWAYS);
                    Label id = new Label(Integer.toString(t.getTransactionId());
                    Label amount = new Label (Double.toString(t.getAmount()));
                    Text date = new Text(t.getDateTime());
                }
            });
            allUserDatas.getChildren().add(aData);
            allUserDatas.setAlignment(Pos.CENTER);
        }
        userData.setContent(allUserDatas);
        this.userList.setAlignment(Pos.CENTER);
        this.userList.getStyleClass().add("userlist");
        this.userList.getChildren().add(userData);

        HBox.setHgrow(this.userList,Priority.ALWAYS);

        rows.getChildren().addAll(transactionsList,userList);

        BorderPane mainContainer = new BorderPane();
        mainContainer.setCenter(this.rows);
        mainContainer.prefWidthProperty().bind(this.widthProperty());
        mainContainer.prefHeightProperty().bind(this.heightProperty());
        this.getChildren().add(mainContainer);

    }

    public  VBox labelCol(User u){
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

}
