module com.example.software1_invmgmnt_cth {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.software1_invmgmnt_cth to javafx.fxml;
    exports com.example.software1_invmgmnt_cth;
//    exports Inventory to javafx.base;
}