module com.example.software1_invmgmnt_cth {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cth.inventoryManagment to javafx.fxml;
    exports com.cth.inventoryManagment;
}