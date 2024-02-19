module com.example.kpo_big_dz {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.kpo_big_dz to javafx.fxml;
    exports com.example.kpo_big_dz;
}