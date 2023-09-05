package com.cth.inventoryManagment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

        int productId = Inventory.incrementProductId();
        int partId = Inventory.incrementPartId();

        // Load sample products
        Product Stratocaster = new Product(productId, "Stratocaster", 1099.99, 10, 1, 15);
        Inventory.addProduct(Stratocaster);
        productId = Inventory.incrementProductId();

        Product Telecaster = new Product(productId, "Telecaster", 1299.99, 5, 1, 15);
        Inventory.addProduct(Telecaster);
        productId = Inventory.incrementProductId();

        Product LesPaul = new Product(productId, "Les Paul", 2499.00, 5, 1, 10);
        Inventory.addProduct(LesPaul);

        // Load sample parts
        Part NickelStrings = new Outsourced(partId, "Nickel Strings", 11.99, 500, 10, 1000, "Ernie Ball");
        Inventory.addPart(NickelStrings);
        partId = Inventory.incrementPartId();

        Part Pick = new InHouse(partId, "Jazz Style Pick", 500, 1.99, 50, 1000, 44);
        Inventory.addPart(Pick);

        launch();
    }
}