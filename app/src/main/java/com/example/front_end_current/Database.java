package com.example.front_end_current;

// Models
import com.example.Models.ScheduleManager;

/**
 * Mock Database that holds a static DATABASE that acts as a representation of our Database
 * Based on the ScheduleManager model
 */
public class Database {
    public static ScheduleManager DATABASE;

    /**
     * Constructor to be called on activity initialization that starts the DB
     */
    public static void start() {
        DATABASE = new ScheduleManager();
    }
}
