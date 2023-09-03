package com.example.software1_invmgmnt_cth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;

import java.io.IOException;

public class AddPartController extends Controller {
    @FXML
    private AnchorPane addPartAnchorPane;
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

    Stage stage;

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

    public void cancelAddPart(ActionEvent event) throws IOException {
        String returnView = "main-view.fxml";
        String returnTitle = "Inventory Manager";

        cancelButton(event, returnView, returnTitle);
    }

}
