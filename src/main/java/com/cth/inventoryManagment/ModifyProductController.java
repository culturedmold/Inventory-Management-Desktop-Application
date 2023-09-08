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

/**
 * Modify product controller contains logic, variables, and initialized UI elements for our modify product view.
 * @author Tyler Hampton (Cory)
 */
public class ModifyProductController extends Controller implements Initializable {
    /**
     * Selected product comes from MainController
     */
    private Product selectedProduct;
    /**
     * Parts associated with selectedProduct
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * Index of selected product that we will pass into Inventory.updateProduct() method when "Save" button is pressed by the user
     */
    private static int selectedProductIndex;

    /**
     * UI element
     */
    @FXML
    private AnchorPane modifyProductAnchorPane;
    /**
     * UI element
     */
    @FXML
    private TextField productIdField;
    /**
     * UI element
     */
    @FXML
    private TextField productNameField;
    /**
     * UI element
     */
    @FXML
    private TextField productInvLevelField;
    /**
     * UI element
     */
    @FXML
    private TextField productCostField;
    /**
     * UI element
     */
    @FXML
    private TextField productMaxField;
    /**
     * UI element
     */
    @FXML
    private TextField productMinField;

    /** All parts in inventory table
     *
     */
    @FXML
    private TableView<Part> allPartsTable;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Part, Integer> colPartID1;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Part, String> colPartName1;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Part, Integer> colInvLevel1;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Part, Double> colPartCost1;

    /**
     * Associated parts table
     */
    @FXML
    private TableView<Part> associatedPartsTable;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Part, Integer> colPartID2;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Part, String> colPartName2;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Part, Integer> colInvLevel2;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Part, Double> colPartCost2;

    /**
     * UI element
     */
    @FXML
    private Button removeAssociatedPartButton;
    /**
     * UI element
     */
    @FXML
    private Button saveModifyProductButton;
    /**
     * UI element
     */
    @FXML
    private Button addAssociatedPartButton;
    /**
     * UI element
     */
    @FXML
    private Button cancelButton;

    /**
     * Method to save changes. The try/catch block will prevent invalid data from being entered into fields and logic
     * implemented will keep min/max/inv levels appropriate.
     *
     * The product modifications will only be saved if the user confirms the changes via an alert/dialog box.
     *
     * LOGICAL ERROR: Initially I was altering the static variable we were getting as the selectedProduct from the MainController when this controller was initialized.
     * This caused an issue where the changes were being saved even if the user did not confirm their changes. To correct this, I am
     * using a local variable in this method to create a new instance of the Product class, and then passing it into the Inventory.updateProduct() method
     * to change the index of the selectedProduct. This prevents the changes from being applied to the product in inventory
     * in the even the user does not click "Save."
     *
     * @param event - button click or other UI event
     * @throws IOException
     */
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

            if ((modifiedProductInv > modifiedProductMax) || (modifiedProductMin >= modifiedProductMax) || (modifiedProductMin > modifiedProductInv)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Check inventory level and max/min.\nInventory cannot be greater than max or less than min.\nMin cannot be greater than max.\n", ButtonType.CLOSE);
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
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your changes have been saved.", ButtonType.CLOSE);
                alert.showAndWait();
                returnToMain(event);
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "There was an error trying to save your modifications due to invalid values entered into text field(s).\nPlease try again.", ButtonType.CLOSE);
            alert.showAndWait();

            System.out.println("Invalid values entered.");
            System.out.println("Exception: " + e);
        }
    }

    /**
     * Remove an associated part
     * @param event - button click or UI event
     * @return - returns boolean: true if successfully removed, false otherwise
     */
    @FXML
    public boolean removeAssociatedPart(ActionEvent event) {
        Part partToRemove = associatedPartsTable.getSelectionModel().getSelectedItem();
        if (partToRemove == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part.", ButtonType.OK);
            alert.showAndWait();
            return false;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this associated part?");
        Optional<ButtonType> confirm = alert.showAndWait();
        if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
            return associatedParts.remove(partToRemove);
        }
        return true;
    }

    /**
     * Add an associated part
     *
     * @param event - button click or UI event
     * @return - returns true if part is successfully added, false otherwise
     */
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

    /**
     * Initialize the controller
     *
     * RUNTIME ERROR: program was crashing when attempting to initialize modify product controller. This is because I
     * had neglected to account for the possibility that selectedProduct could be null.
     * Altering the method to return it the case the selectedProduct was null solved this issue, and I ultimately ended up implementing an alert to notify
     * the user of the problem.
     *
     * @param url - required parameter for initializer
     * @param resourceBundle - required parameter for initializer
     */
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
        associatedParts.addAll(selectedProduct.getAllAssociatedParts());

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
