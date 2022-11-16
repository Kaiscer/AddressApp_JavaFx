package com.github.kaiscer.controllers;

import com.github.kaiscer.MainApp;
import com.github.kaiscer.model.Person;
import com.github.kaiscer.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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
    private Label lbFirstName;
    @FXML
    private Label lbLastName;
    @FXML
    private Label lbStreet;
    @FXML
    private Label lbPostalCode;
    @FXML
    private Label lbCity;
    @FXML
    private Label lbBirthday;


    /**
     * Este método se llama una vez se carga el fxml e Inicializa la clase controladora.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        // Clear person details.
        showPersonDetails(null);


        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));

    }

    private void showPersonDetails(Person person){
        // Lo primero es comprobar si el objeto pero tiene datos

        if(person != null){
            // Asignamos el texto a cada labe con los métodos setText

            lbFirstName.setText(person.getFirstName());
            lbLastName.setText(person.getLastName());
            lbStreet.setText(person.getStreet());
            // Para los dato int casteamos
            lbPostalCode.setText(Integer.toString(person.getPostalCode()));
            lbCity.setText(person.getCity());
            lbBirthday.setText(DateUtil.format(person.getBirthday()));
        }else {
            // Person is null, remove all the text.
            lbFirstName.setText("");
            lbLastName.setText("");
            lbStreet.setText("");
            lbPostalCode.setText("");
            lbCity.setText("");
            lbBirthday.setText("");
        }

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        //Rellena los datos de la tabla
        personTable.setItems(mainApp.getPersonData());
    }

    /**
     * Called when the user clicks on the delete button
     */
    @FXML
    private void handleDeletePerson(){
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0){
        personTable.getItems().remove(selectedIndex);
        }else {
            //Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please selected a person in the table");

            alert.showAndWait();
        }

    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewPerson(){
        Person temPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(temPerson);
        if (okClicked){
            mainApp.getPersonData().add(temPerson);
        }
    }

    @FXML
    private void handleEditPerson(){
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null){
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked){
                showPersonDetails(selectedPerson);
            }
        }else {
            // Nothing selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
}
