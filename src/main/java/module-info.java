module com.objectify {
    requires javafx.controls;
    opens com.objectify;
    exports com.objectify;
    exports com.objectify.controllers;
    opens com.objectify.controllers;
}
