module com.example.software1_invmgmnt_cth {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.cth.inventoryManagement to javafx.fxml;
    exports com.cth.inventoryManagement;
    exports com.cth.inventoryManagement.model;
    opens com.cth.inventoryManagement.model to javafx.fxml;
}