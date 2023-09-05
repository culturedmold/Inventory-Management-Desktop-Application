package com.example.software1_invmgmnt_cth;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends Controller implements Initializable {

    @FXML
    private AnchorPane mainPane;

    // Parts table
    @FXML
    private TableView<Part> mainViewPartsTable;
    @FXML
    private TableColumn<Part, Integer> colPartID;
    @FXML
    private TableColumn<Part, String> colPartName;
    @FXML
    private TableColumn<Part, Integer> colPartInvLevel;
    @FXML
    private TableColumn<Part, Double> colPartCost;

    // Products table
    @FXML
    private TableView<Product> mainViewProductsTable;
    @FXML
    private TableColumn<Product, Integer> colProductID;
    @FXML
    private TableColumn<Product, String> colProductName;
    @FXML
    private TableColumn<Product, Integer> colProductInvLevel;
    @FXML
    private TableColumn<Product, Double> colProductCost;

    // View Buttons
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

    // Products table is being populated with the correct number of rows, but they are all duplicates. Not sure why.
    // It's because properties were marked "static".
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize parts table
        mainViewPartsTable.setItems(Inventory.getAllParts());
        // Set column values
        colPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPartInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPartCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Initialize products table
        mainViewProductsTable.setItems(Inventory.getAllProducts());
        // Set column values
        colProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProductInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colProductCost.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

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