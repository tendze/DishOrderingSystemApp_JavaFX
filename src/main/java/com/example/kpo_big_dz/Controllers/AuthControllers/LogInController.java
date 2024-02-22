package com.example.kpo_big_dz.Controllers.AuthControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.kpo_big_dz.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import static com.example.kpo_big_dz.Services.ButtonServices.shakeButton;
import static com.example.kpo_big_dz.Services.TextFieldServices.flashTextField;
import static com.example.kpo_big_dz.Services.WindowServices.*;
import static com.example.kpo_big_dz.DataBase.SQLite.*;

public class LogInController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button logInButton;

    @FXML
    private PasswordField logInPasswordField;

    @FXML
    private Button goBackButton;

    @FXML
    private Label incorrectPassLabel;

    private String loginInput;

    @FXML
    void initialize() {
        logInPasswordField.textProperty().addListener(
                (observable, oldValue, newValue) -> incorrectPassLabel.setVisible(false));
    }

    @FXML
    public void switchToAuthScene(ActionEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("auth/auth.fxml"));
            MainSceneControl.stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            MainSceneControl.scene = new Scene(root);
            MainSceneControl.stage.setScene(MainSceneControl.scene);
            MainSceneControl.stage.show();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @FXML
    public void loginToProgram(ActionEvent e) {
        if (logInPasswordField.getText().isEmpty()) {
            shakeButton(logInButton);
            flashTextField(logInPasswordField);
            shakeButton(logInButton);
        } else if (!isUserInDB(loginInput, logInPasswordField.getText())) {
            incorrectPassLabel.setVisible(true);
            shakeButton(logInButton);
        } else {
            switchToAuthScene(e);
            try {
                boolean isUserAdmin = isUserAdmin(loginInput);
                String fileUrl = isUserAdmin ? "admin/admin_panel.fxml" : "user_panel.fxml";
                String windowTitle = isUserAdmin ? "Admin panel" : "Durger King";
                Stage window = openNewWindow(fileUrl, windowTitle);
            } catch (IOException exception) {
                System.out.println("loginToProgram exception: " + exception.getMessage());
            }

        }
    }

    public void setLogIn(String login) {
        this.loginInput = login;
    }
}
