module com.github.kaiscer {
    requires javafx.controls;
    requires javafx.fxml;



    opens com.github.kaiscer to javafx.fxml;
    exports com.github.kaiscer;

    exports com.github.kaiscer.model;
    opens com.github.kaiscer.model to javafx.fxml;

    exports com.github.kaiscer.controllers;
    opens com.github.kaiscer.controllers to javafx.fxml;

}