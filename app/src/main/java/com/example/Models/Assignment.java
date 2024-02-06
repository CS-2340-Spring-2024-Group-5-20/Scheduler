package com.example.Models;

/**
 * This class pertains to assignments, which extend the Task class.
 * The time string from the super class is used as a time for which the assignment is due,
 */
public class Assignment extends Task {
    /**
     * Added dueDate variable field for the purposes of display, not to be used in comparisons.
     * All fields used to create dueDate are already being used for compareTo() in Task class.
     */
    public String dueDate;
    private CollegeClass collegeClass;
    /**
     * Constructor for Assignment objects. This constructor accounts for a lack of a description.
     * @param course course for which this assignment originates from.
     * @param time time at which this assignment is due.
     * @param month numerical month the assignment is due.
     * @param dayOfMonth numerical day when the assignment is due.
     */
    public Assignment(CollegeClass course, String time, int month, int dayOfMonth){
        this(course, time, "No description.", month, dayOfMonth);
    }
    /**
     * Constructor for Assignment objects.
     * @param course course for which this assignment originates from.
     * @param time time at which this assignment is due.
     * @param description miscellaneous description of what assignment is.
     * @param month numerical month the assignment is due.
     * @param dayOfMonth numerical day when the assignment is due.
     * Big call to the super's constructor. UUID and Color assignment will occur in the same manner as outlined in the super class.
     */
    public Assignment (CollegeClass course, String time, String description, int month, int dayOfMonth){
        super(time, description, month, dayOfMonth);
        this.dueDate = String.format("%d, %d at %s", month, dayOfMonth, time);
        this.collegeClass = course;
    }
    public String getDueDate() {
        return dueDate;
    }
    public CollegeClass getCollegeClass() {return collegeClass;}
}