import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.function.BiFunction;

public class Scheduler
{
    private static int maxBlock = 8;
    
    private String file;
    private ArrayList<ArrayList<String>> globalSchedule = new ArrayList<ArrayList<String>>();
    private HashMap<String, ArrayList<String>> studentSchedules = new HashMap<String, ArrayList<String>>();
    
    public ArrayList<ArrayList<String>> getGlobalSchedule() { return globalSchedule; }
    public HashMap<String, ArrayList<String>> getStudentSchedules() { return studentSchedules; }
    
    public Scheduler(String file)
    {
        this.file = file;
        for(int i = 0; i < maxBlock; i++)
            globalSchedule.add(new ArrayList<String>());
    }
    
    
    private int currentBlock = 0;
    private ArrayList<Student> students;
    private ArrayList<String> classBuffer = new ArrayList<String>();
    
    public void generate()
    {
        // First, read the file into an ArrayList of students, checking for errors.
        Reader r = new Reader(file);
        r.read();
        ArrayList<String> uniqueRequiredClasses = r.getUniqueRequiredClasses();
        ArrayList<String> uniqueRequestedClasses = r.getUniqueRequestedClasses();
        students = r.getStudents();
        // Then, add all of them to the HashMap.
        for(Student s : students)
            studentSchedules.put(s.getName(), new ArrayList<String>());
        
        // First, try to schedule all required classes successfully.
        while(students.stream().anyMatch((s) -> s.hasAnyRequired()))
        {
            while(!uniqueRequiredClasses.isEmpty())
                findAndScheduleMostCommonRequired(uniqueRequiredClasses);
                
            advanceBlock(uniqueRequiredClasses);
            // If we went a block above the max, we simply say that these schedules are impossible to achieve and abort.
            if(currentBlock >= maxBlock)
                throw new RuntimeException("Too few blocks to schedule all required classes.");
        }
        // Now, try to schedule all requested classes, but not as strictly;
        // i.e., if we cannot schedule all of the classes, we don't abort, but rather fill open blocks with study halls.
        while(students.stream().anyMatch((s) -> s.hasAnyRequested()) && currentBlock < maxBlock)
        {
            while(!uniqueRequestedClasses.isEmpty())
                findAndScheduleMostCommonRequested(uniqueRequestedClasses);
            advanceBlock(uniqueRequiredClasses);
        }
        // Fill the remaining students' schedules with study halls.
        for(int a = 0; a < students.size(); a++)
        {
            ArrayList<String> schedule = studentSchedules.get(students.get(a).getName());
            for(int i = maxBlock - schedule.size(); i > 0; i--)
                schedule.add("Study Hall");
        }
    }
    
    private void findAndScheduleMostCommonRequired(ArrayList<String> uniqueClasses)
    { findAndScheduleMostCommon(uniqueClasses, (Student s, String st) -> s.hasRequired(st)); }
    private void findAndScheduleMostCommonRequested(ArrayList<String> uniqueClasses)
    { findAndScheduleMostCommon(uniqueClasses, (Student s, String st) -> s.hasRequested(st)); }
    
    private void findAndScheduleMostCommon(ArrayList<String> uniqueClasses, BiFunction<Student, String, Boolean> has)
    {
        String mostcommon = "";
        ArrayList<Student> studentsInMaxClass = new ArrayList<Student>(), studentsInCurrentClass = new ArrayList<Student>();
        for(int i = 0; i < uniqueClasses.size(); i++, studentsInCurrentClass = new ArrayList<Student>())
        {
            for(Student s : students)
                if(has.apply(s, uniqueClasses.get(i)))
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
            if(studentsInMaxClass.stream().anyMatch((s) -> has.apply(s, className)))
            {
                classBuffer.add(uniqueClasses.remove(i));
                i--;
            }
        }
    }
    
    private void schedule(String classname, int block, ArrayList<Student> students)
    {
        globalSchedule.get(block).add(classname);
        for(int i = 0; i < students.size(); i++)
        {
            students.get(i).removeRequired(classname);
            studentSchedules.get(students.get(i).getName()).add(classname);
        }
    }
    
    private void advanceBlock(ArrayList<String> uniqueClasses)
    {
        int bufferSize = classBuffer.size();
        for(int i = 0; i < bufferSize; i++)
            uniqueClasses.add(classBuffer.remove(0));
        currentBlock++;
    }
}