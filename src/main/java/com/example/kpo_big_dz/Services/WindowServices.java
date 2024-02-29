package com.example.kpo_big_dz.Services;

import com.example.kpo_big_dz.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowServices {
    public static <T> T openNewWindow(String fxmlFilePath, String windowTitle) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFilePath));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.setResizable(false);
        stage.setTitle(windowTitle);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("images/durger-king-logo.png")));
        stage.show();
        return fxmlLoader.getController();
    }
}
