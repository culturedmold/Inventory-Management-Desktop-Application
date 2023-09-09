package com.cth.inventoryManagment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class for add product view. Contains methods and properties required for adding a new product to inventory.
 * @author Tyler Hampton (Cory)
 */
public class AddProductController extends Controller implements Initializable {
    /**
     * All parts in inventory for the user to specify which will become associated parts with the new product
     */
    ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * Associated parts for the product to be added
     */
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Text field for searching parts in inventory
     */
    @FXML
    private TextField partsSearchField;

    /**
     * UI element
     */
    @FXML
    private TextField addProductIdField;
    /**
     * UI element
     */
    @FXML
    private TextField addProductNameField;
    /**
     * UI element
     */
    @FXML
    private TextField addProductInvLevelField;
    /**
     * UI element
     */
    @FXML
    private TextField addProductCostField;
    /**
     * UI element
     */
    @FXML
    private TextField addProductMaxField;
    /**
     * UI element
     */
    @FXML
    private TextField addProductMinField;

    /**
     * All parts table UI element
     */
    @FXML
    private TableView<Part> allPartsTable;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Part, Integer> colPartId1;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Part, String> colPartName1;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Part, Double> colPartCost1;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Part, Integer> colPartInvLevel1;

    /**
     * Associated parts table to hold parts associated with the new product
     */
    @FXML
    private TableView<Part> associatedPartsTable;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Part, Integer> colPartId2;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Part, String> colPartName2;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Part, Double> colPartCost2;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Part, Integer> colPartInvLevel2;

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
    // Save button to add product to Inventory.allProducts
    /**
     * UI element
     */
    @FXML
    private Button addProductSaveButton;

    /**
     * Method to cancel and return to main view
     * @param event - button click or other UI event
     * @throws IOException
     */
    @FXML
    public void cancelAddProduct(ActionEvent event) throws IOException {
        cancelAndReturn(event);
    }

    /**
     * FUTURE ENHANCEMENT: we don't need to send the user back to the main screen after saving a product. Keeping them in
     * the add product view would allow a user to continue adding more parts without having to go through multiple steps.
     *
     * Method to save the product to inventory with try/catch block and error handling as appropriate to ensure data is entered correctly.
     * @param event - button click or UI event
     * @throws IOException
     */
    @FXML
    public void saveAddProduct(ActionEvent event) throws IOException {
        try {
            int newProductId = Inventory.incrementProductId();
            String newProductName = addProductNameField.getText();
            int newProductInv = Integer.parseInt(addProductInvLevelField.getText());
            int newProductMax = Integer.parseInt(addProductMaxField.getText());
            int newProductMin = Integer.parseInt(addProductMinField.getText());
            double newProductCost = Double.parseDouble(addProductCostField.getText());

            if ((newProductInv > newProductMax) || (newProductMin >= newProductMax) || (newProductMin > newProductInv)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Check inventory level and max/min.\nInventory cannot be greater than max or less than min.\nMin cannot be greater than max.", ButtonType.CLOSE);
                alert.showAndWait();

            } else if (newProductName.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Product name cannot be empty", ButtonType.CLOSE);
                alert.showAndWait();

            } else {
                Product newProduct = new Product(newProductId, newProductName, newProductCost, newProductInv, newProductMax, newProductMin);
                Inventory.addProduct(newProduct);

                for (Part part : associatedParts) {
                    newProduct.addAssociatedPart(part);
                }

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Product added successfully!", ButtonType.OK);
                alert.showAndWait();

                returnToMain(event);

            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "There was an error trying to save your new product entry due to invalid values entered into text field(s).\nPlease try again.", ButtonType.CLOSE);
            alert.showAndWait();

            System.out.println("Invalid values entered.");
            System.out.println("Exception: " + e);
        }
    }

    /**
     * Method to add a part to the associate parts list of the new product
     * @param event - button click or UI event
     * @return - returns boolean: true if part is added to associated parts, false otherwise
     */
    @FXML
    public boolean addAssociatedPart(ActionEvent event) {
        Part partToAdd = allPartsTable.getSelectionModel().getSelectedItem();
        if (partToAdd == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part.", ButtonType.OK);
            alert.showAndWait();
            return false;
        }
        for (Part part:associatedParts) {
            if (part.getId() == partToAdd.getId()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "The selected part is already associated with this product.", ButtonType.OK);
                alert.showAndWait();
                return false;
            }
        }
        return associatedParts.add(partToAdd);
    }

    /**
     * Method to remove an associated part from the product to be added
     * @param event - button click or UI event
     * @return - returns boolean: true if part is removed, false otherwise
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
            associatedParts.remove(partToRemove);
        }
        return true;
    }

    @FXML
    public void searchParts(KeyEvent event) {
        String searchEntry = partsSearchField.getText();
        ObservableList<Part> results = FXCollections.observableArrayList();
        for (Part part:Inventory.getAllParts()) {
            if ((part.getName().contains(searchEntry)) || (Integer.toString(part.getId()).contains(searchEntry))) {
                results.add(part);
                allPartsTable.setItems(results);
            }
        }
        if (results.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No products found");
            alert.showAndWait();
            partsSearchField.clear();
        }
    }

    /**
     * Initializer for the controller
     * @param url - required parameter
     * @param resourceBundle - required parameter
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set all parts variable to display in our table
        allParts = Inventory.getAllParts();

        // Set the table
        allPartsTable.setItems(allParts);

        // Set table columns for all parts
        colPartId1.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName1.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPartInvLevel1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPartCost1.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Set associated parts table
        associatedPartsTable.setItems(associatedParts);

        // Set table columns for associated parts
        colPartId2.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName2.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPartInvLevel2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPartCost2.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
