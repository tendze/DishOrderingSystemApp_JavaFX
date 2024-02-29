package com.example.kpo_big_dz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import com.example.kpo_big_dz.DataBase.SQLite;

    public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SQLite.initDB();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("auth/auth.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("admin/admin_panel.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Authorization");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("images/durger-king-logo.png")));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
