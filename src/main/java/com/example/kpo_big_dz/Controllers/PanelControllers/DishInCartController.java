package com.example.kpo_big_dz.Controllers.PanelControllers;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.example.kpo_big_dz.Models.Dish;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Pair;

public class DishInCartController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label dishNameLabel;

    @FXML
    private Label amountLabel;

    @FXML
    private Label priceLabel;

    @FXML
    void initialize() {
    }

    public void setData(Dish dish) {
        dishNameLabel.setText(dish.getName());
        amountLabel.setText(String.valueOf(dish.getAmout()) + " pcs.");
        priceLabel.setText(String.valueOf(dish.getPrice()) + "$");
    }
}
