package com.example.Models;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Functional representation of the database for now.
 * Does not persist after closing the app.
 */
public class ScheduleManager {
    public ArrayList<CollegeClass> mondayClasses;
    public ArrayList<CollegeClass> tuesdayClasses;
    public ArrayList<CollegeClass> wednesdayClasses;
    public ArrayList<CollegeClass> thursdayClasses;
    public ArrayList<CollegeClass> fridayClasses;
    public ArrayList<Task> academicTasks;

    /**
     * Default constructor for the schedule manager.
     */
    public ScheduleManager() {
        mondayClasses = new ArrayList<CollegeClass>();
        tuesdayClasses = new ArrayList<CollegeClass>();
        wednesdayClasses = new ArrayList<CollegeClass>();
        thursdayClasses = new ArrayList<CollegeClass>();
        fridayClasses = new ArrayList<CollegeClass>();
        academicTasks = new ArrayList<Task>();
    }

    /**
     * This method will take in a collegeClass argument with a MeetingTimes Array. The method
     * will go through that array's elements. For each MeetingTime element it finds, it will create
     * a new collegeClass object with all the same data and add it into the schedule using the
     * addClassToSchedule method.
     *
     * @param arg the College class with a MeetingTime Array that is to be split.
     */
    public void classSplitter(CollegeClass arg) {
        for (int i = 0; i < arg.getMeetingTimes().length; i++) {
            addCourseToSchedule(new CollegeClass
                    (arg.getClassTitle(), arg.getMeetingTimes()[i], arg.getProfessor(),
                            arg.getClassSection(), arg.getLocation(), arg.getRoomNumber()));
        }
    }
    /**
     * A method which takes in a class that is to be added to the overall schedule, and
     * relegates the operation to the addCourseToDay method with correct arraylist argument passed in.
     * @param toAdd CollegeClass object that is to be added.
     */
    public void addCourseToSchedule(CollegeClass toAdd) {
        switch (toAdd.getMeetingTime().getMeetDay()) {
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
        academicTasks.add(toAdd);
    }

    /**
     * Function to update course by UUID
     * @param id of course to update
     * @param toUpdate course to update existing course to
     */
    public void updateCourseByUUID(UUID id, CollegeClass toUpdate) {
        List<CollegeClass> allClasses = getAllClasses();
        Day oldDay = null;
        for (int i = 0; i < allClasses.size(); i++)
        {
            if (allClasses.get(i).getUUID().equals(id)) {
                oldDay = allClasses.get(i).getMeetingTime().getMeetDay();
            }
        }
        if (oldDay == null) {
            return;
        } else {
            switch (oldDay) {
                case Monday:
                    removeClass(mondayClasses, id);
                    break;
                case Tuesday:
                    removeClass(tuesdayClasses, id);
                    break;
                case Wednesday:
                    removeClass(wednesdayClasses, id);
                    break;
                case Thursday:
                    removeClass(thursdayClasses, id);
                    break;
                case Friday:
                    removeClass(fridayClasses, id);
                    break;
            }
        }

        switch (toUpdate.getMeetingTime().getMeetDay()) {
            case Monday:
                mondayClasses.add(toUpdate);
                break;
            case Tuesday:
                tuesdayClasses.add(toUpdate);
                break;
            case Wednesday:
                wednesdayClasses.add(toUpdate);
                break;
            case Thursday:
                thursdayClasses.add(toUpdate);
                break;
            case Friday:
                fridayClasses.add(toUpdate);
                break;
        }
    }

    /**
     * Helper to remove a class from an arraylist by id
     * @param dayClasses to iterate over
     * @param id of class to remove
     */
    private void removeClass(List<CollegeClass> dayClasses, UUID id) {
        for (int i = 0; i < dayClasses.size(); i++) {
            if (dayClasses.get(i).getUUID().equals(id)) {
                dayClasses.remove(i);
            }
        }
    }

    /**
     * Function to return all instance data lists as one large list
     * @return ArrayList of CollegeClass type of all college classes stored by DB
     */
    public ArrayList<CollegeClass> getAllClasses() {
        ArrayList<CollegeClass> allClasses = new ArrayList<>();

        allClasses.addAll(mondayClasses);
        allClasses.addAll(tuesdayClasses);
        allClasses.addAll(wednesdayClasses);
        allClasses.addAll(thursdayClasses);
        allClasses.addAll(fridayClasses);

        // remove duplicates
        Set<CollegeClass> set = new HashSet<>(allClasses);
        allClasses.clear();
        allClasses.addAll(set);

        return allClasses;
    }

    /**
     * Function to update a task by UUID from instance data list
     * @param id of task to delete
     */
    public void updateTaskByUUID(UUID id, Task task) {
        for (int i = 0; i < academicTasks.size(); i++) {
            Task t = academicTasks.get(i);
            if (t.getId().equals(id)) {
                // Update the task with the new task object
                academicTasks.set(i, task);
                break; // Exit the loop once the task is updated
            }
        }
    }

    /**
     * Function to delete a task by UUID from instance data list
     * @param id of task to delete
     */
    public void deleteTaskByUUID(UUID id) {
        Iterator<Task> iterator = academicTasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getId().equals(id)) {
                iterator.remove();
                Log.d("ScheduleManager", "Task deleted: " + task.getDescription());
                break;
            }
        }
    }

    /**
     * Function to delete a class by UUID from all instance data lists
     * @param id of class to delete
     */
    public void deleteClassByUUID(UUID id) {
        deleteClassFromDayByUUID(id, mondayClasses);
        deleteClassFromDayByUUID(id, tuesdayClasses);
        deleteClassFromDayByUUID(id, wednesdayClasses);
        deleteClassFromDayByUUID(id, thursdayClasses);
        deleteClassFromDayByUUID(id, fridayClasses);
    }

    /**
     * Function to delete a class from the lists by UUID
     * @param id of class to find and delete
     * @param dayClasses list to delete from
     */
    private void deleteClassFromDayByUUID(UUID id, List<CollegeClass> dayClasses) {
        Iterator<CollegeClass> iterator = dayClasses.iterator();
        while (iterator.hasNext()) {
            CollegeClass course = iterator.next();
            if (course.getUUID().equals(id)) {
                iterator.remove();
                Log.d("ScheduleManager", "Course deleted: " + course.getClassTitle());
                break;
            }
        }
    }

    /**
     * Method to get a college class by name from the class lists
     * @param className to find in the lists
     * @return CollegeClass associated object
     */
    public CollegeClass getCollegeClassByName(String className) {
        List<List<CollegeClass>> allDayClasses = new ArrayList<>();
        allDayClasses.add(mondayClasses);
        allDayClasses.add(tuesdayClasses);
        allDayClasses.add(wednesdayClasses);
        allDayClasses.add(thursdayClasses);
        allDayClasses.add(fridayClasses);

        for (List<CollegeClass> dayClasses : allDayClasses) {
            for (CollegeClass collegeClass : dayClasses) {
                if (collegeClass.getClassTitle().equalsIgnoreCase(className)) {
                    return collegeClass;
                }
            }
        }
        return null;
    }
}
