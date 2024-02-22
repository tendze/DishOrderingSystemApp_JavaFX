package com.example.kpo_big_dz.Services;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class TextFieldServices {
    public static void flashTextField(TextField textField) {
        String originalBorderColor = textField.getStyle();
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(textField.styleProperty(), "-fx-border-color: red")),
                new KeyFrame(Duration.seconds(0.1), new KeyValue(textField.styleProperty(), originalBorderColor))
        );
        timeline.play();
    }

}
