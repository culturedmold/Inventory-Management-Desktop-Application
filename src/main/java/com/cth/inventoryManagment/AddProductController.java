package com.cth.inventoryManagment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;

public class AddProductController extends Controller {
    // Stage for this view
    Stage stage;

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

    // Anchor pane for this view
    @FXML
    private AnchorPane addProductAnchorPane;

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

            if (newProductMax < newProductMin || newProductInv > newProductMax) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Check inventory level and max/min.\nInventory cannot be greater than max.\nMin cannot be greater than max.", ButtonType.CLOSE);
                alert.showAndWait();
            } else if (newProductName.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Product name cannot be empty", ButtonType.CLOSE);
                alert.showAndWait();
            } else {
                Inventory.addProduct(new Product(newProductId, newProductName, newProductCost, newProductInv, newProductMax, newProductMin));
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Product added successfully!", ButtonType.OK);
                alert.showAndWait();

//                productNameField.clear();
//                productInvLevelField.clear();
//                productMaxField.clear();
//                productCostField.clear();
//                productMinField.clear();
                returnToMain(event);
            }
    }
}
