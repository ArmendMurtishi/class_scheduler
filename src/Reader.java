// Reads in the file based on the format in the README and creates Student objects
// based on it.
import java.util.ArrayList;

public class Reader
{
    /* Parse the file given, generating an ArrayList of students and checking for any errors.
     * The best way to do this is to write a grammar that we can interpret in this function.
     * Grammar:
     * List_Students
     *   List \n Student
     * Student
     *   Name (String), Grade (int) \n "REQUIRED:" \n List_Classes \n "REQUESTED:" \n List_Classes \n
     * List_Classes
     *    List_Classes \n Class (String)
     */
    public static ArrayList<Student> read(String file)
    {
        ArrayList<Student> result = new ArrayList<Student>();
        
        return result;
    }
}