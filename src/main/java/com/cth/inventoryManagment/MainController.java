package com.cth.inventoryManagment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController extends Controller implements Initializable {
    // Main anchor pane of our view
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

    // Stage for this view
    Stage stage;

    // Product to modify/delete/etc.
    private static Product selectedProduct;
    // index of product to modify/delete/etc.
    private static int selectedProductIndex;
    // Part to modify/delete/etc.
    private static Part selectedPart;
    // Index of part to modify/delete/etc.
    private static int selectedPartIndex;

    // Get product to modify/delete/etc.
    public static Product getSelectedProduct() {
        return selectedProduct;
    }
    // Get index of product to modify/delete/etc.
    public static int getSelectedProductIndex() {
        return selectedProductIndex;
    }
    // Get part to modify/delete/etc.
    public static Part getSelectedPart() {
        return selectedPart;
    }
    // Get index of part to modify/delete/etc.
    public static int getSelectedPartIndex() {
        return selectedPartIndex;
    };

    // LOGIC ERROR: initializing the mainViewProductsTable with the sample data led to all rows being duplicate.
    // This was fairly difficult to resolve as everything appeared to be identical between the way the addPart and
    // addProduct methods were implemented, but only one was not working. After careful examination of the code, I had
    // inadvertently marked all properties of the Product class with the "static" keyword, which was causing the issue
    // by not allowing each instance of the class to have unique property values.
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

    // Method to delete selectedPart
    @FXML
    public void deleteSelectedPart(ActionEvent event) {
        selectedPart = mainViewPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No part selected.", ButtonType.CLOSE);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?\nThis action cannot be undone.");
            Optional<ButtonType> confirm = alert.showAndWait();
            if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        }
    }
    // Method to delete selectedProduct
    @FXML
    public void deleteSelectedProduct(ActionEvent event) {
        selectedProduct = mainViewProductsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No product selected.", ButtonType.CLOSE);
            alert.showAndWait();
        } else if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot delete product with associated parts.\nPlease remove associated parts and try again.", ButtonType.OK);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?\nThis action cannot be undone.");
            Optional<ButtonType> confirm = alert.showAndWait();
            if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                Inventory.deleteProduct(selectedProduct);
            }
        }
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
        selectedProduct = mainViewProductsTable.getSelectionModel().getSelectedItem();
        selectedProductIndex = mainViewProductsTable.getSelectionModel().getSelectedIndex();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product to modify, and try again.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
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