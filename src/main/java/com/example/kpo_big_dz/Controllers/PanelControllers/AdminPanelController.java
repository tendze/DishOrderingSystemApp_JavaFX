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

import com.example.kpo_big_dz.Interfaces.IWindow;
import static com.example.kpo_big_dz.TempData.Observer.addSubscriber;
import static com.example.kpo_big_dz.Services.GridPanelServices.loadToGridPaneDishes;

public class AdminPanelController implements IWindow {
    private Boolean isAddNewDishOpened = false;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane dishesContainer;

    @FXML
    private Button addNewDishButton;

    @FXML
    private Button statisticsInfoButton;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private VBox dishVBox;

    @FXML
    public void openAddNewDishWindow(ActionEvent e) {
        if (isAddNewDishOpened) {
            shakeButton(addNewDishButton);
            return;
        }
        try {
            Stage addDishWindow = openNewWindow("admin/add_dish_panel.fxml", "Add Dish");
            isAddNewDishOpened = true;
            addDishWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    isAddNewDishOpened = false;
                }
            });
        } catch (IOException exception) {
            System.out.println("Add new dish window error: " + exception.getMessage());
        }
    }

    @FXML
    void initialize() {
        addSubscriber(this);
        loadToGridPaneDishes(dishesContainer, true, 2);
    }

    @Override
    public void updateMenuList() {
        loadToGridPaneDishes(dishesContainer, true, 2);
    }
}
