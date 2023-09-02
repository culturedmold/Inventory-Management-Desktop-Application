package com.example.software1_invmgmnt_cth;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 600);
        stage.setScene(scene);
        stage.setTitle("Inventory Manager");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}