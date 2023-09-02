package com.example.software1_invmgmnt_cth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class MainController {
    // main anchor pane
    @FXML
    private AnchorPane mainPane;
    // exit button
    @FXML
    private Button exitButton;
    // add part button
    @FXML
    private Button addPartButton;
    // modify part button
    @FXML
    private Button modifyPartButton;
    // delete part button
    @FXML
    private Button deletePartButton;

    // add product button
    @FXML
    private Button addProductButton;
    // modify product button
    @FXML
    private Button modifyProductButton;
    // delete product button
    @FXML
    private Button deleteProductButton;

    // stage for this view
    Stage stage;

    @FXML
    public void openAddPartView(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("addpart-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void openAddProductView(ActionEvent event) throws  IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("addproduct-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void openModifyProductView(ActionEvent event) throws  IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("modifyproduct-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Modify Product");
        stage.setScene(scene);
        stage.show();
    }

    // exit application method
    public void exitApplication(ActionEvent event) {
        stage = (Stage) mainPane.getScene().getWindow();
        System.out.println("Exit Application");
        stage.close();
    }
}