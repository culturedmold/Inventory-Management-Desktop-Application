package com.example.software1_invmgmnt_cth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ModifyPartController extends Controller {
    @FXML
    private AnchorPane modifyPartAnchorPane;
    @FXML
    private Button cancelButton;

    @FXML
    public void cancelModifyPart(ActionEvent event) throws IOException {
        String returnView = "main-view.fxml";
        String returnTitle = "Inventory Management";

        cancelButton(event, returnView, returnTitle);
    }
}
