package com.cth.inventoryManagment;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
  * Defining Controller superclass to avoid duplicating too much code throughout the application. By having my controller classes inherit from this superclass I can extend functionality without having to write the same method repeatedly.
  * @author Tyler Hampton (Cory)
  */
public class Controller {
     /**
      * This method opens a new view
      * @param event - button click or other UI event
      * @param view - view where we will go
      * @param title - new view title
      * @throws IOException
      */
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

    /**
     * Method to cancel and return to main view
     * @param event
     * @throws IOException
     */
    public void cancelAndReturn(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel? Your changes will not be saved.");
        Optional<ButtonType> confirm = alert.showAndWait();
        if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
            returnToMain(event);
        }
    }

     /**
      * Return to main view
      * @param event - button click or other UI event
      * @throws IOException
      */
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

     /**
      * Method to exit the application
      * @param stage - the stage of the current view we will be closing
      * @param anchorPane - anchor pane of the current view we will be closing
      */
    public void exitApplication(Stage stage, AnchorPane anchorPane) {
        stage = (Stage) anchorPane.getScene().getWindow();
        System.out.println("Exit Application");
        stage.close();
    }
}
