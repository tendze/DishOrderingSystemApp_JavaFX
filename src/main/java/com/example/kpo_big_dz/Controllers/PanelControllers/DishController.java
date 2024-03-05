package com.example.kpo_big_dz.Controllers.PanelControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import com.example.kpo_big_dz.Models.Dish;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

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
    private Group plusMinusGroup;

    @FXML
    public void onOrderButtonClick(ActionEvent e) {
        plusMinusGroup.setVisible(true);
        orderButton.setVisible(false);
        quantityField.setText("1");

        dishesInCartCountButton.setVisible(true);
        int currentDishesInCartCount = Integer.parseInt(dishesInCartCountButton.getText());
        dishesInCartCountButton.setText(String.valueOf(currentDishesInCartCount + 1));
        currentUserPanel.currentDishesCount.put(dishNameLabel.getText(),
                new Pair<>(Integer.parseInt(priceLabel.getText().replace("$", "")), 1));
    }

    @FXML
    public void onPlusButtonClick(ActionEvent e) {
        int currentDishCount = Integer.parseInt(quantityField.getText());
        quantityField.setText(String.valueOf(currentDishCount + 1));
        int currentDishesInCartCount = Integer.parseInt(dishesInCartCountButton.getText());
        dishesInCartCountButton.setText(String.valueOf(currentDishesInCartCount + 1));
        currentUserPanel.currentDishesCount.put(dishNameLabel.getText(),
                new Pair<>(Integer.parseInt(priceLabel.getText().replace("$", "")), currentDishCount + 1));
    }

    @FXML
    public void onMinusButtonClick(ActionEvent e) {
        int currentQuantity = Integer.parseInt(String.valueOf(Integer.parseInt(quantityField.getText()) - 1));
        int currentDishesInCartCount = Integer.parseInt(dishesInCartCountButton.getText());

        if (currentDishesInCartCount == 1) {
            dishesInCartCountButton.setVisible(false);
            dishesInCartCountButton.setText("0");
        } else {
            dishesInCartCountButton.setText(String.valueOf(currentDishesInCartCount - 1));
        }

        if (currentQuantity == 0) {
            plusMinusGroup.setVisible(false);
            orderButton.setVisible(true);
            currentUserPanel.currentDishesCount.remove(dishNameLabel.getText());
        } else {
            quantityField.setText(String.valueOf(currentQuantity));
            currentUserPanel.currentDishesCount.put(dishNameLabel.getText(),
                    new Pair<>(Integer.parseInt(priceLabel.getText().replace("$", "")), currentQuantity));
        }
    }

    @FXML
    void initialize() {
        quantityField.setEditable(false);
    }

    private int dishID;
    private int dishQuantity;
    private Button dishesInCartCountButton;
    private UserPanelController currentUserPanel;

    public void setData(Dish dish) {
        this.dishID = dish.getDishID();
        dishNameLabel.setText(dish.getName());
        priceLabel.setText(dish.getPrice() + "$");
        amoutLabel.setText("Remains: " + dish.getAmout() + "pcs.");
        difficultyLabel.setText("Cooking time: " + dish.getCookingTimeSecs() + "s.");
        dishQuantity = dish.getAmout();
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

    public void setUserDishView() {
        deleteDishButton.setVisible(false);
    }
    public void setPlusMinusGroupButtonVisible(Boolean visible) {
        plusMinusGroup.setVisible(visible);
    }

    public void setDishesInCartCountButton(Button button) {
        this.dishesInCartCountButton = button;
    }

    public void setCurrentUserPanelController(UserPanelController panel) { this.currentUserPanel = panel; }
}
