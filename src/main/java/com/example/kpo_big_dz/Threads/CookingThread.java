package com.example.kpo_big_dz.Threads;

import com.example.kpo_big_dz.Models.OrderStatus;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.Timer;

import static com.example.kpo_big_dz.TempData.Observer.*;
import static com.example.kpo_big_dz.DataBase.SQLite.*;

public class CookingThread extends Thread{
    private int orderId;
    private int userId;

    public void run() {
        Platform.runLater(
                () -> {
                    updateOrderStatus(orderId, OrderStatus.Cooking, userId);
                    Timeline timeLine = new Timeline(
                            new KeyFrame(Duration.seconds(3), e -> {
                                if (isInterrupted()) {
                                    updateOrderStatus(orderId, OrderStatus.Cancelled, userId);
                                    return;
                                }
                                updateOrderStatus(orderId, OrderStatus.Ready, userId);
                            })
                    );
                    timeLine.play();
                }
        );
    }

    public CookingThread(int orderId, int userId) {
        this.orderId = orderId;
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }
}
