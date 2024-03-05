package com.example.kpo_big_dz.Threads;

import com.example.kpo_big_dz.Models.OrderStatus;

import static com.example.kpo_big_dz.TempData.Observer.*;
import static com.example.kpo_big_dz.DataBase.SQLite.*;

public class CookingThread extends Thread {
    private int orderId;
    private int userId;
    @Override
    public void run() {
        try {
            updateOrderStatus(orderId, OrderStatus.Cooking, userId);
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
