package com.cth.inventoryManagment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ModifyPartController extends Controller {
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
        String returnView = "main-view.fxml";
        String returnTitle = "Inventory Management";

        cancelButton(event, returnView, returnTitle);
    }
}
