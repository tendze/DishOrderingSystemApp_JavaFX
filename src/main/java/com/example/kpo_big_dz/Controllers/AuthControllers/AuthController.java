package com.example.kpo_big_dz.Controllers.AuthControllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;

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
        logIn.setOnAction(
                (actionEvent -> loginAction())
        );
    }

    @FXML
    private void loginAction() {
        shakeButton(logIn);
    }

    private void shakeButton(Button button) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(button.translateXProperty(), 0)),
                new KeyFrame(Duration.seconds(0.1), new KeyValue(button.translateXProperty(), -5)),
                new KeyFrame(Duration.seconds(0.2), new KeyValue(button.translateXProperty(), 5)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(button.translateXProperty(), -4)),
                new KeyFrame(Duration.seconds(0.4), new KeyValue(button.translateXProperty(), 4)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(button.translateXProperty(), -3)),
                new KeyFrame(Duration.seconds(0.6), new KeyValue(button.translateXProperty(), 3)),
                new KeyFrame(Duration.seconds(0.7), new KeyValue(button.translateXProperty(), -2)),
                new KeyFrame(Duration.seconds(0.8), new KeyValue(button.translateXProperty(), 2)),
                new KeyFrame(Duration.seconds(0.9), new KeyValue(button.translateXProperty(), -1)),
                new KeyFrame(Duration.seconds(1), new KeyValue(button.translateXProperty(), 0))
        );
        timeline.play();

    }
}
