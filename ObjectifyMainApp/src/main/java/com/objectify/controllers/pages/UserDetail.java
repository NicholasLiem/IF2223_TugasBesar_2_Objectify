package com.objectify.controllers.pages;

import com.objectify.models.entities.Member;
import com.objectify.models.entities.User;
import com.objectify.models.entities.VIP;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UserDetail extends Pane {
    public UserDetail(String nama, String number, Integer pointsValue){
        VBox spesificUser = new VBox();
        spesificUser.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        spesificUser.setAlignment(Pos.CENTER);
        Text titleUserName = new Text(nama);

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
        Text phoneNumberValue = new Text(number);
        phoneNumberValue.getStyleClass().add("user-data");

        Label userPoints = new Label("Points : ");
        userPoints.getStyleClass().add("sub-title");
        String points = pointsValue.toString();
        Text userPointsValue = new Text(points);
        userPointsValue.getStyleClass().add("user-data");

        labelCol.getChildren().addAll(phoneNumber,userPoints);
        userValue.getChildren().addAll(phoneNumberValue,userPointsValue);

        titleUserName.getStyleClass().add("member-name-title");
        containAll.getChildren().addAll(labelCol,userValue);

        Button returnBack = new Button("Back");
        returnBack.setOnAction(backToPage ->{

        });

        spesificUser.getChildren().addAll(titleUserName,containAll,returnBack);
    }
}
