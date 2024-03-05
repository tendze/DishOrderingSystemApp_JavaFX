package com.example.kpo_big_dz.Models;

public enum OrderStatus {
    Ready ("Ready"),
    Cooking ("Cooking"),
    Processing ("Processing"),
    Completed ("Completed"),
    Cancelled ("Cancelled");

    private final String status;

    OrderStatus(String strStatus) {
        status = strStatus;
    }

    public String value() {
        return status;
    }
}
