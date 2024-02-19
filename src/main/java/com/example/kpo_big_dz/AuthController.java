package com.example.kpo_big_dz;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AuthController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button logIn;

    @FXML
    private TextField loginField;

    @FXML
    void initialize() {
        assert logIn != null : "fx:id=\"logIn\" was not injected: check your FXML file 'auth.fxml'.";
        assert loginField != null : "fx:id=\"loginField\" was not injected: check your FXML file 'auth.fxml'.";

    }
}
