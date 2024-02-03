package com.example.Models;

/**
 *This class relates to exams. This class is an extension of the Tasks class, adding a
 * String variable for the end time of an exam.
 */
public class Exam extends Task {
    /**
     * time at which the exam ends, differs from the ambiguous field "time".
     */
    public String endTime;
    /**
     * Constructor for Exam objects. This constructor accounts for a lack of a description.
     * @param course the course for which this exam belongs to.
     * @param time the start time of the exam.
     * @param endTime the time the exam is scheduled to end.
     * @param day day of the week this exam takes place.
     * @param month the month in which this exam will occur.
     * @param dayOfMonth numerical day of the month this exam will occur.
     */
    public Exam(CollegeClass course, String time, String endTime, Day day, int month, int dayOfMonth){
        this(course, time, endTime, "No description.", day, month, dayOfMonth);
    }
    /**
     * constructor for task objects.
     * @param course the course for which this exam belongs to.
     * @param time the start time of the exam.
     * @param endTime the time the exam is scheduled to end.
     * @param description miscellaneous description of the exam.
     * @param day day of the week this exam takes place.
     * @param month the month in which this exam will occur.
     * @param dayOfMonth numerical day of the month this exam will occur.
     * Big call to the super's constructor. UUID and Color assignment will occur in the same manner as outlined in the super class.
     */
    public Exam(CollegeClass course, String time, String endTime, String description, Day day, int month, int dayOfMonth){
        super(course, time, description, day, month, dayOfMonth);
        this.endTime = endTime;
    }
    public String getEndTime() {
        return endTime;
    }
}