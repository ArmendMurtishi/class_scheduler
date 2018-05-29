import java.util.ArrayList;
import java.util.HashMap;

public class Scheduler
{
    private ArrayList<String> globalSchedule;
    private HashMap<String, ArrayList<String>> studentSchedules;
    
    public ArrayList<String> getGlobalSchedule() { return globalSchedule; }
    public HashMap<String, ArrayList<String>> getStudentSchedules() { return studentSchedules; }
    
    // Generate a HashMap associating a student name with an ArrayList of their classes.
    public Scheduler(String file)
    {
        ArrayList<String> globalSchedule = new ArrayList<String>();
        HashMap<String, ArrayList<String>> studentSchedules = new HashMap<String, ArrayList<String>>();
        
        // First, read the file into an ArrayList of students, checking for errors.
        ArrayList<Student> students = Reader.read(file);
        
        this.globalSchedule = globalSchedule;
        this.studentSchedules = studentSchedules;
    }
}