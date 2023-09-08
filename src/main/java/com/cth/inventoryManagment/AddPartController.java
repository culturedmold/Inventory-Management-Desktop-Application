package com.cth.inventoryManagment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Controller for add part view. Contains methods and properties required for adding a new part to inventory.
 *
 * @author Tyler Hampton (Cory)
 */
public class AddPartController extends Controller {
    /**
     * UI element
     */
    @FXML
    private AnchorPane addPartAnchorPane;
    /**
     * UI element
     */
    @FXML
    private RadioButton inHouseButton;
    /**
     * UI element
     */
    @FXML
    private RadioButton outsourcedButton;
    /**
     * UI element
     */
    @FXML
    private Label companyOrMachineIDLabel;
    /**
     * UI element
     */
    @FXML
    private TextField addPartIDField;
    /**
     * UI element
     */
    @FXML
    private TextField addPartNameField;
    /**
     * UI element
     */
    @FXML
    private TextField addPartInvField;
    /**
     * UI element
     */
    @FXML
    private TextField addPartCostField;
    /**
     * UI element
     */
    @FXML
    private TextField addPartMaxField;
    /**
     * UI element
     */
    @FXML
    private TextField addPartMinField;
    /**
     * UI element
     */
    @FXML
    private TextField companyOrMachineIDField;
    /**
     * UI element
     */
    @FXML
    private Button addPartCancelButton;

    /**
     * Method to set part type
     * This method will change the label and prompt text of the companyOrMachineIDField based on which radio button is selected
     *
     * @param event - button click or other UI event
     */
    @FXML
    public void setPartType(ActionEvent event) {
        if (inHouseButton.isSelected()) {
            companyOrMachineIDLabel.setText("Machine ID");
            companyOrMachineIDField.setPromptText("Machine ID");
        } else  {
            companyOrMachineIDLabel.setText("Company");
            companyOrMachineIDField.setPromptText("Company");
        }
    }

    /**
     * Method to save the new part to Inventory.allParts.
     * Method will be called when addPartSaveButton is clicked.
     * Method checks to ensure min/max and inventory levels are valid, and that the correct value types are entered in the text fields.
     * If incorrect values are entered, the user will receive the appropriate error message to help them correct the issue.
     *
     * @param event - button click or other UI event
     */
    @FXML
    public void saveAddPart(ActionEvent event) throws IOException {
        try {
            int newPartId = Inventory.incrementPartId();
            String newPartName = addPartNameField.getText();
            int newPartInv = Integer.parseInt(addPartInvField.getText());
            double newPartCost = Double.parseDouble(addPartCostField.getText());
            int newPartMax = Integer.parseInt(addPartMaxField.getText());
            int newPartMin = Integer.parseInt(addPartMinField.getText());

            if ((newPartMin >= newPartMax) || (newPartInv > newPartMax) || (newPartMin > newPartInv)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Check inventory level and max/min.\nInventory cannot be greater than max or less than min.\nMin cannot be greater than max.\n", ButtonType.CLOSE);
                alert.showAndWait();
                return;
            } else if (newPartName == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Part name cannot be empty.", ButtonType.CLOSE);
                alert.showAndWait();
                return;
            }

            if (inHouseButton.isSelected()) {
                int machineId = Integer.parseInt(companyOrMachineIDField.getText());
                InHouse newInHousePart = new InHouse(newPartId, newPartName, newPartInv, newPartCost, newPartMin, newPartMax, machineId);

                Inventory.addPart(newInHousePart);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your changes have been saved.", ButtonType.CLOSE);
                alert.showAndWait();
                returnToMain(event);
            } else if (outsourcedButton.isSelected()) {
                String companyName = companyOrMachineIDField.getText();

                Outsourced newOutsourcedPart = new Outsourced(newPartId, newPartName, newPartCost, newPartInv, newPartMin, newPartMax, companyName);

                Inventory.addPart(newOutsourcedPart);

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
     * Method to return a user to the main view when addPartCancelButton is clicked
     * This method will show an alert to the user and require confirmation before cancelling and returning to main view
     *
     * @param event - button click or other UI event
     * @throws IOException
     */
    public void cancelAddPart(ActionEvent event) throws IOException {
        cancelAndReturn(event);
    }
}
