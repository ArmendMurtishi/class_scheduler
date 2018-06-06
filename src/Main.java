import java.io.*;
import java.util.ArrayList;
public class Main
{
    // Get the file from args, use Scheduler to generate the schedules, and save them to unique files.
    public static void main(String[] args)
    {
        File fileOfStudents = File(README);
        ArrayList<Student> students = read(fileOfStudents);
        File globalSchedule = getGlobalSchedule();
        File studentSchedule = getStudentSchedule();
        
    }
    /**
     * This method inputs the student objects state into the method
     * Then writes the students into the file that will be read to the schedule 
     * 
     * 6 errors
     * 5 of them are on the name of the method
     */
    public void writeFile(getStudentSchedules()) throws IOException 
    {
        for(Map.Entry<String, HashMap> entry : getStudentSchedules().entrySet())
        {
            BufferedWriter admin = new BufferedWriter(new FileWriter(fileName));
            admin.write(getName());
            admin.write(getGrade()):
            admin.write(hasRequired());
            admin.write(hasRequested());
            admin.close();
        }
    }
    
}