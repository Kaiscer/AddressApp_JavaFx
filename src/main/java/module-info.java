module com.github.kaiscer {
    requires javafx.controls;
    requires javafx.fxml;



    opens com.github.kaiscer to javafx.fxml;
    exports com.github.kaiscer;

}