package com.example.Models;

import android.graphics.Color;

import java.util.Random;
import java.util.UUID;

/**
 * class for CollegeClass objects. These objects will correlate with a college course.
 * Each CollegeClass object will have a unique UUID, class title, an array of MeetingTime objects to correlate to what days
 * and when they should meet, a designated professor, and a (temporarily) randomized color to visually identify it.
 */
public class CollegeClass implements Comparable<CollegeClass> {

    private UUID id;
    private String classTitle;
    //        ClassObject[] classwork //arraylist (no longer needed as of 1/27, new arraylist will be designated solely for assignments)
    private String professor;
    private String classSection;
    private String location;
    private String roomNumber;
    private MeetingTime[] meetingTimes;
    private MeetingTime meetingTime;
    private Color color; //for now, random color from color array;
    private Random random = new Random();
    /**
     * Constructor for CollegeClass objects.
     * @param classTitle title of college course.
     * @param meetingTimes an array of different meeting times.
     * ^this is to account for a class which has non-uniform meeting patterns throughout the week.
     * @param professor the professor for this class.
     * @param classSection the section you are enrolled in for a certain course.
     * @param location the building wherein which this class is located in.
     * @param roomNumber the number of the room where the class is to be held.
     *  the unique UUID will be automatically generated, and the color used will be randomized as well (temporary).
     */
    public CollegeClass(String classTitle, MeetingTime[] meetingTimes, String professor, String classSection, String location, String roomNumber) {
        this.id = UUID.randomUUID();
        this.classTitle = classTitle;
        this.meetingTimes = meetingTimes;
        this.professor = professor;
        this.classSection = classSection;
        this.location = location;
        this.roomNumber = roomNumber;
//            color = COLOR_CONSTANTS[random.nextInt(6)];
    }

    /**
     * Different constructor for CollegeClass objects meant to work with only one meeting time.
     * @param classTitle title of college course.
     * @param meetingTime a meeting time. Details start time, end time, and other details.
     * @param professor the professor for this class.
     * @param classSection the section you are enrolled in for a certain course.
     * @param location the building wherein which this class is located in.
     * @param roomNumber the number of the room where the class is to be held.
     */
    public CollegeClass(String classTitle, MeetingTime meetingTime, String professor, String classSection, String location, String roomNumber) {
        this.id = UUID.randomUUID();
        this.classTitle = classTitle;
        this.meetingTime = meetingTime;
        this.professor = professor;
        this.classSection = classSection;
        this.location = location;
        this.roomNumber = roomNumber;
//            color = COLOR_CONSTANTS[random.nextInt(6)];
    }
    /**
     * Getter for this CollegeClass object's UUID.
     * @return UUID.
     */
    public UUID getUUID() {
        return id;
    }
    /**
     * Getter for the title of this CollegeClass object.
     * @return title of class.
     */
    public String getClassTitle() {
        return classTitle;
    }
    /**
     * Getter for MeetingTime Array of this CollegeClass object.
     * @return MeetingTime array of the days of the week and corresponding times the class meets.
     */
    public MeetingTime[] getMeetingTimes () {
        return meetingTimes;
    }

    /**
     * Getter for MeetingTime if singular meeting time is used.
     * @return MeetingTime object of college class.
     */
    public MeetingTime getMeetingTime() {return meetingTime; }

    /**
     * Getter for the professor that teaches this class.
     * @return name of professor that teaches this class.
     */
    public String getProfessor() {
        return professor;
    }
    /**
     * Getter for the class section for this class.
     * @return name of class section for class.
     */
    public String getClassSection(){return classSection;}
    /**
     * Getter for the building the class is located in.
     * @return name of building this class is located in.
     */
    public String getLocation(){return location;}
    /**
     * Getter for the room number this class is in.
     * @return room number for this class.
     */
    public String getRoomNumber(){return roomNumber;}
    /**
     * Getter for color.
     * @return color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Getter method for starting time of a class.
     * @return starting time of a class.
     * Note: only to be used with college class objects without meeting-times array.
     */
    public String getStartTime() {
        return meetingTime.getStartTime();
    }

    /**
     * compares 2 College Class objects (without MeetingTimes Array) and returns an integer.
     * Returns 1 if the college class object is earlier than the one passed into the argument.
     * Returns -1 if the college class object in the argument is earlier than the current object.
     * @param comp college class argument that is meant to be compared to the current one.
     * @return an integer representing which object occurs earlier.
     */
    public int compareTo(CollegeClass comp) {
        for (int i = 0; i < 4; i++) { //compares the digits in their respective start times to see who starts first.
            if (getStartTime().charAt(i) < comp.getStartTime().charAt(i)) {
                return 1;
            } else if (getStartTime().charAt(i) < comp.getStartTime().charAt(i)) {
                return -1;
            }
        }
        return id.compareTo(comp.getUUID()); //Reached only if they take place at the same time.
    }
}