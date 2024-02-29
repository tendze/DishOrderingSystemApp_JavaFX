package com.example.kpo_big_dz.Services;

import com.example.kpo_big_dz.Controllers.PanelControllers.DishController;
import com.example.kpo_big_dz.Controllers.PanelControllers.DishInCartController;
import com.example.kpo_big_dz.Controllers.PanelControllers.UserPanelController;
import com.example.kpo_big_dz.Main;
import com.example.kpo_big_dz.Models.Dish;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.kpo_big_dz.DataBase.SQLite.deleteDish;
import static com.example.kpo_big_dz.DataBase.SQLite.getAllDishesList;
import static com.example.kpo_big_dz.TempData.CurrentDishes.dishes;

public class GridPanelServices {
    public static void loadToGridPaneDishes(GridPane gridPane,
                                            Boolean isAdmin,
                                            Button dishesCountButton,
                                            int columnLimit,
                                            UserPanelController userPanel) {
        try {
            gridPane.getChildren().clear();
            int column = 0;
            int row = 1;
            dishes = getAllDishesList();
            for (Dish dish : dishes) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(Main.class.getResource("samples/dish_sample.fxml"));
                AnchorPane dishBox = fxmlLoader.load();
                DishController dishController = fxmlLoader.getController();
                dishController.setData(dish);
                dishController.setCurrentUserPanelController(userPanel);
                if (column == columnLimit) {
                    column = 0;
                    ++row;
                }
                var dishDeleteButton = dishController.getDeleteDishButton();
                dishDeleteButton.setOnAction(event -> deleteDish(dishController.getDishID()));
                if (isAdmin) {
                    dishController.setAdminDishView();
                } else {
                    dishController.setDishesInCartCountButton(dishesCountButton);
                    dishController.setUserDishView();
                    dishController.setPlusMinusGroupButtonVisible(false);
                }
                gridPane.add(dishBox, column++, row);

                // Расстояние между объектами
                javafx.scene.layout.GridPane.setMargin(dishBox, new Insets(5));
            }
        } catch (IOException e) {
            System.out.println("loadToGridPaneDishes initialize error: " + e.getMessage());
        }
    }

    public static void loadToGridPaneDishes(GridPane gridPane,
                                            Boolean isAdmin) {
        loadToGridPaneDishes(gridPane, isAdmin, new Button(), 2, new UserPanelController());
    }

    public static void loadToGridPaneDishes(GridPane gridPane,
                                            Boolean isAdmin,
                                            int columnLimit) {
        loadToGridPaneDishes(gridPane, isAdmin, new Button(), columnLimit, new UserPanelController());
    }

    public static void loadToGridPaneCartDishes(GridPane gridPane,
                                                int columnLimit,
                                                HashMap<String, Pair<Integer, Integer>> namePriceAmountMap) {
        try {
            gridPane.getChildren().clear();
            int column = 0;
            int row = 1;
            for (Map.Entry<String, Pair<Integer, Integer>> dish : namePriceAmountMap.entrySet()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(Main.class.getResource("samples/dish_sample_in_cart.fxml"));
                AnchorPane dishBox = fxmlLoader.load();
                DishInCartController dishInCartController = fxmlLoader.getController();
                dishInCartController.setData(new Dish(dish.getKey(),
                        dish.getValue().getValue(),
                        dish.getValue().getKey()));
                if (column == columnLimit) {
                    column = 0;
                    ++row;
                }
                gridPane.add(dishBox, column++, row);

                // Расстояние между объектами
                javafx.scene.layout.GridPane.setMargin(dishBox, new Insets(5));
            }
        } catch (IOException e) {
            System.out.println("loadToGridPaneCartDishes initialize error: " + e.getMessage());
        }

    }
}
