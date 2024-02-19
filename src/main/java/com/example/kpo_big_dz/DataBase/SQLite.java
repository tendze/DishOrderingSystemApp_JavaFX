package com.example.kpo_big_dz.DataBase;

import java.io.File;
import java.util.Arrays;
import java.sql.*;


public class SQLite {
    private Connection conn;

    public static void initDB() {
        try {
            Class.forName("org.sqlite.JDBC:storage/durgerking.db");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}


