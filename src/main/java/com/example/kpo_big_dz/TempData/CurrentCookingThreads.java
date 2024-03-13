package com.example.kpo_big_dz.TempData;

import com.example.kpo_big_dz.Threads.CookingThread;

import java.util.HashMap;

public class CurrentCookingThreads {
    // id заказа - поток, который его готовит.
    public static HashMap<Integer, CookingThread> currentCookingThreads = new HashMap<Integer, CookingThread>();
}
