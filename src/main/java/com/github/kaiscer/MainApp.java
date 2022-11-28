package com.github.kaiscer;

import com.github.kaiscer.controllers.PersonEditDialogController;
import com.github.kaiscer.controllers.PersonOverviewController;
import com.github.kaiscer.model.Person;
import com.github.kaiscer.model.PersonListWrapper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.*;
import java.io.File;
import java.io.IOException;

import java.util.prefs.Preferences;


public class MainApp extends Application {

    private static final String PATH_ROOT_LAYOUT = "RootLayout.fxml";
    private static final String PATH_PERSON_OVERVIEW = "PersonOverview.fxml";

    private static final String PATH_PERSON_EDIT = "PersonEditDialog.fxml";
    private BorderPane rootLayout;
    private AnchorPane personOverview;

    private Stage primaryStage;

    //Instancia de objeto ObservaleList para manejar los datos y añadirlos
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    /**
     * In this method we initialise the loadLayout
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("AddressApp");
        loadLayouts();
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();

        this.primaryStage = primaryStage;
        this.primaryStage.getIcons().add(new javafx.scene.image.Image("file:resource/images/addressBook_icon.png"));

    }

    /**
     * We manage all layouts in one single method
     */
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

    public Stage getPrimaryStage(){
        return primaryStage;
    }
    public boolean showPersonEditDialog(Person person){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(PATH_PERSON_EDIT));
            AnchorPane page = (AnchorPane) loader.load();

            //Create the dialog Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //Set the person into the controller
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            //Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Returns the preference of the person file, i.e. the file that was last opened.
     * @return
     */
    public File getPersonFilePath(){
        Preferences pref = Preferences.userNodeForPackage(MainApp.class);
        String filePath = pref.get("filePath", null);
        if (filePath != null){
            return new File(filePath);
        }else {
             return null;
        }

    }

    public void setPersonFilePath(File file){
        Preferences pref = Preferences.userNodeForPackage(MainApp.class);
        if (file != null){
            pref.put("filePath", file.getPath());
            // Update the stage title.
            primaryStage.setTitle("AddressApp - " + file.getName());
        }else{
            pref.remove("filePath");
            // Update the stage title.
            primaryStage.setTitle("AddressApp");
        }
    }

    /**
     * Loads person data from the specified file. The current person data will
     *  * be replaced.
     * @param file
     */
    public void loadPersonDataFromFile(File file){
        try {
            JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

            personData.clear();
            personData.addAll(wrapper.getPersons());

            // Save the file path to the registry.
            setPersonFilePath(file);

        } catch (Exception e) { // We Capture any exception
           e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not load data from file\n" + file.getPath());
            alert.showAndWait();
        }
    }

    public void savePersonDataToFile(File file){

        try {
            JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);

            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(personData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setPersonFilePath(file);



        } catch (Exception e) {
            e.printStackTrace();// We Capture any exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Could not save data from file:\n" + file.getPath());
            alert.showAndWait();
        }
    }



    public static void main(String[] args) {
        launch();
    }



}
