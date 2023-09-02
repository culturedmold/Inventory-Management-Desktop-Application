package com.example.software1_invmgmnt_cth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;

import java.io.IOException;

public class ModifyProductController {
    @FXML
    private Button cancelButton;

    @FXML
    public void cancelModifyProduct(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Inventory Manager");
        stage.setResizable(false);
        stage.setScene(scene);
    }
}
