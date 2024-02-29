package com.example.kpo_big_dz.Controllers.AuthControllers;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.kpo_big_dz.Controllers.PanelControllers.AdminPanelController;
import com.example.kpo_big_dz.Controllers.PanelControllers.UserPanelController;
import com.example.kpo_big_dz.Interfaces.IUser;
import com.example.kpo_big_dz.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import static com.example.kpo_big_dz.Services.ButtonServices.shakeButton;
import static com.example.kpo_big_dz.Services.TextFieldServices.flashTextField;
import static com.example.kpo_big_dz.Services.WindowServices.*;
import static com.example.kpo_big_dz.DataBase.SQLite.*;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signUpButton;

    @FXML
    private PasswordField signUpPasswordField;

    @FXML
    private PasswordField signUpRepeatPasswordField;

    @FXML
    private Button goBackButton;

    @FXML
    private CheckBox isAdminCheckBox;

    private String loginInput;

    @FXML
    void initialize() {

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
    public void signUpToProgram(ActionEvent e) {
        boolean isAllOk = true;
        if (signUpPasswordField.getText().isEmpty()) {
            shakeButton(signUpButton);
            flashTextField(signUpPasswordField);
            isAllOk = false;
        }
        if (signUpRepeatPasswordField.getText().isEmpty()) {
            shakeButton(signUpButton);
            flashTextField(signUpRepeatPasswordField);
            isAllOk = false;
        }
        if (!isAllOk) { return; }

        if (!Objects.equals(signUpRepeatPasswordField.getText(), signUpPasswordField.getText())) {
            shakeButton(signUpButton);
            flashTextField(signUpRepeatPasswordField);
            flashTextField(signUpPasswordField);
        } else {
            addUser(loginInput, signUpPasswordField.getText(), isAdminCheckBox.isSelected());
            switchToAuthScene(e);
            try {
                boolean isCheckBoxSelected = isAdminCheckBox.isSelected();
                String fileUrl = isCheckBoxSelected ? "admin/admin_panel.fxml" : "user/user_panel.fxml";
                String windowTitle = isCheckBoxSelected ? "Admin Panel" : "Durger King";
                IUser window = openNewWindow(fileUrl, windowTitle);
                int userID = getUserIdByLogin(loginInput);
                window.setUserID(userID);
            } catch (IOException exception) {
                System.out.println("singUpToProgram exception: " + exception.getMessage());
            }

        }

    }

    public void setLogIn(String login) {
        this.loginInput = login;
    }
}
