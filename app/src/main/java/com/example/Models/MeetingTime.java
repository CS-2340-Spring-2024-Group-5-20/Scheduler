package com.example.Models;

/**
 * A class with MeetingTime Objects. These will consist of a day to meet and a start and end time.
 * The start and end dates will correlate to how long this class should appear repeatedly on the schedule.
 * The start and end dates are inclusive. The first and last times they appear correlate directly to the dates.
 */
public class MeetingTime {
    Day meetDay;
    String startTime, endTime;;
    /**
     * constructor for MeetingTime objects.
     * @param meetDay day that the class will meet.
     * @param startTime time that class will start on specified meet-day.
     * @param endTime time that class will end on specified meet-day.
     */
    public MeetingTime(Day meetDay, String startTime, String endTime) {
        this.meetDay = meetDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    /**
     * Getter for which day this class will meet.
     * @return day for which this class meets.
     */
    public Day getMeetDay() {
        return meetDay;
    }
    /**
     * Getter for start time of class on the specified day.
     * @return start time of the class on the specified day.
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Getter for end time of class on the specified day.
     * @return end time of the class on the specified day.
     */
    public String getEndTime() {
        return endTime;
    }
}