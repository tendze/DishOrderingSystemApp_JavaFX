package com.example.kpo_big_dz.Models;

public class Order {
    private int orderId;
    private int totalPrice;
    private int userId;

    OrderStatus status;

    public Order(int orderId, int userId, int totalPrice, OrderStatus status) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getOrderStatus() {
        return status;
    }

    public int getUserId() {
        return userId;
    }
}
