package com.example.kpo_big_dz.Controllers.AuthControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.kpo_big_dz.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.example.kpo_big_dz.DataBase.SQLite;

import static com.example.kpo_big_dz.Services.ButtonServices.shakeButton;

public class AuthController {
    public static String loginMemory = "";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button logInOrSignUpButton;

    @FXML
    private TextField authLogInField;
    @FXML
    void initialize() {
        authLogInField.setText(loginMemory);
    }

    @FXML
    public void switchToLogInSignUpScene(ActionEvent e) {
        try {
            String loginFieldText = authLogInField.getText();
            FXMLLoader fxmlLoader;
            Parent root;
            if (loginFieldText.isEmpty()) {
                shakeButton(logInOrSignUpButton);
                return;
            } else if (SQLite.isUserInDB(loginFieldText)) {
                fxmlLoader = new FXMLLoader(Main.class.getResource("auth/login.fxml"));
                root = fxmlLoader.load();
                LogInController logInController = fxmlLoader.getController();
                logInController.setLogIn(loginFieldText);
            } else {
                fxmlLoader = new FXMLLoader(Main.class.getResource("auth/signup.fxml"));
                root = fxmlLoader.load();
                SignUpController signUpController = fxmlLoader.getController();
                signUpController.setLogIn(loginFieldText);
            }
            loginMemory = loginFieldText;
            MainSceneControl.stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            MainSceneControl.scene = new Scene(root);
            MainSceneControl.stage.setScene(MainSceneControl.scene);
            MainSceneControl.stage.show();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
