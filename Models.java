import java.util.UUID;
import java.util.Random;
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
        public UUID getUUID() {
            return id;
        }
        public String getClassName() {
            return classTitle;
        }
        public MeetingTime[] getMeetingTimes () {
            return meetingTimes;
        }
        public String getProfessor() {
            return professor;
        }
        public Color getColor() {
            return color;
        }
    }

    /**
     * This class will pertain to Tasks. Each task will have it's own UUID, its own String designated for time, and a
     * string designated for a description.
     */
    public abstract class Task {
        public UUID id;
        public String time;
        public String description;
    }

//    public class AcademicTasks extends Task {
//
//    }
//    Unneeded (?)
    public abstract ClassObject extends AcademicTask {
        /**
         * will compare two objects and determine which should appear first.
         * This will be done according to the rules outlined by how academic tasks will be displayed.
         * Their days will be compared first, then start times, then object types. Assignments will appear first.
         * Then if all the other things are the same, meaning possibly two classes or two assignments occur or are due
         * at the same time, it will be sorted via UUID number.
         */
//        @Override;
//        public int compareTo(AcacdemicTask obj) {
//            // while loop to run while return integer is zero. If it changes we will have our result.
//            // if this.Day.ordinal > obj.Day.Ordinal >>> return 1
//            // if return is still zero, check start times
//            // if return is still zero, check types
//            // if this.getClass != obj.getclass (
//            //    if current class is an assignment, it takes priority
//            // if they are of the same day, time, and type then organize by UUID's
//            //    if current.classid > obj.classid;
//        }
    }
    /**
     * will sort current backing data structure to organize academic tasks
     * by which day of the week the tasks occur, then by priority and then
     * class start/assignment due times.
     */
    public void classObjSorter() {

    }
    public class Exam extends ClassObject {
        public String endTime;

    }
    public class Assignments extends ClassObject {
        String dueDate; //inheirted as String time
    }

    public class ScheduleManager {
        public CollegeClass[] classes;
        public
        private compareObjects();
    }
}
