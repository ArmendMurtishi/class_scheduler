import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class Scheduler
{
    private String file;
    private ArrayList<ArrayList<String>> globalSchedule = new ArrayList<ArrayList<String>>(Collections.nCopies(8, new ArrayList<String>()));
    private HashMap<String, ArrayList<String>> studentSchedules = new HashMap<String, ArrayList<String>>();
    
    public ArrayList<ArrayList<String>> getGlobalSchedule() { return globalSchedule; }
    public HashMap<String, ArrayList<String>> getStudentSchedules() { return studentSchedules; }
    
    public Scheduler(String file) { this.file = file; }
    
    
    private int currentBlock = 0;
    private ArrayList<String> uniqueClasses;
    private ArrayList<String> classBuffer = new ArrayList<String>();
    
    public void generate()
    {
        // First, read the file into an ArrayList of students, checking for errors.
        Reader r = new Reader(file);
        r.read();
        uniqueClasses = r.getUniqueClasses();
        ArrayList<Student> students = r.getStudents();
        // Then, add all of them to the HashMap.
        for(Student s : students)
            studentSchedules.put(s.getName(), new ArrayList<String>());

        while(anyStudentHasAnyRequired(students))
        {
            while(!uniqueClasses.isEmpty())
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
    
    private void findAndScheduleMostCommonRequired(ArrayList<Student> students)
    {
        ArrayList<String> unique = Utils.copyStrings(uniqueClasses);
        String mostcommon = "";
        ArrayList<Student> studentsInMaxClass = new ArrayList<Student>(), studentsInCurrentClass = new ArrayList<Student>();
        for(int i = 0; i < unique.size(); i++, studentsInCurrentClass = new ArrayList<Student>())
        {
            for(Student s : students)
                if(s.hasRequired(unique.get(i)))
                    studentsInCurrentClass.add(s);
            if(studentsInCurrentClass.size() >= studentsInMaxClass.size())
            {
                mostcommon = unique.get(i);
                studentsInMaxClass = studentsInCurrentClass;
            }
        }
        schedule(mostcommon, currentBlock, studentsInMaxClass);
        // Now that the class has been scheduled, remove it for later consideration.
        uniqueClasses.remove(uniqueClasses.indexOf(mostcommon));
        
        for(int i = 0; i < uniqueClasses.size(); i++)
            if(anyStudentHasRequired(studentsInMaxClass, uniqueClasses.get(i)))
            {
                classBuffer.add(uniqueClasses.remove(i));
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
        int bufferSize = classBuffer.size();
        for(int i = 0; i < bufferSize; i++)
            uniqueClasses.add(classBuffer.remove(0));
        currentBlock++;
    }
}