package com.example.front_end_current;

import android.util.Log;

import com.example.Models.ScheduleManager;

import java.util.ArrayList;

public class ScheduleManagerLogger {
    private static final String TAG = "Schedule Manager Logger";

    // Method to log the contents of the ScheduleManager
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

    // Method to log the contents of an ArrayList
    private void logArrayList(ArrayList<?> arrayList) {
        for (Object obj : arrayList) {
            Log.d(TAG, obj.toString());
        }
    }
}