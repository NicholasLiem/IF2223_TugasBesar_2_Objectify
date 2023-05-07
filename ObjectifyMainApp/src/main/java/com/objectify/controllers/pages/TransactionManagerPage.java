package com.objectify.controllers.pages;

import com.objectify.datastore.SystemPointOfSales;
import com.objectify.models.entities.*;
import com.objectify.models.transactions.Transaction;
import com.objectify.models.transactions.TransactionHistory;
import com.objectify.models.items.*;
import com.objectify.exceptions.ItemNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map.Entry;

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
        transactionsList.setPrefWidth(250);

        HBox.setHgrow(this.transactionsList, Priority.ALWAYS);

        VBox selectedUser = new VBox();
        selectedUser.setMinWidth(70);
        selectedUser.setAlignment(Pos.CENTER);

        this.userList = new VBox();
        userList.setMaxHeight(Double.MAX_VALUE);
        this.userList.setPadding(new Insets(0,10,0,0));
        VBox.setVgrow(this.userList,Priority.ALWAYS);
        ScrollPane userData = new ScrollPane();
        userData.getStyleClass().add("user-scrollpane");
        userData.setMaxWidth(500);
        userData.setMinWidth(200);

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
                updateSelectedUser(true, u);
                updateTransactionsPane(u);
            });
            allUserDatas.getChildren().add(aData);
            allUserDatas.setAlignment(Pos.CENTER);
        }
        userData.setContent(allUserDatas);
        this.userList.setAlignment(Pos.CENTER);
        this.userList.getStyleClass().add("userlist");
        this.userList.getChildren().add(userData);

        HBox.setHgrow(this.userList,Priority.ALWAYS);

        rows.getChildren().addAll(transactionsList,selectedUser,userList);

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

    private void updateTransactionsPane(User user) {
        ScrollPane transactionsScrollPane = new ScrollPane(); 
        VBox allTransactionsData = new VBox();
        allTransactionsData.setPadding(new Insets(10));
        allTransactionsData.setSpacing(10);
        allTransactionsData.setMaxHeight(Double.MAX_VALUE);
        allTransactionsData.setPrefWidth(250);
        transactionsScrollPane.setFitToWidth(true);
        
        // Create a cart and add some items to it
        ShoppingCart cart = new ShoppingCart();
        cart.addCartItem(1,3);
        cart.addCartItem(2, 2);
        cart.addCartItem(3, 1);
        
        TransactionHistory transactions2 = new TransactionHistory();
        for (int i = 0; i < 10; i++) {
            String desc = "desc" + (i+1);
            transactions2.addTransaction(new Transaction(i+1,"today", desc, 12, cart));
        }
        
        user.setTransactionHistory(transactions2);
        
        TransactionHistory transactions = user.getTransactionHistory();
        for (Transaction transaction : transactions.getTransactions()) {
            HBox box = new HBox();
            box.setAlignment(Pos.CENTER_LEFT);
            box.getStyleClass().add("single-data");
            VBox transactionBox = new VBox();
            transactionBox.setPrefWidth(200);
            HBox.setHgrow(transactionBox,Priority.ALWAYS);
            Text id = new Text("ID             : " + Integer.toString(transaction.getTransactionId()));
            Text datetime = new Text("DateTime : " + transaction.getDateTime() + "\n");
            ShoppingCart userCart = transaction.getShoppingCart();
            Label cartLabel = new Label(cartString(userCart));
            double discountVal = 0;
            try {
                discountVal += userCart.value() - transaction.getAmount();
            } catch (ItemNotFoundException e) {
                throw new RuntimeException(e);
            }
            Text discount = new Text("Discount : " + Double.toString(discountVal));
            Text amount2 = new Text("Total       : " + Double.toString(transaction.getAmount()));
            transactionBox.getChildren().addAll(id,datetime,cartLabel,discount,amount2);
            Button print = new Button("Print Transaction");
            print.setOnAction(event -> {
                transaction.printToPDF();
            });
            print.getStyleClass().add("button");
            box.getChildren().addAll(transactionBox,print);
            HBox.setHgrow(print, Priority.ALWAYS);
            allTransactionsData.getChildren().add(box);
        }
        transactionsScrollPane.setContent(allTransactionsData);
        BorderPane borderPane = (BorderPane) this.getChildren().get(0);
        HBox row = (HBox) borderPane.getChildren().get(0);
        VBox transactionsList = (VBox) row.getChildren().get(0);
        if (transactionsList.getChildren().size() == 0) {
            transactionsList.getChildren().add(transactionsScrollPane);
        } else {
            transactionsList.getChildren().set(0,transactionsScrollPane);
        }
    }

    private String cartString(ShoppingCart shoppingCart) {
        StringBuilder sb = new StringBuilder();
        for (Entry<Integer, Integer> entry : shoppingCart.getCartItems().entrySet()) {
            sb.append("Qty: ");
            sb.append(entry.getValue());
            sb.append("     Product: ");
            try {
                sb.append(SystemPointOfSales.getInstance().getStorageManager().searchById(entry.getKey()).getProductName());
            } catch (ItemNotFoundException e) {
                throw new RuntimeException(e);
            }
            sb.append("     Unit Price: ");
            try {
                sb.append(SystemPointOfSales.getInstance().getStorageManager().searchById(entry.getKey()).getProductPrice());
            } catch (ItemNotFoundException e) {
                throw new RuntimeException(e);
            }
            sb.append("     Total Price: ");
            try {
                sb.append(SystemPointOfSales.getInstance().getStorageManager().searchById(entry.getKey()).getProductPrice() * entry.getValue());
            } catch (ItemNotFoundException e) {
                throw new RuntimeException(e);
            }
            sb.append("\n");
        }
        try {
            sb.append("\nSubtotal  : " + shoppingCart.value() + "\n");
        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    } 

    private void updateSelectedUser(boolean selected, User user) {
        BorderPane borderPane = (BorderPane) this.getChildren().get(0);
        HBox row = (HBox) borderPane.getChildren().get(0);
        VBox selectedUser = (VBox) row.getChildren().get(1);
        if (selected) {
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.setSpacing(10);
            Label slctdUser = new Label("User " + user.getUserID() + "'s Transaction History");
            slctdUser.getStyleClass().add("sub-title");
            Button unselectButton = new Button("Unselect User");
            unselectButton.setOnAction(event -> {
                updateSelectedUser(false, null);
            });
            unselectButton.getStyleClass().add("button");
            Button printLaporan = new Button("Print User's Transaction History");
            printLaporan.setOnAction(event -> { 
                user.getTransactionHistory().printToPDF();
            });
            printLaporan.getStyleClass().add("button");
            box.getChildren().addAll(slctdUser, printLaporan, unselectButton);
            if (selectedUser.getChildren().size() == 0){
                selectedUser.getChildren().add(box);
            } else {
                selectedUser.getChildren().set(0, box);
            }
        } else {
            selectedUser.getChildren().remove(0);
            VBox transactionsList = (VBox) row.getChildren().get(0);
            transactionsList.getChildren().remove(0);
        }
    }
}
