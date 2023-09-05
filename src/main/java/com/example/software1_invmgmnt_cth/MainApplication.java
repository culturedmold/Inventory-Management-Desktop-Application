package com.example.software1_invmgmnt_cth;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Inventory Manager");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {

        Product Stratocaster = new Product(1, "Stratocaster", 1099.99, 10, 1, 15);
        Inventory.addProduct(Stratocaster);
        Product Telecaster = new Product(2, "Telecaster", 1299.99, 5, 1, 15);
        Inventory.addProduct(Telecaster);
        Product LesPaul = new Product(3, "Les Paul", 2499.00, 5, 1, 10);
        Inventory.addProduct(LesPaul);


        Part NickelStrings = new Outsourced(1, "Nickel Strings", 11.99, 500, 10, 1000, "Ernie Ball");
        Inventory.addPart(NickelStrings);
        Part Pick = new InHouse(2, "Jazz Style Pick", 500, 1.99, 50, 1000, 44);
        Inventory.addPart(Pick);

        launch();
    }
}