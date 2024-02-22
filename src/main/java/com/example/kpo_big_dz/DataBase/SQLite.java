package com.example.kpo_big_dz.DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.kpo_big_dz.Models.Dish;
import static com.example.kpo_big_dz.TempData.Observer.notifySubscribers;

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
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(createUserTableQuery);
            stmt.execute(createDishTableQuery);
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
            notifySubscribers();
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
            notifySubscribers();
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
            notifySubscribers();
        } catch (SQLException e) {
            System.out.println("addDish() error: " + e.getMessage());
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


