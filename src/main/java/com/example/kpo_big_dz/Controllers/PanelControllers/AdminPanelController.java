package com.example.kpo_big_dz.Controllers.PanelControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.kpo_big_dz.Interfaces.IAdminOrders;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;

import static com.example.kpo_big_dz.Services.ButtonServices.shakeButton;
import static com.example.kpo_big_dz.Services.GridPanelServices.loadToAdminUserOrdersGridPane;
import static com.example.kpo_big_dz.Services.WindowServices.*;

import com.example.kpo_big_dz.Interfaces.IMenu;
import com.example.kpo_big_dz.Interfaces.IUser;

import static com.example.kpo_big_dz.TempData.Observer.addAdminOrderListSubscriber;
import static com.example.kpo_big_dz.TempData.Observer.addMenuSubscriber;
import static com.example.kpo_big_dz.Services.GridPanelServices.loadToGridPaneDishes;

public class AdminPanelController implements IMenu, IUser, IAdminOrders {
    private Boolean isAddNewDishOpened = false;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ScrollPane menuScrollPane;

    @FXML
    private GridPane menuGridPane;

    @FXML
    private Button addNewDishButton;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private VBox dishVBox;

    @FXML
    private Button userOrdersListButton;

    @FXML
    private Button statisticsInfoButton;

    @FXML
    private Button menuButton;

    @FXML
    private GridPane userOrderGridPane;

    @FXML
    private ScrollPane userOrderScrollPane;

    @FXML
    private ScrollPane statisticsScrollPane;

    @FXML
    private GridPane statisticsGridPane;

    @FXML
    private Button exclamationButton;

    @FXML
    public void openAddNewDishWindow(ActionEvent e) {
        if (isAddNewDishOpened) {
            shakeButton(addNewDishButton);
            return;
        }
        try {
            AddNewDishController addDishWindow = openNewWindow("admin/add_dish_panel.fxml", "Add Dish");
            isAddNewDishOpened = true;
            addDishWindow.getCurrentStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    isAddNewDishOpened = false;
                }
            });
        } catch (IOException exception) {
            System.out.println("Add new dish window error: " + exception.getMessage());
        }
    }

    @FXML
    public void onUserOrderListButton(ActionEvent e) {
        exclamationButton.setVisible(false);
        hideAllScrollPanes();
        userOrderScrollPane.setVisible(true);
        loadToAdminUserOrdersGridPane(userOrderGridPane, 1);
    }

    @FXML
    public void onMenuButtonClick(ActionEvent e) {
        hideAllScrollPanes();
        menuScrollPane.setVisible(true);
    }

    @FXML
    void initialize() {
        addMenuSubscriber(this);
        addAdminOrderListSubscriber(this);
        loadToGridPaneDishes(menuGridPane, true, 2);
    }

    private int id;

    public int getUserID() {
        return id;
    }

    public void setUserID(int id) {
        this.id = id;
    }

    @Override
    public void updateAdminUserOrderList() {
        if (!userOrderScrollPane.isVisible()) {
            exclamationButton.setVisible(true);
        }
        loadToAdminUserOrdersGridPane(userOrderGridPane, 1);
    }

    @Override
    public void updateMenuList() {
        loadToGridPaneDishes(menuGridPane, true, 2);
    }

    private void hideAllScrollPanes() {
        userOrderScrollPane.setVisible(false);
        statisticsScrollPane.setVisible(false);
        menuScrollPane.setVisible(false);
    }
}
