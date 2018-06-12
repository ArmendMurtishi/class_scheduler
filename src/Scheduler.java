import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class Scheduler
{
    private String file;
    private ArrayList<ArrayList<String>> globalSchedule;
    private HashMap<String, ArrayList<String>> studentSchedules;
    
    public ArrayList<ArrayList<String>> getGlobalSchedule() { return globalSchedule; }
    public HashMap<String, ArrayList<String>> getStudentSchedules() { return studentSchedules; }
    
    private int currentBlock = 0;
    
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
    
    public void generate()
    {
        // First, read the file into an ArrayList of students, checking for errors.
        Reader r = new Reader(file);
        r.read();
        this.globunique = r.getUniqueClasses();
        ArrayList<Student> students = r.getStudents();
        // Then, add all of them to the HashMap.
        for(Student s : students)
            studentSchedules.put(s.getName(), new ArrayList<String>());

        //while(anyStudentHasAnyRequired(students))
        for(int i = 0; i < 3; i++)
        {
            while(!globunique.isEmpty())
            {
                findAndScheduleMostCommonRequired(students);
            }
            advanceBlock();
        }
    }
    
    private boolean anyStudentHasAnyRequired(ArrayList<Student> students)
    {
        for(Student s : students)
            if(s.hasAnyRequired())
                return true;
        return false;
    }
    private boolean anyStudentHasRequired(ArrayList<Student> students, String className)
    {
        for(Student s : students)
            if(s.hasRequired(className))
                return true;
        return false;
    }
    
    private ArrayList<String> classBuffer = new ArrayList<String>();
    private ArrayList<String> globunique;
    
    private void findAndScheduleMostCommonRequired(ArrayList<Student> students)
    {
        ArrayList<String> unique = Utils.copyStrings(globunique);
        String mostcommon = "", current;
        int max_count = 0, count = 0;
        ArrayList<Student> studentsInMaxClass = new ArrayList<Student>(), studentsInCurrentClass = new ArrayList<Student>();
        for(current = unique.remove(0); !unique.isEmpty(); current = unique.remove(0), studentsInCurrentClass.clear(), count = 0)
        {
            for(Student s : students)
                if(s.hasRequired(current))
                {
                    count++;
                    studentsInCurrentClass.add(s);
                }
            if(count >= max_count)
            {
                max_count = count;
                mostcommon = current;
                studentsInMaxClass = Utils.copyStudents(studentsInCurrentClass);
            }
        }
        schedule(mostcommon, currentBlock, studentsInMaxClass);
        // Now that the class has been scheduled, remove it for later consideration.
        globunique.remove(globunique.indexOf(mostcommon));
        
        for(int i = 0; i < globunique.size(); i++)
            if(anyStudentHasRequired(studentsInMaxClass, globunique.get(i)))
            {
                classBuffer.add(globunique.remove(i));
                i--;
            }
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
    
    private void advanceBlock()
    {
        for(int i = 0; i < classBuffer.size(); i++)
            globunique.add(classBuffer.remove(0));
        currentBlock++;
    }
}