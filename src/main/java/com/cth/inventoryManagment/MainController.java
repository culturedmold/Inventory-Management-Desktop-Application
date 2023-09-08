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

/**
 * Controller for our main view. Contains methods and properties required to navigate to other views and delete parts or products.
 */
public class MainController extends Controller implements Initializable {
    /**
     * Main anchor pane for our view
     */
    @FXML
    private AnchorPane mainPane;

    /**
     * Parts table to display parts in inventory
     */
    @FXML
    private TableView<Part> mainViewPartsTable;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Part, Integer> colPartID;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Part, String> colPartName;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Part, Integer> colPartInvLevel;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Part, Double> colPartCost;

    /**
     * Table to hold all products in inventory
     */
    @FXML
    private TableView<Product> mainViewProductsTable;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Product, Integer> colProductID;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Product, String> colProductName;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Product, Integer> colProductInvLevel;
    /**
     * UI element
     */
    @FXML
    private TableColumn<Product, Double> colProductCost;

    /**
     * UI element
     */
    @FXML
    private Button exitButton;
    /**
     * UI element
     */
    @FXML
    private Button addPartButton;
    /**
     * UI element
     */
    @FXML
    private Button modifyPartButton;
    /**
     * UI element
     */
    @FXML
    private Button deletePartButton;
    /**
     * UI element
     */
    @FXML
    private Button addProductButton;
    /**
     * UI element
     */
    @FXML
    private Button modifyProductButton;
    /**
     * UI element
     */
    @FXML
    private Button deleteProductButton;

    /**
     * Stage for this view
     */
    Stage stage;

    /**
     * Product to delete/modify/etc.
     */
    private static Product selectedProduct;
    /**
     * Index of product to delete/modify/etc.
     */
    private static int selectedProductIndex;
    /**
     * Part to delete/modify/etc.
     */
    private static Part selectedPart;
    /**
     * Index of part to delete/modify/etc.
     */
    private static int selectedPartIndex;

    /**
     * Method to get the selected product
     * @return - returns the selected product
     */
    public static Product getSelectedProduct() {
        return selectedProduct;
    }

    /**
     * Method to get the index of the selected product
     * @return - returns index as integer
     */
    public static int getSelectedProductIndex() {
        return selectedProductIndex;
    }

    /**
     * Method to get the selected part
     * @return - returns the selected part
     */
    public static Part getSelectedPart() {
        return selectedPart;
    }

    /**
     * Method to get the index of the selected part
     * @return - returns index as integer
     */
    public static int getSelectedPartIndex() {
        return selectedPartIndex;
    }

    /**
     * Initialize the view
     *
     * LOGIC ERROR: initializing the mainViewProductsTable with the sample data led to all rows being duplicate.
     * This was fairly difficult to resolve as everything appeared to be identical between the way the addPart and
     * addProduct methods were implemented, but only one was not working. After careful examination of the code, I had
     * inadvertently marked all properties of the Product class with the "static" keyword, which was causing the issue
     * by not allowing each instance of the class to have unique property values.
     *
     * @param url - required parameter
     * @param resourceBundle - required parameter
     */
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

    /**
     * Method to delete the selected part
     * @param event - button click or UI event
     */
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
                boolean deletedResult = Inventory.deletePart(selectedPart);
                if (deletedResult) {
                    Alert finalAlert = new Alert(Alert.AlertType.INFORMATION, "Part has been deleted.");
                    finalAlert.showAndWait();
                } else {
                    Alert failedAlert = new Alert(Alert.AlertType.ERROR, "There was a problem trying to delete the selected part. Please try again.");
                    failedAlert.showAndWait();
                }
            }
        }
    }

    /**
     * Method to delete the selected product.
     * This method will display an error message if no product is selected or if the product has associated parts that have not been removed,.
     * User will be asked to confirm their choice to delete the product before the action is carried out.
     * @param event - button click or UI event
     */
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
                boolean deletedResult = Inventory.deleteProduct(selectedProduct);
                if (deletedResult) {
                    Alert finalAlert = new Alert(Alert.AlertType.INFORMATION, "Product has been deleted.");
                    finalAlert.showAndWait();
                } else {
                    Alert failedAlert = new Alert(Alert.AlertType.ERROR, "There was a problem trying to delete the selected product. Please try again.");
                    failedAlert.showAndWait();
                }
            }
        }
    }

    /**
     * Method to open add part view
     * @param event - button click or UI event
     * @throws IOException
     */
    @FXML
    public void openAddPartView(ActionEvent event) throws IOException {
        String view = "addpart-view.fxml";
        String title = "Add Part";

        openNewView(event, view, title);
    }

    /**
     * Method to open add product view
     * @param event - button click or UI event
     * @throws IOException
     */
    @FXML
    public void openAddProductView(ActionEvent event) throws  IOException {
        String view = "addproduct-view.fxml";
        String title = "Add Product";

        openNewView(event, view, title);
    }

    /**
     * Method to open modify product view
     * @param event - button click or UI event
     * @throws IOException
     */
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

    /**
     * Method to open the modify part view
     * @param event - button click or UI event
     * @throws IOException
     */
    @FXML
    public void openModifyPartView(ActionEvent event) throws IOException {
        selectedPart = mainViewPartsTable.getSelectionModel().getSelectedItem();
        selectedPartIndex = mainViewPartsTable.getSelectionModel().getSelectedIndex();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to modify, and try again.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        String view = "modifypart-view.fxml";
        String title = "Modify Part";

        openNewView(event, view, title);
    }

    /**
     * Function to exit the application
     * @param event - button click or UI event
     */
    public void exitApplication(ActionEvent event) {
        stage = (Stage) mainPane.getScene().getWindow();
        System.out.println("Exit Application");
        stage.close();
    }
}