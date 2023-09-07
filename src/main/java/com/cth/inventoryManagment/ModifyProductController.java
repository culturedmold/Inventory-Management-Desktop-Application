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
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductController extends Controller implements Initializable {
    private Product selectedProduct;
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
    // Button to add an associated part
    @FXML
    private Button addAssociatedPartButton;
    @FXML
    private Button cancelButton;

    // Method to save changes
    @FXML
    public void saveModifyProductChanges(ActionEvent event) throws IOException {
        // Try/catch block will prevent issues from occurring if the user enters invalid form data
        try {
            int modifiedProductId = selectedProduct.getId();
            String modifiedProductName = productNameField.getText();
            int modifiedProductInv = Integer.parseInt(productInvLevelField.getText());
            int modifiedProductMax = Integer.parseInt(productMaxField.getText());
            int modifiedProductMin = Integer.parseInt(productMinField.getText());
            double modifiedProductCost = Double.parseDouble(productCostField.getText());

            if ((modifiedProductInv > modifiedProductMax) || (modifiedProductMin >= modifiedProductMax)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Check inventory level and max/min.\nInventory cannot be greater than max.\nMin cannot be greater than max.\n", ButtonType.CLOSE);
                alert.showAndWait();
            } else if (modifiedProductName.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Product name cannot be empty", ButtonType.CLOSE);
                alert.showAndWait();
            } else {
                Product modifiedProduct = new Product(modifiedProductId, modifiedProductName, modifiedProductCost, modifiedProductInv, modifiedProductMin, modifiedProductMax);

                for (Part associatedPart : associatedParts) {
                    modifiedProduct.addAssociatedPart(associatedPart);
                }

                Inventory.updateProduct(modifiedProduct, selectedProductIndex);
                returnToMain(event);
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "There was an error trying to save your modifications due to invalid values entered into text field(s).\nPlease try again.", ButtonType.CLOSE);
            alert.showAndWait();
            System.out.println("Invalid values entered.");
            System.out.println("Exception: " + e);
        }
    }

    @FXML
    public void removeAssociatedPart(ActionEvent event) {
        Part partToRemove = associatedPartsTable.getSelectionModel().getSelectedItem();
        if (partToRemove == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part.", ButtonType.OK);
            alert.showAndWait();
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this associated part?");
        Optional<ButtonType> confirm = alert.showAndWait();
        if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
            associatedParts.remove(partToRemove);
        }
    }

    @FXML
    public boolean addAssociatedPart(ActionEvent event) {
        Part partToAdd = allPartsTable.getSelectionModel().getSelectedItem();
        if (partToAdd == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part.", ButtonType.OK);
            alert.showAndWait();
            return false;
        }
        for (Part part : associatedParts) {
            if (part.getId() == partToAdd.getId()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "The selected part is already associated with this product.", ButtonType.OK);
                alert.showAndWait();
                return false;
            }
        }
        return associatedParts.add(partToAdd);
    }



    // Cancel function for cancel button, needs to alert user and return to main view
    @FXML
    public void cancelModifyProduct(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel? Your changes will not be saved.");
        Optional<ButtonType> confirm = alert.showAndWait();
        if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
            returnToMain(event);
        }
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

        // Set associated parts to modify
        for (Part part:selectedProduct.getAllAssociatedParts()) {
            associatedParts.add(part);
        }

        // Initialize associatedParts table
        associatedPartsTable.setItems(associatedParts);
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
    }
}
