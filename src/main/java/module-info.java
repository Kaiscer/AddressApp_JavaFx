module com.github.kaiscer.actividad_3_di {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.github.kaiscer.actividad_3_di to javafx.fxml;
    exports com.github.kaiscer.actividad_3_di;
}