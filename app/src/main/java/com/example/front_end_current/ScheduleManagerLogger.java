package com.example.front_end_current;

// Android
import android.util.Log;

// Models
import com.example.Models.ScheduleManager;


// Java
import java.util.ArrayList;

/**
 * Non-functional logger class to facilitate quick logging of database.
 */
public class ScheduleManagerLogger {
    private static final String TAG = "Schedule Manager Logger";

    /**
     * Method to log the entire schedule manager instance.
     * @param scheduleManager to log.
     */
    public void logScheduleManager(ScheduleManager scheduleManager) {
        Log.d(TAG, "Monday Classes:");
        logArrayList(scheduleManager.mondayClasses);
        Log.d(TAG, "Tuesday Classes:");
        logArrayList(scheduleManager.tuesdayClasses);
        Log.d(TAG, "Wednesday Classes:");
        logArrayList(scheduleManager.wednesdayClasses);
        Log.d(TAG, "Thursday Classes:");
        logArrayList(scheduleManager.thursdayClasses);
        Log.d(TAG, "Friday Classes:");
        logArrayList(scheduleManager.fridayClasses);
        Log.d(TAG, "Academic Tasks:");
        logArrayList(scheduleManager.academicTasks);
    }

    /**
     * Helper method to log an individual arraylist within the schedule manager.
     * @param arrayList to log.
     */
    private void logArrayList(ArrayList<?> arrayList) {
        for (Object obj : arrayList) {
            Log.d(TAG, obj.toString());
        }
    }
}