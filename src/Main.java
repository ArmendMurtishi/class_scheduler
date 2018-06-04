import java.io.*;
public class Main
{
    // Get the file from args, use Scheduler to generate the schedules, and save them to unique files.
    public static void main(String[] args)
    {
        
    }
    /**
     * This method inputs the student objects state into the method
     * Then writes the students into the file that will be read to the schedule 
     * 
     * 6 errors
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