package com.example.kpo_big_dz.Controllers.PanelControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import static com.example.kpo_big_dz.Services.TextAreaServices.flashTextArea;
import static com.example.kpo_big_dz.Services.ButtonServices.shakeButton;
import static com.example.kpo_big_dz.DataBase.SQLite.addNewFeedback;

public class FeedbackPanelController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label askLabel;

    @FXML
    private Label alertLabel;

    @FXML
    private TextArea feedbackTextArea;

    @FXML
    private Button sendButton;

    @FXML
    private Rating starsRating;

    private int userId;
    private int orderId;

    @FXML
    public void onSendButtonClick(ActionEvent e) {
        if (feedbackTextArea.getText().isEmpty()) {
            flashTextArea(feedbackTextArea);
            shakeButton(sendButton);
            alertLabel.setText("Please fill in the text area below");
            alertLabel.setVisible(true);
        }
        else {
            addNewFeedback(userId, orderId, feedbackTextArea.getText(), starsRating.getRating());
            ((Stage)askLabel.getScene().getWindow()).close();
        }
    }

    @FXML
    void initialize() {
        feedbackTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            alertLabel.setVisible(false);
        });
    }

    public void setUserIdOrderId(int userId, int orderId) {
        this.userId = userId;
        this.orderId = orderId;
    }
}
