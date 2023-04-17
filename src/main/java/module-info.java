module org.objectify {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.objectify to javafx.fxml;

    exports org.objectify;
    exports org.objectify.controller;
    opens org.objectify.controller to javafx.fxml;
}
