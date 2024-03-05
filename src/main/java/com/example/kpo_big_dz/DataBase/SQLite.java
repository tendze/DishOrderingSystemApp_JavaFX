package com.example.kpo_big_dz.DataBase;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.kpo_big_dz.Controllers.PanelControllers.UserOrderController;
import com.example.kpo_big_dz.Main;
import com.example.kpo_big_dz.Models.Dish;

import static com.example.kpo_big_dz.TempData.CurrentUserOrders.orders;
import static com.example.kpo_big_dz.TempData.Observer.*;

import com.example.kpo_big_dz.Models.Order;
import com.example.kpo_big_dz.Models.OrderStatus;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class SQLite {
    public static final String dbName = "durgerking.db";
    private static final String defaultDBPath = "src/main/java/com/example/kpo_big_dz/DataBase/storage/";

    public static void initDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = getConnection();
            createTable();
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("The driver name is " + meta.getDriverName());
            System.out.println("DB connected");
        } catch (Exception e) {
            System.out.println("initDB() error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static void createTable() {
        String createUserTableQuery = "CREATE TABLE IF NOT EXISTS user(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "login CHAR(50)," +
                "password CHAR(50)," +
                "isAdmin BOOLEAN" +
                ")";
        String createDishTableQuery = "CREATE TABLE IF NOT EXISTS dish(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "name CHAR(50)," +
                "quantity INTEGER NOT NULL," +
                "price INTEGER NOT NULL," +
                "difficulty INTEGER NOT NULL," +
                "totalOrders INTEGER NOT NULL" +
                ")";

        String createOrdersTableQuery = "CREATE TABLE IF NOT EXISTS `order`(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "total_price INTEGER NOT NULL," +
                "status TEXT" +
                ")";
        String createDishesInOrderTableQuery = "CREATE TABLE IF NOT EXISTS order_dishes(" +
                "order_id INTEGER NULL," +
                "dish_name CHAR(50)," +
                "quantity INTEGER NOT NULL," +
                "price INTEGER NOT NULL" +
                ")";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(createUserTableQuery);
            stmt.execute(createDishTableQuery);
            stmt.execute(createOrdersTableQuery);
            stmt.execute(createDishesInOrderTableQuery);
        } catch (SQLException e) {
            System.out.println("createTable() error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void addUser(String login, String password, Boolean isAdmin) {
        String addUserQuery = "INSERT INTO user(login, password, isAdmin) VALUES(?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(addUserQuery)) {
            pstmt.setString(1, login);
            pstmt.setString(2, password);
            pstmt.setBoolean(3, isAdmin);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("addUser() error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static Boolean isUserInDB(String login) {
        String selectQuery = "SELECT * FROM user WHERE login = ? LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("isUserInDB() error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static Boolean isUserInDB(String login, String password) {
        String selectQuery = "SELECT * FROM user WHERE login = ? AND password = ? LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
            pstmt.setString(1, login);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("isUserInDB() error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void addDish(String name, int price, int quantity, int cookingTimeSecs) {
        String addDishQuery = "INSERT INTO dish(name, quantity, price, difficulty, totalOrders) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(addDishQuery)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, quantity);
            pstmt.setInt(3, price);
            pstmt.setInt(4, cookingTimeSecs);
            pstmt.setInt(5, 0);
            pstmt.executeUpdate();
            notifyMenuSubscribers();
        } catch (SQLException e) {
            System.out.println("addDish() error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void addDish(Dish dish) {
        String name = dish.getName();
        int quantity = dish.getAmout();
        int price = dish.getPrice();
        int cookingTimeSecs = dish.getCookingTimeSecs();
        String addDishQuery = "INSERT INTO dish(name, quantity, price, difficulty, totalOrders) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(addDishQuery)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, quantity);
            pstmt.setInt(3, price);
            pstmt.setInt(4, cookingTimeSecs);
            pstmt.setInt(5, 0);
            pstmt.executeUpdate();
            notifyMenuSubscribers();
        } catch (SQLException e) {
            System.out.println("addDish() error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static Boolean isDishInDB(String dishName) {
        String selectQuery = "SELECT * FROM dish WHERE name = ? LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
            pstmt.setString(1, dishName);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("isDishInDB() error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static List<Dish> getAllDishesList() {
        String selectDishesQuery = "SELECT * FROM dish";
        List<Dish> result = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(selectDishesQuery);
            while (rs.next()) {
                result.add(new Dish(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getInt(5)));
            }
            return result;
        } catch (SQLException e) {
            System.out.println("isDishInDB() error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static Boolean isUserAdmin(String login) {
        String selectDishesQuery = "SELECT * FROM user WHERE login = ? LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectDishesQuery)) {
            pstmt.setString(1, login);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                return result.getBoolean("isAdmin");
            }
            return false;
        } catch (SQLException e) {
            System.out.println("isDishInDB() error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void deleteDish(int dishID) {
        String deleteDishQuery = "DELETE FROM dish WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteDishQuery)) {
            pstmt.setInt(1, dishID);
            pstmt.executeUpdate();
            notifyMenuSubscribers();
        } catch (SQLException e) {
            System.out.println("addDish() error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static int getUserIdByLogin(String login) {
        String deleteDishQuery = "SELECT id FROM user WHERE login = ? LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteDishQuery)) {
            pstmt.setString(1, login);
            var result = pstmt.executeQuery();
            if (!result.next()) {
                return -1;
            } else {
                return result.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println("addDish() error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void addNewOrder(int userId, HashMap<String, Pair<Integer, Integer>> dishes) {
        String addOrderQuery = "INSERT INTO `order`(user_id, total_price, status) VALUES(?, ?, ?)";
        String addOrderDishesQuery = "INSERT INTO order_dishes(order_id, dish_name, quantity, price) VALUES(?, ?, ?, ?)";
        long lastId;
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(addOrderQuery)) {
            int total_price = 0;
            for (Map.Entry<String, Pair<Integer, Integer>> dish : dishes.entrySet()) {
                total_price += dish.getValue().getKey() * dish.getValue().getValue();
            }
            pstmt.setInt(1, userId);
            pstmt.setInt(2, total_price);
            pstmt.setString(3, "Processing");
            pstmt.executeUpdate();
            lastId = (int)getLastInsertedId("`order`");


        } catch (SQLException e) {
            System.out.println("addOrder() error: " + e.getMessage());
            throw new RuntimeException(e);
        }

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(addOrderDishesQuery)) {
            for (Map.Entry<String, Pair<Integer, Integer>> dish : dishes.entrySet()) {
                pstmt.setInt(1, (int)lastId);
                pstmt.setString(2, dish.getKey());
                pstmt.setInt(3, dish.getValue().getValue());
                pstmt.setInt(4, dish.getValue().getKey());
                pstmt.executeUpdate();
                pstmt.clearParameters();
            }
        } catch (SQLException e) {
            System.out.println("addOrder() error: " + e.getMessage());
            throw new RuntimeException(e);
        }
        notifyAdminOrderListSubscribers();
    }

    public static List<Order> getAllOrdersList() {
        String query = "SELECT * FROM `order`";
        try  (Connection conn = getConnection();
              Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            List<Order> result = new ArrayList<>();
            while (rs.next()) {
                String strStatus = rs.getString(4);
                OrderStatus status = null;

                switch (strStatus) {
                    case ("Processing") -> status = OrderStatus.Processing;
                    case ("Cooking") -> status = OrderStatus.Cooking;
                    case ("Ready") -> status = OrderStatus.Ready;
                    case ("Completed") -> status = OrderStatus.Completed;
                }
                result.add(new Order(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        status
                ));
            }
            return result;
        } catch (SQLException e) {
            System.out.println("getAllOrdersList() error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static List<Order> getAllOrdersListByUserId(int userId) {
        String query = "SELECT * FROM `order` WHERE user_id = ?";
        try  (Connection conn = getConnection();
              PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            List<Order> result = new ArrayList<>();
            while (rs.next()) {
                String strStatus = rs.getString(4);
                OrderStatus status = null;
                switch (strStatus) {
                    case ("Processing") -> status = OrderStatus.Processing;
                    case ("Cooking") -> status = OrderStatus.Cooking;
                    case ("Ready") -> status = OrderStatus.Ready;
                    case ("Completed") -> status = OrderStatus.Completed;
                }
                result.add(new Order(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        status
                ));
            }
            return result;
        } catch (SQLException e) {
            System.out.println("getAllOrdersList() error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void updateOrderStatus(int orderId, OrderStatus status, int userIdToNotify) {
        String updateQuery = "UPDATE `order` SET status = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, status.value());
            pstmt.setInt(2, orderId);
            pstmt.executeUpdate();
            notifyUserOrderListSubscribers(userIdToNotify);
            notifyAdminOrderListSubscribers();
        } catch (SQLException e) {
            System.out.println("updateOrderStatus() error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static int getUserIdByOrderId(int orderId) {
        String updateQuery = "SELECT * FROM `order` WHERE id = ? LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            System.out.println("updateOrderStatus() error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static long getLastInsertedId(String table) {
        String query = "SELECT MAX(id) FROM " + table;
        try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                return -1;
            }
            return rs.getLong(1);
        } catch (SQLException e) {
            System.out.println("getLastInsertedId() error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    private static Connection getConnection() {
        String url = "jdbc:sqlite:" + defaultDBPath + dbName;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("getConnection() error: " + e.getMessage());
        }
        return conn;
    }
}


