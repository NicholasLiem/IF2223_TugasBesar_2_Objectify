module com.objectify {
    requires javafx.controls;
    requires com.fasterxml.jackson.databind;
    opens com.objectify;
    exports com.objectify;
    exports com.objectify.controllers;
    opens com.objectify.controllers;
}
