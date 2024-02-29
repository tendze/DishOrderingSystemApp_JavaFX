package com.example.kpo_big_dz.TempData;

import com.example.kpo_big_dz.Interfaces.IMenu;

import java.util.ArrayList;
import java.util.List;

public class Observer {
    public static List<IMenu> subscribers = new ArrayList<>();
    public static void notifyMenuSubscribers() {
        for (IMenu window : subscribers) {
            window.updateMenuList();
        }
    }
    public static void addSubscriber(IMenu w) {
        subscribers.add(w);
    }
}
