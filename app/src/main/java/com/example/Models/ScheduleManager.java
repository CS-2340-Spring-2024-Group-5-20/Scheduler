package com.example.Models;

import java.util.ArrayList;

/**
 * Functional representation of the database for now.
 * Does not persist after closing the app.
 */
public class ScheduleManager {
    public ArrayList<CollegeClass> mondayClasses = new ArrayList<>();
    public ArrayList<CollegeClass> tuesdayClasses = new ArrayList<>();
    public ArrayList<CollegeClass> wednesdayClasses = new ArrayList<>();
    public ArrayList<CollegeClass> thursdayClasses = new ArrayList<>();
    public ArrayList<CollegeClass> fridayClasses = new ArrayList<>();
    public ArrayList<Task> academicTasks = new ArrayList<>();
    /**
     * This method will take in a collegeClass argument with a MeetingTimes Array. The method
     * will go through that array's elements. For each MeetingTime element it finds, it will create
     * a new collegeClass object with all the same data and add it into the schedule using the
     * addClassToSchedule method.
     *
     * @param arg the College class with a MeetingTime Array that is to be split.
     */
    public void classSplitter(CollegeClass arg) {
        for (int i = 0; i < arg.meetingTimes.length; i++) {
            addCourseToSchedule(new CollegeClass
                    (arg.getClassTitle(), arg.meetingTimes[i], arg.getProfessor(),
                            arg.getClassSection(), arg.getLocation(), arg.getRoomNumber()));
        }
    }
    /**
     * A method which takes in a class that is to be added to the overall schedule, and
     * relegates the operation to the addCourseToDay method with correct arraylist argument passed in.
     * @param toAdd CollegeClass object that is to be added.
     */
    public void addCourseToSchedule(CollegeClass toAdd) {
        switch (toAdd.meetingTime.getMeetDay()) {
            case Monday:
                addCourseToDay(mondayClasses, toAdd);
                break;
            case Tuesday:
                addCourseToDay(tuesdayClasses, toAdd);
                break;
            case Wednesday:
                addCourseToDay(wednesdayClasses, toAdd);
                break;
            case Thursday:
                addCourseToDay(thursdayClasses, toAdd);
                break;
            case Friday:
                addCourseToDay(fridayClasses, toAdd);
                break;
        }
    }
    /**
     * addTo method which takes in a collegeClass object to add, as well as an arraylist to add the class to.
     * @param arrayList the intended arrayList correlating to the correct day the toAdd argument belongs to.
     * @param toAdd the collegeClass object that is to be added into one of the 5 arraylists.
     */
    public void addCourseToDay(ArrayList<CollegeClass> arrayList, CollegeClass toAdd) {
        if (arrayList.isEmpty()) {
            arrayList.add(toAdd);
        } else {
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).compareTo(toAdd) < 0) {
                    arrayList.add(i, toAdd);
                    break;
                }
            }
        }
    }

    /**
     * This method takes in a Task to add to the academicTasks arraylist, and goes through each
     * index in the arraylist to see where it belongs chronologically.
     * @param toAdd Task that is to be added.
     */
    public void addTaskToSchedule(Task toAdd){
        if (academicTasks.size() == 0) {
            academicTasks.add(toAdd);
        } else {
            for (int i = 0; i < academicTasks.size(); i++) {
                if (academicTasks.get(i).compareTo(toAdd) < 0) {
                    academicTasks.add(i,toAdd);
                    break;
                }
            }
        }
    }
}