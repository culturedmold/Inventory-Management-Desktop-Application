package com.cth.inventoryManagment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private static int selectedProductIndex;

    // Anchor pane for modify product view
    @FXML
    private AnchorPane modifyProductAnchorPane;

    // Field values to display existing product data for modification
    @FXML
    private TextField productIdField;
    @FXML
    private TextField productNameField;
    @FXML
    private TextField productInvLevelField;
    @FXML
    private TextField productCostField;
    @FXML
    private TextField productMaxField;
    @FXML
    private TextField productMinField;

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

    // Remove associated part from selectedProduct
    @FXML
    private Button removeAssociatedPartButton;
    // Button to save the changes
    @FXML
    private Button saveModifyProductButton;
    // Button will cancel modify product and return to main screen
    @FXML
    private Button cancelButton;

    // Method to save changes
    @FXML
    public void saveModifyProductChanges(ActionEvent event) throws IOException {

        int modifiedProductId = selectedProduct.getId();
        String modifiedProductName = productNameField.getText();
        int modifiedProductInv = Integer.parseInt(productInvLevelField.getText());
        int modifiedProductMax = Integer.parseInt(productMaxField.getText());
        int modifiedProductMin = Integer.parseInt(productMinField.getText());
        double modifiedProductCost = Double.parseDouble(productCostField.getText());

        Product modifiedProduct = new Product(modifiedProductId, modifiedProductName, modifiedProductCost, modifiedProductInv, modifiedProductMax, modifiedProductMin);

        for (Part associatedPart:associatedParts) {
            modifiedProduct.addAssociatedPart(associatedPart);
        }

        Inventory.updateProduct(modifiedProduct, selectedProductIndex);
        returnToMain(event);

    }

    @FXML
    public boolean removeAssociatedPart(ActionEvent event) {
        Part partToRemove = associatedPartsTable.getSelectionModel().getSelectedItem();
        if (partToRemove == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part.", ButtonType.OK);
            alert.showAndWait();
            return false;
        }
        return associatedParts.remove(partToRemove);
    }

    // Cancel function for cancel button, needs to alert user and return to main view
    @FXML
    public void cancelModifyProduct(ActionEvent event) throws IOException {
        returnToMain(event);
    }

    // RUNTIME ERROR: program was crashing when attempting to initialize modify product controller. This is because I
    // had neglected to account for the possibility that selectedProduct could be null. Altering the method to return in
    // the case the selectedProduct was null solved this issue, and I ultimately ended up implementing an alert to notify
    // the user of the problem.

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
        productIdField.setText(Integer.toString(selectedProduct.getId()));
        productNameField.setText(selectedProduct.getName());
        productInvLevelField.setText(Integer.toString(selectedProduct.getStock()));
        productCostField.setText(Double.toString(selectedProduct.getPrice()));
        productMaxField.setText(Integer.toString(selectedProduct.getMax()));
        productMinField.setText(Integer.toString(selectedProduct.getMin()));

        // Set associated parts to modify
        associatedParts = selectedProduct.getAllAssociatedParts();
    }
}
