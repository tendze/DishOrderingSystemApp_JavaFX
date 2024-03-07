module com.example.kpo_big_dz {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires sqlite.jdbc;
    requires org.controlsfx.controls;

    opens com.example.kpo_big_dz to javafx.fxml;
    exports com.example.kpo_big_dz;
    exports com.example.kpo_big_dz.Controllers.AuthControllers;
    opens com.example.kpo_big_dz.Controllers.AuthControllers to javafx.fxml;

    exports com.example.kpo_big_dz.Controllers.PanelControllers;
    opens com.example.kpo_big_dz.Controllers.PanelControllers to javafx.fxml;

}