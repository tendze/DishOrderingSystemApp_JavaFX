package com.example.kpo_big_dz.Models;

public class Dish {
    private final int dishID;
    private final String name;
    private final int price;
    private final int amount;
    private final int cookingTimeSecs;


    public Dish(int id, String name, int amount, int price, int cookingTimeSecs) {
        this.dishID = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.cookingTimeSecs = cookingTimeSecs;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getAmout() {
        return amount;
    }

    public int getCookingTimeSecs() {
        return cookingTimeSecs;
    }
    public int getDishID() { return dishID; }
}