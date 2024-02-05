package com.example.front_end_current;

import com.example.Models.ScheduleManager;

public class Database {
    public static ScheduleManager DATABASE;

    public static void start() {
        DATABASE = new ScheduleManager();
    }
}
