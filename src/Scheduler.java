import java.util.ArrayList;
import java.util.HashMap;

public class Scheduler
{
    private String file;
    private ArrayList<ArrayList<String>> globalSchedule = new ArrayList<ArrayList<String>>();
    private HashMap<String, ArrayList<String>> studentSchedules = new HashMap<String, ArrayList<String>>();
    
    public ArrayList<ArrayList<String>> getGlobalSchedule() { return globalSchedule; }
    public HashMap<String, ArrayList<String>> getStudentSchedules() { return studentSchedules; }
    
    public Scheduler(String file)
    {
        this.file = file;
        for(int i = 0; i < 8 /* max blocks */; i++)
            globalSchedule.add(new ArrayList<String>());
    }
    
    
    private int currentBlock = 0;
    private ArrayList<String> uniqueClasses;
    private ArrayList<Student> students;
    private ArrayList<String> classBuffer = new ArrayList<String>();
    
    public void generate()
    {
        // First, read the file into an ArrayList of students, checking for errors.
        Reader r = new Reader(file);
        r.read();
        uniqueClasses = r.getUniqueClasses();
        students = r.getStudents();
        // Then, add all of them to the HashMap.
        for(Student s : students)
            studentSchedules.put(s.getName(), new ArrayList<String>());

        while(students.stream().anyMatch((s) -> s.hasAnyRequired()))
        {
            while(!uniqueClasses.isEmpty())
                findAndScheduleMostCommonRequired();
            advanceBlock();
        }
    }
    
    private void findAndScheduleMostCommonRequired()
    {
        String mostcommon = "";
        ArrayList<Student> studentsInMaxClass = new ArrayList<Student>(), studentsInCurrentClass = new ArrayList<Student>();
        for(int i = 0; i < uniqueClasses.size(); i++, studentsInCurrentClass = new ArrayList<Student>())
        {
            for(Student s : students)
                if(s.hasRequired(uniqueClasses.get(i)))
                    studentsInCurrentClass.add(s);
            if(studentsInCurrentClass.size() >= studentsInMaxClass.size())
            {
                mostcommon = uniqueClasses.get(i);
                studentsInMaxClass = studentsInCurrentClass;
            }
        }
        schedule(mostcommon, currentBlock, studentsInMaxClass);
        // Now that the class has been scheduled, remove it for later consideration.
        uniqueClasses.remove(uniqueClasses.indexOf(mostcommon));
        
        for(int i = 0; i < uniqueClasses.size(); i++)
        {
            String className = uniqueClasses.get(i);
            if(studentsInMaxClass.stream().anyMatch((s) -> s.hasRequired(className)))
            {
                classBuffer.add(uniqueClasses.remove(i));
                i--;
            }
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