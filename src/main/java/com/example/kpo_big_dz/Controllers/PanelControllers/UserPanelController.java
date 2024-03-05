package com.example.kpo_big_dz.Controllers.PanelControllers;

import java.net.URL;
import java.util.HashMap;
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
import javafx.scene.layout.GridPane;

import com.example.kpo_big_dz.Interfaces.*;
import static com.example.kpo_big_dz.DataBase.SQLite.*;
import javafx.util.Pair;

import static com.example.kpo_big_dz.Services.GridPanelServices.*;
import static com.example.kpo_big_dz.TempData.Observer.addMenuSubscriber;
import static com.example.kpo_big_dz.TempData.Observer.notifyAdminOrderListSubscribers;

public class UserPanelController implements IMenu, IUser, IUserOrders {

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
    private Button orderButton;

    @FXML
    private ImageView sadSmileImage;

    @FXML
    private Label cartEmptyLabel;

    @FXML
    private Group cartGroup;

    @FXML
    private ScrollPane userOrdersScrollPane;

    @FXML
    private GridPane userOrdersGridPane;

    @FXML
    void initialize() {
        addMenuSubscriber(this);
        buttonDishesCount.setVisible(false);
        loadToGridPaneDishes(dishesGridPane, false, buttonDishesCount, 2, this);
    }

    @FXML
    public void onMenuButtonClick(ActionEvent e) {
        mainLabel.setText("MENU");
        hideAllScrollPanes();
        menuScrollPane.setVisible(true);
    }

    @FXML
    public void onCartButtonClick(ActionEvent e) {
        mainLabel.setText("CART");
        hideAllScrollPanes();
        cartGroup.setVisible(true);
        if (currentDishesCount.isEmpty()) {
            cartScrollPane.setPadding(new Insets(5, 20, 20, 20));
            sadSmileImage.setVisible(true);
            cartEmptyLabel.setVisible(true);
            orderButton.setVisible(false);
        } else {
            sadSmileImage.setVisible(false);
            cartEmptyLabel.setVisible(false);
            cartScrollPane.setPadding(new Insets(5, 20, 30, 20));
            orderButton.setVisible(true);
        }
        loadToGridPaneCartDishes(cartGridPane, 1, currentDishesCount);
    }

    @FXML
    public void onOrderButtonClick(ActionEvent e) {
        cartGridPane.getChildren().clear();
        addNewOrder(getUserID(), currentDishesCount);

        currentDishesCount.clear();
        loadToGridPaneDishes(dishesGridPane, false, buttonDishesCount, 2, this);
        loadToGridPaneCartDishes(cartGridPane, 1, currentDishesCount);
        buttonDishesCount.setText("0");
        buttonDishesCount.setVisible(false);
        onCartButtonClick(e);
    }

    @FXML
    public void onUserOrdersListButtonClick(ActionEvent e) {
        mainLabel.setText("YOUR ORDERS");
        hideAllScrollPanes();
        userOrdersScrollPane.setVisible(true);
        loadToUserOrdersGridPane(userOrdersGridPane, getUserID(), 1);
    }

    // Название блюда: цена - количество
    public HashMap<String, Pair<Integer, Integer>> currentDishesCount = new HashMap<>();

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

    @Override
    public void updateUserOrderList(int userId) {
        if (getUserID() == userId) {
            loadToUserOrdersGridPane(userOrdersGridPane, getUserID(), 1);
        }
    }

    private void hideAllScrollPanes() {
        cartGroup.setVisible(false);
        menuScrollPane.setVisible(false);
        userOrdersScrollPane.setVisible(false);
    }

    public int getUserID() {
        return id;
    }

    public void setUserID(int id) {
        this.id = id;
    }
}
