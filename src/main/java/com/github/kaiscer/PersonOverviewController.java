package com.github.kaiscer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PersonOverviewController {

    private MainApp mainApp;

    // Instanciamos los nodos que vamos a manejar en el controlador
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label street;
    @FXML
    private Label postalCode;
    @FXML
    private Label city;
    @FXML
    private Label birthday;


    /**
     * Este método se llama una vez se carga el fxml e Inicializa la clase controladora.
     */
    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        //Rellena los datos de la tabla
        personTable.setItems(mainApp.getPersonData());
    }
}
