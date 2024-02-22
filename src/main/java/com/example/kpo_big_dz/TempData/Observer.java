package com.example.kpo_big_dz.TempData;

import com.example.kpo_big_dz.Interfaces.IWindow;

import java.util.ArrayList;
import java.util.List;

public class Observer {
    public static List<IWindow> subscribers = new ArrayList<>();
    public static void notifySubscribers() {
        for (IWindow window : subscribers) {
            window.updateMenuList();
        }
    }

    public static void addSubscriber(IWindow w) {
        subscribers.add(w);
    }
}
