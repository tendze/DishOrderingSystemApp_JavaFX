package com.example.kpo_big_dz.Services;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class ButtonServices {
    public static void shakeButton(Button button) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(button.translateXProperty(), 0)),
                new KeyFrame(Duration.seconds(0.1), new KeyValue(button.translateXProperty(), -3)),
                new KeyFrame(Duration.seconds(0.2), new KeyValue(button.translateXProperty(), 3)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(button.translateXProperty(), -2)),
                new KeyFrame(Duration.seconds(0.4), new KeyValue(button.translateXProperty(), 2)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(button.translateXProperty(), -1)),
                new KeyFrame(Duration.seconds(0.6), new KeyValue(button.translateXProperty(), 1)),
                new KeyFrame(Duration.seconds(0.7), new KeyValue(button.translateXProperty(), -0.5)),
                new KeyFrame(Duration.seconds(0.8), new KeyValue(button.translateXProperty(), 0.5)),
                new KeyFrame(Duration.seconds(0.9), new KeyValue(button.translateXProperty(), -0.2)),
                new KeyFrame(Duration.seconds(1), new KeyValue(button.translateXProperty(), 0))
        );
        timeline.play();
    }
}
