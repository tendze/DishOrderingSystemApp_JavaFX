package com.example.kpo_big_dz.Controllers.PanelControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.kpo_big_dz.Interfaces.IAdminOrders;
import com.example.kpo_big_dz.Interfaces.IStatistics;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

import static com.example.kpo_big_dz.Services.GridPanelServices.loadToGridPaneDishes;
import static com.example.kpo_big_dz.DataBase.SQLite.*;
import static com.example.kpo_big_dz.TempData.Observer.*;

public class AdminPanelController implements IMenu, IUser, IAdminOrders, IStatistics {
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
    private Button exclamationButton;

    @FXML
    private VBox statisticsVBox;

    @FXML
    private Label mostPopularDishLabel;

    @FXML
    private Label outcomeLabel;

    @FXML
    private Label averageRatingLabel;

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
    public void onStatisticButtonClick(ActionEvent e) {
        hideAllScrollPanes();
        statisticsVBox.setVisible(true);
        setStatisticsInformation();
    }

    @FXML
    void initialize() {
        addStatisticsSubscriber(this);
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

    private void setStatisticsInformation() {
        String mostPopularDish = getMostPopularDish();
        double averageRating = getAverageRatingOfOrders();
        int outcome = getOutcomeFromOrders();
        if (mostPopularDish.isEmpty()) {
            mostPopularDishLabel.setText("NO INFORMATION");
        } else {
            mostPopularDishLabel.setText(mostPopularDish);
        }
        if (averageRating == 0) {
            averageRatingLabel.setText("NO INFORMATION");
        } else {
            averageRatingLabel.setText(String.format("%.2f", averageRating));
        }
        outcomeLabel.setText(String.valueOf(outcome) + "$");
    }

    private void hideAllScrollPanes() {
        userOrderScrollPane.setVisible(false);
        statisticsVBox.setVisible(false);
        menuScrollPane.setVisible(false);
    }

    public void updateStatistics() {
        setStatisticsInformation();
    }
}
