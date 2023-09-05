package com.cth.inventoryManagment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartController extends Controller implements Initializable {
    // Selected part to modify
    private static Part partToModify;

    @FXML
    private AnchorPane modifyPartAnchorPane;
    @FXML
    private RadioButton inHouseButton;
    @FXML
    private RadioButton outsourcedButton;
    @FXML
    private Label companyOrMachineIDLabel;
    @FXML
    private TextField companyOrMachineIDTextField;
    @FXML
    private Button cancelButton;

    @FXML
    public void setPartType(ActionEvent event) {
        if (inHouseButton.isSelected()) {
            companyOrMachineIDLabel.setText("Machine ID");
            companyOrMachineIDTextField.setPromptText("Machine ID");
        } else  {
            companyOrMachineIDLabel.setText("Company");
            companyOrMachineIDTextField.setPromptText("Company");
        }
    }
    @FXML
    public void cancelModifyPart(ActionEvent event) throws IOException {
        returnToMain(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get the selected part to modify
        partToModify = MainController.getSelectedPart();
    }
}
