package com.example.kpo_big_dz.Controllers.PanelControllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import static com.example.kpo_big_dz.Services.GridPanelServices.loadToGridPaneCartDishes;
import static com.example.kpo_big_dz.Services.GridPanelServices.loadToGridPaneDishes;
import com.example.kpo_big_dz.Interfaces.IMenu;
import com.example.kpo_big_dz.Interfaces.IUser;
import javafx.util.Pair;

import static com.example.kpo_big_dz.TempData.Observer.addSubscriber;

public class UserPanelController implements IMenu, IUser {

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

    private int id;

    @Override
    public void updateMenuList() {
        // Запоминаем какие у пользователи были блюда в корзине до обновления
        // Название - кол-во (типа String)
        var memory = new HashMap<String, String>();
        int totalDishes = 0;
        for (Node node : dishesGridPane.getChildren()) {
            TextField quantityField = (TextField)node.lookup("#quantityField");
            Label nameLabel = (Label)node.lookup("#dishNameLabel");
            if (!quantityField.getText().isEmpty()) {
                memory.put(nameLabel.getText(), quantityField.getText());
            }
        }
        loadToGridPaneDishes(dishesGridPane, false, buttonDishesCount, 2, this);
        currentDishesCount = new HashMap<>();
        for (Node node : dishesGridPane.getChildren()) {
            Label nameLabel = (Label)node.lookup("#dishNameLabel");
            if (memory.containsKey(nameLabel.getText())) {
                Group plusMinusGroup = (Group)node.lookup("#plusMinusGroup");
                Button orderButton = (Button)node.lookup("#orderButton");
                TextField quantityField = (TextField)node.lookup("#quantityField");
                Label priceLabel = (Label)node.lookup("#priceLabel");
                quantityField.setText(memory.get(nameLabel.getText()));
                plusMinusGroup.setVisible(true);
                orderButton.setVisible(false);
                totalDishes += Integer.parseInt(memory.get(nameLabel.getText()));
                currentDishesCount.put(nameLabel.getText(), new Pair<>(Integer.parseInt(priceLabel.getText().replace("$", "")),
                        Integer.parseInt(quantityField.getText().replace("pcs.", ""))));
            }
        }

        if (totalDishes != 0) {
            buttonDishesCount.setVisible(true);
            buttonDishesCount.setText(String.valueOf(totalDishes));
        } else {
            buttonDishesCount.setVisible(false);
            buttonDishesCount.setText("0");
        }
    }

    public int getUserID() {
        return id;
    }

    public void setUserID(int id) {
        this.id = id;
    }
}
