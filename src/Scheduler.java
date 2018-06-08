import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class Scheduler
{
    private String file;
    private ArrayList<ArrayList<String>> globalSchedule;
    private HashMap<String, ArrayList<String>> studentSchedules;
    
    public ArrayList<ArrayList<String>> getGlobalSchedule() 
    {
        return globalSchedule; 
    }
    public HashMap<String, ArrayList<String>> getStudentSchedules() 
    {
        return studentSchedules; 
    }
    
    // Generate a HashMap associating a student name with an ArrayList of their classes.
    public Scheduler(String file)
    {
        // Generate the ArrayList, full of blank ArrayLists.
        ArrayList<ArrayList<String>> globalSchedule = new ArrayList<ArrayList<String>>(Collections.nCopies(8, new ArrayList<String>()));
        HashMap<String, ArrayList<String>> studentSchedules = new HashMap<String, ArrayList<String>>();
        
        this.file = file;
        this.globalSchedule = globalSchedule;
        this.studentSchedules = studentSchedules;
    }
    
    public void generateSchedules()
    {
        // First, read the file into an ArrayList of students, checking for errors.
        ArrayList<Student> students = Reader.read(file);
        // Then, add all of them to the HashMap.
        for(Student s : students)
            studentSchedules.put(s.getName(), new ArrayList<String>());
        
        while(anyStudentHasAnyRequired(students))
        {
            String mostcommon = findMostCommonRequired(unique, students);
            globalSchedule.get(0 /*block 0*/).add(mostcommon);
        }
    }
    
    private boolean anyStudentHasAnyRequired(ArrayList<Student> students)
    {
        for(Student s : students)
            if(s.hasAnyRequired())
                return true;
        return false;
    }
    
    private void findAndScheduleMostCommonRequired(ArrayList<String> unique, ArrayList<Student> students)
    {
        String mostcommon = "", current;
        int max_count = 0, count;
        ArrayList<Student> studentsInMaxClass = new ArrayList<Student>(), studentsInCurrentClass = new ArrayList<Student>();
        for(current = unique.remove(0), count = 0; !unique.isEmpty(); current = unique.remove(0), studentsInCurrentClass.clear())
        {
            for(Student s : students)
                if(s.hasRequired(current))
                {
                    count++;
                    studentsInCurrentClass.add(s);
                }
            if(count >= max_count)
            {
                mostcommon = current;
                studentsInMaxClass = studentsInCurrentClass;
            }
        }
        schedule(mostcommon, studentsInMaxClass);
    }
    
    private void schedule(String classname, int block, ArrayList<Student> students)
    {
        for(int i = 0; i < students.size(); i++)
        {
            students.get(i).removeRequired(classname);
            studentSchedules.get(students.get(i).getName()).add(classname);
        }
        globalSchedule.get(block).add(classname);
    }
    
    public boolean classFitsInSchedule()
    {
        /*if(//index of desired class in global schedule == index of an already scheduled class)
        {
            return true;
        }*/
        return false;
    }
}