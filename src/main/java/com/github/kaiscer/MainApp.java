package com.github.kaiscer;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private static final String PATH_ROOT_LAYOUT = "RootLayout.fxml";
    private static final String PATH_PERSON_OVERVIEW = "PersonOverview.fxml";
    private BorderPane rootLayout;
    private AnchorPane personOverview;

    //Instancia de objeto ObservaleList para manejar los datos y añadirlos
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    @Override
    public void start(Stage stage){
        stage.setTitle("AddressApp");
        loadLayouts();
        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);
        stage.show();


    }

    private void loadLayouts() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(PATH_ROOT_LAYOUT));
            rootLayout = loader.load();

            FXMLLoader loader2 = new FXMLLoader();
            loader2.setLocation(MainApp.class.getResource(PATH_PERSON_OVERVIEW));
            personOverview = loader2.load();

            rootLayout.setCenter(personOverview);

            PersonOverviewController controller = loader2.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor
     */
    public MainApp() {
        // Datos de ejemplo:
        personData.add(new Person("Pedro", "Alvarez"));
        personData.add(new Person("Hector", "Gonzalez"));
        personData.add(new Person("Francisco", "Gonzalez"));
        personData.add(new Person("Andres", "Freitas"));
        personData.add(new Person("Alejandro", "Ramos"));
        personData.add(new Person("Ezequiel", "Ramos"));
        personData.add(new Person("Miguel", "Carola"));
        personData.add(new Person("Mattias", "Vasquez"));
        personData.add(new Person("Alfredo", "Velez"));
    }

    //Retorna estos datos de ejemplo
    public ObservableList<Person> getPersonData() {
        return personData;
    }

    public static void main(String[] args) {
        launch();
    }


}
