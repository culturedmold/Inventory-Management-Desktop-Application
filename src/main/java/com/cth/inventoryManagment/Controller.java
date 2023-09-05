package com.cth.inventoryManagment;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

// Defining Controller superclass to avoid duplicating too much code throughout the application. By having my controller
// classes inherit from this superclass I can extend functionality without having to write the same method repeatedly.
public class Controller {
    // Open a new view
    public void openNewView(ActionEvent event, String view, String title) throws IOException {
        // View we want to open
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(view));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set title for new view
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    // Cancel method
    public void returnToMain(ActionEvent event) throws IOException {
        // View we want to return to after cancelling operation
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set title for return view
        stage.setTitle("Inventory Manager");
        stage.setResizable(false);
        stage.setScene(scene);
    }

    // This method exits the application
    public void exitApplication(Stage stage, AnchorPane anchorPane) {
        stage = (Stage) anchorPane.getScene().getWindow();
        System.out.println("Exit Application");
        stage.close();
    }
}
