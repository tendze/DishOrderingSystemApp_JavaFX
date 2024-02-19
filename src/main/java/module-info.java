module com.example.kpo_big_dz {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires org.slf4j;

    opens com.example.kpo_big_dz to javafx.fxml;
    exports com.example.kpo_big_dz;
    exports com.example.kpo_big_dz.Controllers.AuthControllers;
    opens com.example.kpo_big_dz.Controllers.AuthControllers to javafx.fxml;
}