package com.example.kpo_big_dz.TempData;

import java.util.List;

public class CurrentUserSessions {
    // Хранит логины пользователей, которые уже открыли окно.
    // Делается чтобы при входе уже активную сессию ничего не делалось.
    public static List<String> sessions;
}
