package com.cth.inventoryManagment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for the modify part view. Contains methods and properties necessary for modifying or updating a part in inventory.
 * @author Tyler Hampton (Cory)
 */
public class ModifyPartController extends Controller implements Initializable {
    /**
     * Selected part to modify
     */
    private Part selectedPart;
    /**
     * Index of selected part
     */
    private int selectedPartIndex;

    @FXML
    private AnchorPane modifyPartAnchorPane;
    @FXML
    private RadioButton inHouseButton;
    @FXML
    private RadioButton outsourcedButton;
    @FXML
    private TextField modifyPartIdField;
    @FXML
    private TextField modifyPartNameField;
    @FXML
    private TextField modifyPartInvField;
    @FXML
    private TextField modifyPartCostField;
    @FXML
    private TextField modifyPartMaxField;
    @FXML
    private TextField modifyPartMinField;
    @FXML
    private Label companyOrMachineIDLabel;
    @FXML
    private TextField companyOrMachineIDTextField;
    @FXML
    private Button modifyPartSaveButton;
    @FXML
    private Button modifyPartCancelButton;

    @FXML
    public void setPartType(ActionEvent event) {
        if (inHouseButton.isSelected()) {
            companyOrMachineIDLabel.setText("Machine ID");
            companyOrMachineIDTextField.setPromptText("Machine ID");
        } else if (outsourcedButton.isSelected()) {
            companyOrMachineIDLabel.setText("Company");
            companyOrMachineIDTextField.setPromptText("Company");
        }
    }

    /**
     * Method to cancel and return to main view
     * @param event - button click or UI event
     * @throws IOException
     */
    @FXML
    public void cancelModifyPart(ActionEvent event) throws IOException {
        cancelAndReturn(event);
    }

    /**
     * Initialize our controller
     *
     * @param url - required parameter for our initializer
     * @param resourceBundle - required parameter for our initializer
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
          Get the selected part to modify
         */
        selectedPart = MainController.getSelectedPart();
        /*
          Get the selected part index to improve ease of update the saved modify product in our Inventory list
         */
        selectedPartIndex = MainController.getSelectedPartIndex();
        /*
          Set the ID field to be the ID of our part to modify
          This field will not be editable by the user
         */
        modifyPartIdField.setText(String.valueOf(selectedPart.getId()));
        /*
         * Set the editable name text field
         */
        modifyPartNameField.setText(selectedPart.getName());
        /*
         * Set the editable inventory level text field
         */
        modifyPartInvField.setText(String.valueOf(selectedPart.getStock()));
        /*
         * Set the editable cost/price text field
         */
        modifyPartCostField.setText(String.valueOf(selectedPart.getPrice()));
        /*
         * Set the editable max text field
         */
        modifyPartMaxField.setText(String.valueOf(selectedPart.getMax()));
        /*
         * Set the editable min text field
         */
        modifyPartMinField.setText(String.valueOf(selectedPart.getMin()));

        if (selectedPart instanceof InHouse) {
            inHouseButton.setSelected(true);
            companyOrMachineIDLabel.setText("Machine ID");
            companyOrMachineIDTextField.setText(String.valueOf(((InHouse) selectedPart).getMachineID()));
        } else if (selectedPart instanceof Outsourced) {
            outsourcedButton.setSelected(true);
            companyOrMachineIDLabel.setText("Outsourced");
            companyOrMachineIDTextField.setText(((Outsourced) selectedPart).getCompanyName());
        }
    }

    /**
     * Method to save the part modifications
     * When save button is clicked, this method will be called and will replace the part at partToModifyIndex of Inventory.allParts
     *
     * @param event
     */
    @FXML
    public void saveModifyPart(ActionEvent event) throws IOException {
        try {
            int modifiedPartID = selectedPart.getId();
            String modifiedPartName = modifyPartNameField.getText();
            int modifiedPartInv = Integer.parseInt(modifyPartInvField.getText());
            double modifiedPartCost = Double.parseDouble(modifyPartCostField.getText());
            int modifiedPartMax = Integer.parseInt(modifyPartMaxField.getText());
            int modifiedPartMin = Integer.parseInt(modifyPartMinField.getText());

            if ((modifiedPartMin >= modifiedPartMax) || (modifiedPartInv > modifiedPartMax) || (modifiedPartMin > modifiedPartInv)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Check inventory level and max/min.\nInventory cannot be greater than max or less than min.\nMin cannot be greater than max.\n", ButtonType.CLOSE);
                alert.showAndWait();
                return;
            } else if (modifiedPartName == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Part name cannot be empty.", ButtonType.CLOSE);
                alert.showAndWait();
                return;
            }
            if (inHouseButton.isSelected()) {
                int machineID = Integer.parseInt(companyOrMachineIDTextField.getText());

                InHouse modifiedPart = new InHouse(modifiedPartID, modifiedPartName, modifiedPartInv, modifiedPartCost, modifiedPartMin, modifiedPartMax, machineID);
                Inventory.updatePart(selectedPartIndex, modifiedPart);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your changes have been saved.", ButtonType.CLOSE);
                alert.showAndWait();
                returnToMain(event);
            } else if (outsourcedButton.isSelected()) {
                String company = companyOrMachineIDTextField.getText();

                Outsourced modifiedPart = new Outsourced(modifiedPartID, modifiedPartName, modifiedPartCost, modifiedPartInv, modifiedPartMin, modifiedPartMax, company);
                Inventory.updatePart(selectedPartIndex, modifiedPart);

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
}
