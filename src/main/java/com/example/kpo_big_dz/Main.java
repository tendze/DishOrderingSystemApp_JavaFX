package com.example.kpo_big_dz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.example.kpo_big_dz.DataBase.SQLite;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SQLite.initDB();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("auth/auth.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Authorization");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
