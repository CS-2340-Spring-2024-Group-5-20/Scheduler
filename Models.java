import java.awt.*;
import java.util.UUID;
import java.util.Random;
import java.util.ArrayList;
public class Models {
    /**
     * An enum filled with all the days of the week, accessible by any class below which requires fixed days.
     */
    public enum Day {
    "Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"
    }

    /**
     * A class with MeetingTime Objects. These will consist of a day to meet and a start and end time.
     * The start and end dates will correlate to how long this class should appear repeatedly on the schedule.
     * The start and end dates are inclusive. The first and last times they appear correlate directly to the dates.
     */
    public class MeetingTime {
        Day meetDay;
        String startTime;
        String endTime;
        String startDate;
        String endDate;
        /**
         * constructor for MeetingTime objects.
         * @param meetDay day that the class will meet.
         * @param startTime time that class will start on specified meetday.
         * @param endTime time that class will end on specified meetday.
         * @param startDate the date from which the class will start regularly meeting.
         * @param endDate the date at which the class will no longer meet anymore.
         */
        public MeetingTime(Day meetDay, String startTime, String endTime, String startDate, String endDate) {
            this.meetDay = meetDay;
            this.startTime = startTime;
            this.endTime = endTime;
            this.startDate = startDate;
            this.endDate = endDate;
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

        /**
         * Getter for the date from which this class will begin routinely meeting.
         * @return Date for which this class will routinely meet.
         */
        public String getStartDate() {
            return startDate;
        }
        /**
         * Getter for the date from which this class will cease routinely meeting.
         * @return Date for which this class is done meeting.
         */
        public String getEndDate() {
            return endDate;
        }
    }

    /**
     * class for CollegeClass objects. These objects will correlate with a college course.
     * Each CollegeClass object will have a unique UUID, class title, an array of MeetingTime objects to correlate to what days
     * and when they should meet, a designated professor, and a (temorarily) randomized color to visually identify it.
     */
    public class CollegeClass {
        Color[] COLOR_CONSTANTS = [red, blue, green, yellow, orange...]
        UUID id;
        String classTitle;
//        ClassObject[] classwork //arraylist (no longer needed as of 1/27, new arraylist will be designated solely for assignments)
        MeetingTime[] meetingTimes;
        String professor;
        Color color; //for now, random color from color array;
        Random random = new Random();

        /**
         * Constructor for CollegeClass objects.
         * @param classTitle title of college course.
         * @param meetingTimes an array of different meeting times.
         * ^this is to account for a class which has non-uniform meeting patterns throughout the week.
         * @param professor the professor for this class.
         *  the unique UUID will be automatically generated, and the color used will be randomized as well (temporary).
         */
        public CollegeClass(String classTitle, MeetingTime[] meetingTimes, String professor) {
            this.id = UUID.randomUUID();
            this.classTitle = classTitle;
            this.meetingTimes = meetingTimes;
            this.professor = professor;
            color = COLOR_CONSTANTS[random.nextInt(6)];
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
         * Getter for the professor that teaches this class.
         * @return name of professor that teaches this class.
         */
        public String getProfessor() {
            return professor;
        }

        /**
         * Getter for color.
         * @return color.
         */
        public Color getColor() {
            return color;
        }
    }

    /**
     * This class will pertain to Tasks. Each task will have it's own UUID, its own String designated for time, and a
     * string designated for a description.
     * The integer fields for month and day of month exist for sorting purposes.
     */
    public class Task {
        public UUID id;
        public CollegeClass course;
        public String time;
        public String description;
        public Day day;
        public int month;
        public int dayOfMonth;
        public Color color;

        /**
         * constructor to account for if this academic task doesnt relate to a specific college course. (aka just a normal reminder)
         * @param time the time at which this task will take place.
         * @param description description of task
         * @param day day of week on which this task will occur
         * @param month the month when this task will occur (as a number)
         * @param dayOfMonth day of the month for which this task will occur.
         * course field is left null and the color will be random.
         */
        public Task(String time, String description, Day day, int month, int dayOfMonth) {
            this(null, time, description, day, month, dayOfMonth);
            this.color = COLOR_CONSTANTS[random.nextInt(6)];
        }

        /**
         * Constructor for Task objects.
         * @param course the course for which this task relates to.
         * @param time the time at which this task will take place.
         * @param description description of task
         * @param day day of week on which this task will occur
         * @param month the month when this task will occur (as a number)
         * @param dayOfMonth day of the month for which this task will occur.
         */
        public Task(CollegeClass course, String time, String description, Day day, int month, int dayOfMonth){
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
    }

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
            this(course, time, endTime, "No description.", day, month, dayOfMonth)
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

    /**
     * This class pertains to assignments, which extend the Task class.
     * The time string from the super class is used as a time for which the assignment is due,
     */
    public class Assignment extends Task {
        public String dueDate;

        /**
         * Constructor for Assignment objects. This constructor accounts for a lack of a description.
         * @param course course for which this assignment originates from.
         * @param time time at which this assignment is due.
         * @param day day of week the assignment is due.
         * @param month numerical month the assignment is due.
         * @param dayOfMonth numerical day when the assignment is due.
         */
        public Assignment(CollegeClass course, String time, Day day, int month, int dayOfMonth){
            this(course, time, "No description.", day, month, dayOfMonth);
        }
        /**
         * Constructor for Assignment objects.
         * @param course course for which this assignment originates from.
         * @param time time at which this assignment is due.
         * @param description miscellaneous description of what assignment is.
         * @param day day of week the assignment is due.
         * @param month numerical month the assignment is due.
         * @param dayOfMonth numerical day when the assignment is due.
         * Big call to the super's constructor. UUID and Color assignment will occur in the same manner as outlined in the super class.
         */
        public Assignment (CollegeClass course, String time, String description, Day day, int month, int dayOfMonth){
            super(course, time, description, day, month, dayOfMonth);
            this.dueDate = String.format("%s, %s - %s", day, month, dayOfMonth);
        }
        public String getDueDate() {
            return dueDate;
        }
    }

    public class ScheduleManager {
        public ArrayList<CollegeClass> classes = new ArrayList<CollegeClass>();
        public ArrayList<Task> academicTasks = new ArrayList<Task>();
        private compareObjects();
    }
}
