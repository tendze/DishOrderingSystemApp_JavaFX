package com.example.kpo_big_dz.Controllers.PanelControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import static com.example.kpo_big_dz.Services.ButtonServices.shakeButton;
import static com.example.kpo_big_dz.Services.WindowServices.*;

import com.example.kpo_big_dz.Interfaces.IMenu;
import com.example.kpo_big_dz.Interfaces.IUser;
import static com.example.kpo_big_dz.TempData.Observer.addSubscriber;
import static com.example.kpo_big_dz.Services.GridPanelServices.loadToGridPaneDishes;

public class AdminPanelController implements IMenu, IUser {
    private Boolean isAddNewDishOpened = false;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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

    }
    @FXML
    void initialize() {
        addSubscriber(this);
        loadToGridPaneDishes(menuGridPane, true, 2);
    }

    @Override
    public void updateMenuList() {
        loadToGridPaneDishes(menuGridPane, true, 2);
    }

    private int id;

    public int getUserID() {
        return id;
    }

    public void setUserID(int id) {
        this.id = id;
    }
}
