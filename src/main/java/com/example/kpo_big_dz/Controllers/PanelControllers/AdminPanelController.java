package com.example.kpo_big_dz.Controllers.PanelControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.kpo_big_dz.Main;
import com.example.kpo_big_dz.Models.Dish;
import com.example.kpo_big_dz.DataBase.SQLite;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import static com.example.kpo_big_dz.DataBase.SQLite.addDish;
import static com.example.kpo_big_dz.Services.ButtonServices.shakeButton;
import static com.example.kpo_big_dz.Services.WindowServices.*;
import static com.example.kpo_big_dz.TempData.CurrentDishes.dishes;
import com.example.kpo_big_dz.Interfaces.IWindow;
import static com.example.kpo_big_dz.TempData.Observer.addSubscriber;
import static com.example.kpo_big_dz.DataBase.SQLite.deleteDish;

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
        loadToGridPaneDishes();
    }


    private void loadToGridPaneDishes() {
        try {
            dishesContainer.getChildren().clear();
            int column = 0;
            int row = 1;
            dishes = SQLite.getAllDishesList();
            for (Dish dish : dishes) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(Main.class.getResource("samples/dish_sample.fxml"));
                AnchorPane dishBox = fxmlLoader.load();
                DishController dishController = fxmlLoader.getController();
                dishController.setData(dish);
                if (column == 2) {
                    column = 0;
                    ++row;
                }
                dishController.setRowAndColumnIndex(row, column);
                var dishDeleteButton = dishController.getDeleteDishButton();
                dishDeleteButton.setOnAction(event -> deleteDish(dishController.getDishID()));
                dishController.setAdminDishView();
                dishesContainer.add(dishBox, column++, row);

//                dishesContainer.setMinWidth(Region.USE_COMPUTED_SIZE);
//                dishesContainer.prefWidth(Region.USE_COMPUTED_SIZE);
//                dishesContainer.setMaxWidth(Region.USE_COMPUTED_SIZE);
//
//                dishesContainer.setMinHeight(Region.USE_COMPUTED_SIZE);
//                dishesContainer.prefHeight(Region.USE_COMPUTED_SIZE);
//                dishesContainer.setMaxHeight(Region.USE_COMPUTED_SIZE);

                // Расстояние между объектами
                GridPane.setMargin(dishBox, new Insets(5));
            }
        } catch (IOException e) {
            System.out.println("AdminPanelController initialize error: " + e.getMessage());
        }
    }

    @Override
    public void updateMenuList() {
        loadToGridPaneDishes();
    }
}
