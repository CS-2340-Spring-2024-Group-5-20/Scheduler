package com.example.Models;

import android.graphics.Color;

import java.util.Random;
import java.util.UUID;

import java.io.Serializable;

/**
 * This class will pertain to Tasks. Each task will have it's own UUID, its own String designated for time, and a
 * string designated for a description.
 * The integer fields for month and day of month exist for sorting purposes.
 */
public class Task implements Comparable<Task>, Serializable {
    public UUID id;
    public String time;
    public String description;
    public int month;
    public int dayOfMonth;
    Random random = new Random();

    /**
     * Constructor for Task objects.
     *
     * @param time        the time at which this task will take place.
     * @param description description of task
     * @param month       the month when this task will occur (as a number)
     * @param dayOfMonth  day of the month for which this task will occur.
     */
    public Task(String time, String description, int month, int dayOfMonth) {
        this.id = UUID.randomUUID();
        this.time = time;
        this.description = description;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    public UUID getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public int getMonth() {
        return month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    /**
     * An override of java's compareTo method, compares two Task objects and returns:
     * - a positive integer if the current object is better than the object passed into the argument.
     * - a negative integer if the current object is less than the object passed into the argument.
     * in the case of this scheduler app, a positive integer is return if the current object comes before the one passed into the argument.
     *
     * @param comp the Task object that the current object is to be compared to.
     * @return a positive or negative integer.
     */
    public int compareTo(Task comp) {
        if (month == comp.month) {
            if (dayOfMonth == comp.dayOfMonth) { // reached if they have the same month
                for (int i = 0; i < 4; i++) { // reached only if they have the same day of month and day of the week.
                    if (time.charAt(i) < comp.time.charAt(i)) { // checks each char at a time to see which one has an earlier time
                        return 1;
                    } else if (time.charAt(i) > comp.time.charAt(i)) {
                        return -1;
                    }
                }
                return (id.compareTo(comp.id)); // reached if no early returns are done in for-loop.
            } else {
                if (dayOfMonth < comp.dayOfMonth) { //reached if the months are the same but the days aren't.
                    return 1;
                } else {
                    return -1;
                }
            }
        } else { // reached if the months are not the same
            if (month < comp.month) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}