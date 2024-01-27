import Java.util.UUID;
public enum Day {
    "Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"
}
public class MeetingTime {
    Day day;
    String startTime;
    String endTime;
}
public class CollegeClass {
    Color[] COLOR_CONSTANTS = [red, blue, green, yellow, orange...]
    UUID id;
    String name;
    ClassObject[] classwork //arraylist
    MeetingTime[] meetingTimes;
    String professor;
    Color color; //for now, random color from color array;
}
public abstract class Task {
    public UUID id;
    public String time;
    public String description;
}

public class AcademicTasks inherits Task {

}
    public abstract ClassObject imports AcademicTask {
    public UUID classid;
    /**
     * will compare two objects and determine which should appear first.
     */
    @Override;
    public int compareTo(AcacdemicTask obj) {
        // if this.Day.ordinal > obj.Day.Ordinal >>> return 1
        // if return is still zero, check start times
        // if return is still zero, check types
        // if this.getClass != obj.getclass
        //    if current class is an assignment, it takes priority
        // if they are of the same day, time, and type then organize by UUID's
        //    if current.uuid > obj.uuid
    }
}
    /**
     * will sort current backing data structure to organize academic tasks
     * by which day of the week the tasks occur, then by priority and then
     * class start/assignment due times.
     */
    public void classObjSorter() {

    }
}
public class Exam inherits ClassObject {
    public String endTime;
}
public class Assignments inherits ClassObject {
    String dueDate; //inheirted as String time
}

public class ScheduleManager {
    def generateSchedule();
    private compareObjects();
}

