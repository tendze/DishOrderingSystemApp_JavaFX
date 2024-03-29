package com.example.kpo_big_dz.TempData;

import com.example.kpo_big_dz.Interfaces.*;

import java.util.ArrayList;
import java.util.List;

public class Observer {
    public static List<IMenu> menuSubscribers = new ArrayList<>();
    public static List<IAdminOrders> adminSubscribers = new ArrayList<>();

    public static List<IUserOrders> userSubscribers = new ArrayList<>();

    public static List<IStatistics> statisticsSubscribers = new ArrayList<>();
    public static void notifyMenuSubscribers() {
        for (IMenu window : menuSubscribers) {
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

    public static void notifyStatisticsSubscribers() {
        for (IStatistics stat : statisticsSubscribers) {
            stat.updateStatistics();
        }
    }

    public static void addMenuSubscriber(IMenu w) {
        menuSubscribers.add(w);
    }
    public static void addAdminOrderListSubscriber(IAdminOrders w) {
        adminSubscribers.add(w);
    }

    public static void addUserOrderListSubscriber(IUserOrders w) {
        userSubscribers.add(w);
    }

    public static void addStatisticsSubscriber(IStatistics w) {
        statisticsSubscribers.add(w);
    }
}
