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

public class MainController extends Controller {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Button exitButton;
    @FXML
    private Button addPartButton;
    @FXML
    private Button modifyPartButton;
    @FXML
    private Button deletePartButton;
    @FXML
    private Button addProductButton;
    @FXML
    private Button modifyProductButton;
    @FXML
    private Button deleteProductButton;

    // stage for this view
    Stage stage;

    @FXML
    public void openAddPartView(ActionEvent event) throws IOException {
        String view = "addpart-view.fxml";
        String title = "Add Part";

        openNewView(event, view, title);
    }

    @FXML
    public void openAddProductView(ActionEvent event) throws  IOException {
        String view = "addproduct-view.fxml";
        String title = "Add Product";

        openNewView(event, view, title);
    }

    @FXML
    public void openModifyProductView(ActionEvent event) throws  IOException {
        String view = "modifyproduct-view.fxml";
        String title = "Modify Product";

        openNewView(event, view, title);
    }

    @FXML
    public void openModifyPartView(ActionEvent event) throws IOException {
        String view = "modifypart-view.fxml";
        String title = "Modify Part";

        openNewView(event, view, title);
    }

    // exit application method
    public void exitApplication(ActionEvent event) {
        stage = (Stage) mainPane.getScene().getWindow();
        System.out.println("Exit Application");
        stage.close();
    }
}