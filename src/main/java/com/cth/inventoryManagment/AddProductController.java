package com.cth.inventoryManagment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AddProductController extends Controller {
    @FXML
    private AnchorPane addProductAnchorPane;
    @FXML
    private Button cancelButton;

    @FXML
    public void cancelAddProduct(ActionEvent event) throws IOException {
        String returnView = "main-view.fxml";
        String returnTitle = "Inventory Manager";

        cancelButton(event, returnView, returnTitle);
    }

    public int autoIncrementID() {
        int newID = 0;
        for (Product product:Inventory.getAllProducts()) {
            if (product.getId() > newID) {
                newID = product.getId();
            }
        }
        return ++newID;
    }
}
