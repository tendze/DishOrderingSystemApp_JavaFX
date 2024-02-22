package com.example.kpo_big_dz.Controllers.PanelControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import com.example.kpo_big_dz.Models.Dish;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class DishController {
    @FXML
    private AnchorPane anchorPale;

    @FXML
    private VBox infoVBox;

    @FXML
    private Label dishNameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label amoutLabel;

    @FXML
    private Label difficultyLabel;

    @FXML
    private Button deleteDishButton;

    @FXML
    private Button minusButton;

    @FXML
    private Button plusButton;

    @FXML
    private TextField quantityField;

    @FXML
    private Button orderButton;

    @FXML
    public void deleteDishRecord(ActionEvent e) {
    }

    private int rowIndex;
    private int columnIndex;
    private int dishID;

    public void setData(Dish dish) {
        this.dishID = dish.getDishID();
        dishNameLabel.setText(dish.getName());
        priceLabel.setText(String.valueOf(dish.getPrice()) + "$");
        amoutLabel.setText("Remains: " + String.valueOf(dish.getAmout()) + "pcs.");
        difficultyLabel.setText("Cooking time: " + dish.getCookingTimeSecs() + "s.");
    }

    public void setRowAndColumnIndex(int row, int column) {
        rowIndex = row;
        columnIndex = column;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int getDishID() {
        return dishID;
    }

    public Button getDeleteDishButton() {
        return deleteDishButton;
    }

    // Убирает кнопки заказать для панельки админов
    public void setAdminDishView() {
        minusButton.setVisible(false);
        plusButton.setVisible(false);
        quantityField.setVisible(false);
        orderButton.setVisible(false);
        infoVBox.setPrefHeight(211);
        anchorPale.setPrefHeight(211);
    }
}
