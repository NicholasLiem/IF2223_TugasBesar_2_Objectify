module com.objectify {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.objectify to javafx.fxml;
    exports com.objectify;
    exports com.objectify.controllers;
    opens com.objectify.controllers to javafx.fxml;
}
