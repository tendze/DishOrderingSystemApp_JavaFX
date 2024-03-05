package com.example.kpo_big_dz.TempData;

import com.example.kpo_big_dz.Threads.CookingThread;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CurrentCookingThreads {
    // id заказа - поток, который его готовит.
    public static HashMap<int, CookingThread> currentCookingThreads = new HashMap<int, CookingThread>();
}
