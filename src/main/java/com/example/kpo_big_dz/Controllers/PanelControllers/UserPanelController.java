package com.example.kpo_big_dz.Controllers.PanelControllers;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import static com.example.kpo_big_dz.Services.GridPanelServices.loadToGridPaneCartDishes;
import static com.example.kpo_big_dz.Services.GridPanelServices.loadToGridPaneDishes;
import com.example.kpo_big_dz.Interfaces.IWindow;
import javafx.util.Pair;

import static com.example.kpo_big_dz.TempData.Observer.addSubscriber;

public class UserPanelController implements IWindow {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane dishesGridPane;

    @FXML
    private Button cartButton;

    @FXML
    private Button userOrdersListButton;

    @FXML
    private Button buttonDishesCount;

    @FXML
    private Label mainLabel;

    @FXML
    private ScrollPane menuScrollPane;

    @FXML
    private Button menuButton;

    @FXML
    private GridPane cartGridPane;

    @FXML
    private ScrollPane cartScrollPane;

    @FXML
    private Button purchaseAndOrderButton;

    @FXML
    private ImageView sadSmileImage;

    @FXML
    private Label cartEmptyLabel;

    @FXML
    private Group cartGroup;

    @FXML
    void initialize() {
        addSubscriber(this);
        buttonDishesCount.setVisible(false);
        loadToGridPaneDishes(dishesGridPane, false, buttonDishesCount, 2, this);
    }

    @FXML
    public void onMenuButtonClick(ActionEvent e) {
        mainLabel.setText("MENU");
        cartGroup.setVisible(false);
        menuScrollPane.setVisible(true);
    }

    @FXML
    public void onCartButtonClick(ActionEvent e) {
        mainLabel.setText("CART");
        menuScrollPane.setVisible(false);
        cartGroup.setVisible(true);
        if (currentDishesCount.isEmpty()) {
            cartScrollPane.setPadding(new Insets(5, 20, 20, 20));
            sadSmileImage.setVisible(true);
            cartEmptyLabel.setVisible(true);
            purchaseAndOrderButton.setVisible(false);
        } else {
            sadSmileImage.setVisible(false);
            cartEmptyLabel.setVisible(false);
            cartScrollPane.setPadding(new Insets(5, 20, 30, 20));
            purchaseAndOrderButton.setVisible(true);
        }
        loadToGridPaneCartDishes(cartGridPane, 1, currentDishesCount);
    }

    // Название блюда: цена - количество
    public HashMap<String, Pair<Integer, Integer>> currentDishesCount = new HashMap<String, Pair<Integer, Integer>>();

    @Override
    public void updateMenuList() {
        loadToGridPaneDishes(dishesGridPane, false, buttonDishesCount, 2, this);
    }
}
