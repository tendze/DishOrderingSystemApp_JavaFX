package com.example.kpo_big_dz.Services;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

public class TextAreaServices {
    public static void flashTextArea(TextArea textArea) {
        String originalBorderColor = textArea.getStyle();
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(textArea.styleProperty(), "-fx-border-color: red")),
                new KeyFrame(Duration.seconds(0.1), new KeyValue(textArea.styleProperty(), originalBorderColor))
        );
        timeline.play();
    }
}
