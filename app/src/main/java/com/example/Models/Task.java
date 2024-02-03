package com.example.Models;

import android.graphics.Color;

import java.util.Random;
import java.util.UUID;

/**
 * This class will pertain to Tasks. Each task will have it's own UUID, its own String designated for time, and a
 * string designated for a description.
 * The integer fields for month and day of month exist for sorting purposes.
 */
public class Task implements Comparable<Task> {
    public UUID id;
    public CollegeClass course;
    public String time;
    public String description;
    public Day day;
    public int month;
    public int dayOfMonth;
    public Color color;
    Random random = new Random();

    /**
     * constructor to account for if this academic task doesn't relate to a specific college course. (aka just a normal reminder)
     *
     * @param time        the time at which this task will take place.
     * @param description description of task
     * @param day         day of week on which this task will occur
     * @param month       the month when this task will occur (as a number)
     * @param dayOfMonth  day of the month for which this task will occur.
     *                    course field is left null and the color will be random.
     */
    public Task(String time, String description, Day day, int month, int dayOfMonth) {
        this(null, time, description, day, month, dayOfMonth);
//            this.color = COLOR_CONSTANTS[random.nextInt(6)];
    }

    /**
     * Constructor for Task objects.
     *
     * @param course      the course for which this task relates to.
     * @param time        the time at which this task will take place.
     * @param description description of task
     * @param day         day of week on which this task will occur
     * @param month       the month when this task will occur (as a number)
     * @param dayOfMonth  day of the month for which this task will occur.
     */
    public Task(CollegeClass course, String time, String description, Day day, int month, int dayOfMonth) {
        this.id = UUID.randomUUID();
        this.course = course;
        this.time = time;
        this.description = description;
        this.day = day;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.color = course.getColor();
    }

    public UUID getId() {
        return id;
    }

    public CollegeClass getCourse() {
        return course;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public Day getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public Color getColor() {
        return color;
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
            if (dayOfMonth == comp.dayOfMonth && day == day) { // reached if they have the same month
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