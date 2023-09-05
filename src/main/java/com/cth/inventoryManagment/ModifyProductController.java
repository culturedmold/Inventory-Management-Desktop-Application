package com.cth.inventoryManagment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyProductController extends Controller implements Initializable {
    private static Product selectedProduct;
    private static int selectedProductIndex;

    // Anchor pane for modify product view
    @FXML
    private AnchorPane modifyProductAnchorPane;

    // Field values to display existing product data for modification
    @FXML
    private TextField prodNameField;
    @FXML
    private TextField prodInvLevelField;
    @FXML
    private TextField prodCostField;
    @FXML
    private TextField prodMaxField;
    @FXML
    private TextField prodMinField;

    // All parts table
    @FXML
    private TableView<Part> allPartsTable;
    @FXML
    private TableColumn<Part, Integer> colPartID1;
    @FXML
    private TableColumn<Part, String> colPartName1;
    @FXML
    private TableColumn<Part, Integer> colInvLevel1;
    @FXML
    private TableColumn<Part, Double> colPartCost1;

    // Associated parts table
    @FXML
    private TableView<Part> associatedPartsTable;
    @FXML
    private TableColumn<Part, Integer> colPartID2;
    @FXML
    private TableColumn<Part, String> colPartName2;
    @FXML
    private TableColumn<Part, Integer> colInvLevel2;
    @FXML
    private TableColumn<Part, Double> colPartCost2;
    @FXML
    private Button cancelButton;

    // Cancel button, needs to alert user and return to main view
    @FXML
    public void cancelModifyProduct(ActionEvent event) throws IOException {
        returnToMain(event);
    }

    // Runtime error: program was crashing when attempting to initialize modify product controller. This is because I
    // had neglected to account for the possibility that selectedProduct could be null. Altering the method to return in
    // the case the selectedProduct was null solved this issue.

    // Initialize the controller
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get product to modify
        selectedProduct = MainController.getSelectedProduct();
        // Get index of product to modify
        selectedProductIndex = MainController.getSelectedProductIndex();

        // Initialize allParts table
        allPartsTable.setItems(Inventory.getAllParts());
        // Set column values for allParts table
        colPartID1.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName1.setCellValueFactory(new PropertyValueFactory<>("name"));
        colInvLevel1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPartCost1.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Initialize associatedParts table
        associatedPartsTable.setItems(selectedProduct.getAllAssociatedParts());
        // Set column values for associatedParts table
        colPartID2.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName2.setCellValueFactory(new PropertyValueFactory<>("name"));
        colInvLevel2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPartCost2.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Set values for modifiable fields
        prodNameField.setText(selectedProduct.getName());
        prodInvLevelField.setText(Integer.toString(selectedProduct.getStock()));
        prodCostField.setText(Double.toString(selectedProduct.getPrice()));
        prodMaxField.setText(Integer.toString(selectedProduct.getMax()));
        prodMinField.setText(Integer.toString(selectedProduct.getMin()));
    }
}
