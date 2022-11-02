package com.github.kaiscer.control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws IOException {
       this.primaryStage = primaryStage;
       this.primaryStage.setTitle("AddressApp"); //

        initRootLayout();

        showPersonOverview();

    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Show the person overview inside the root layout
     */
    private void showPersonOverview() {

        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("src/main/java/com/github/kaiscer/view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Initializes the root layout
     */
    private void initRootLayout() {


        try {
          // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("src/main/java/com/github/kaiscer/view/RootLayout.fxml"));
                rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}