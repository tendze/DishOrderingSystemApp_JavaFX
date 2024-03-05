package com.example.kpo_big_dz.TempData;

import com.example.kpo_big_dz.Interfaces.*;

import java.util.ArrayList;
import java.util.List;

public class Observer {
    public static List<IMenu> subscribers = new ArrayList<>();
    public static List<IAdminOrders> adminSubscribers = new ArrayList<>();

    public static List<IUserOrders> userSubscribers = new ArrayList<>();
    public static void notifyMenuSubscribers() {
        for (IMenu window : subscribers) {
            window.updateMenuList();
        }
    }

    public static void notifyAdminOrderListSubscribers() {
        for (IAdminOrders admins : adminSubscribers) {
            admins.updateAdminUserOrderList();
        }
    }

    public static void notifyUserOrderListSubscribers(int userId) {
        for (IUserOrders users : userSubscribers) {
            users.updateUserOrderList(userId);
        }
    }
    public static void addMenuSubscriber(IMenu w) {
        subscribers.add(w);
    }
    public static void addAdminOrderListSubscriber(IAdminOrders w) {
        adminSubscribers.add(w);
    }

    public static void addUserOrderListSubscriber(IUserOrders w) {
        userSubscribers.add(w);
    }
}
