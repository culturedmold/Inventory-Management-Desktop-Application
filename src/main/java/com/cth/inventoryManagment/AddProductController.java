package com.cth.inventoryManagment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController extends Controller implements Initializable {
    ObservableList<Part> allParts = FXCollections.observableArrayList();

    // Fields to enter new product data
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

    // All parts table to display our inventory
    @FXML
    private TableView<Part> allPartsTable;
    @FXML
    private TableColumn<Part, Integer> colPartId1;
    @FXML
    private TableColumn<Part, String> colPartName1;
    @FXML
    private TableColumn<Part, Double> colPartCost1;
    @FXML
    private TableColumn<Part, Integer> colPartInvLevel1;

    // Cancel button will return to main view
    @FXML
    private Button cancelButton;
    // Save button to add product to Inventory.allProducts
    @FXML
    private Button addProductSaveButton;

    @FXML
    public void cancelAddProduct(ActionEvent event) throws IOException {
        returnToMain(event);
    }

    // FUTURE ENHANCEMENT: we don't need to send the user back to the main screen after saving a product. Keeping them in
    // the add product view would allow a user to continue adding more parts without having to go through multiple steps.
    @FXML
    public void saveAddProduct(ActionEvent event) throws IOException {
            int newProductId = Inventory.incrementProductId();
            String newProductName = productNameField.getText();
            int newProductInv = Integer.parseInt(productInvLevelField.getText());
            int newProductMax = Integer.parseInt(productMaxField.getText());
            int newProductMin = Integer.parseInt(productMinField.getText());
            double newProductCost = Double.parseDouble(productCostField.getText());

            if ((newProductInv > newProductMax) || (newProductMin >= newProductMax)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Check inventory level and max/min.\nInventory cannot be greater than max.\nMin cannot be greater than max.", ButtonType.CLOSE);
                alert.showAndWait();

            } else if (newProductName.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Product name cannot be empty", ButtonType.CLOSE);
                alert.showAndWait();

            } else {
                Inventory.addProduct(new Product(newProductId, newProductName, newProductCost, newProductInv, newProductMax, newProductMin));
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Product added successfully!", ButtonType.OK);
                alert.showAndWait();

                returnToMain(event);
            }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set all parts variable to display in our table
        allParts = Inventory.getAllParts();

        // Set the table
        allPartsTable.setItems(allParts);

        // Set table columns
        colPartId1.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName1.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPartInvLevel1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPartCost1.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
