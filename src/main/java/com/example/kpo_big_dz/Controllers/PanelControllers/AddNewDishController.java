package com.example.kpo_big_dz.Controllers.PanelControllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static com.example.kpo_big_dz.Services.ButtonServices.shakeButton;
import static com.example.kpo_big_dz.Services.TextFieldServices.flashTextField;
import static com.example.kpo_big_dz.Services.StringServices.parseIntOrNull;
import static com.example.kpo_big_dz.DataBase.SQLite.addDish;
import static com.example.kpo_big_dz.DataBase.SQLite.*;

public class AddNewDishController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Label dishExistsLabel;

    @FXML
    private TextField dishNameField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField difficultyField;

    @FXML
    private Button addNewDishButton;

    @FXML
    private Button refreshButton;

    @FXML
    public void addNewDish(ActionEvent e) {
        boolean isTheresProblem = false;
        String name = dishNameField.getText();
        Integer quantity = parseIntOrNull(quantityField.getText()),
                price =  parseIntOrNull(priceField.getText()),
                difficulty =  parseIntOrNull(difficultyField.getText());
        if (quantity == null || quantity <= 0) {
            flashTextField(quantityField);
            isTheresProblem = true;
        }
        if (price == null || price <= 0) {
            flashTextField(priceField);
            isTheresProblem = true;
        }
        if (difficulty == null || difficulty < 0) {
            flashTextField(difficultyField);
            isTheresProblem = true;
        }

        if (dishNameField.getText().isEmpty()) {
            flashTextField(dishNameField);
            isTheresProblem = true;
        } else if (isDishInDB(name)) {
            isTheresProblem = true;
            dishExistsLabel.setVisible(true);
            flashTextField(dishNameField);
        }

        if (!isTheresProblem) {
            addDish(name, price, quantity, difficulty);
            shakeButton(addNewDishButton);
            dishExistsLabel.setVisible(false);
        } else {
            shakeButton(addNewDishButton);
        }
    }

    @FXML
    public void refreshFields(ActionEvent e) {
        difficultyField.setText("");
        dishNameField.setText("");
        priceField.setText("");
        quantityField.setText("");
    }

    @FXML
    void initialize() {
        dishNameField.textProperty().addListener(
                (observable, oldValue, newValue) -> dishExistsLabel.setVisible(false));
    }
}
