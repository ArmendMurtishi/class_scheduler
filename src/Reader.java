// Reads in the file based on the format in the README and creates Student objects
// based on it.
import java.util.ArrayList;

public class Reader
{
    /* Parse the file given, generating an ArrayList of students and checking for any errors.
     * The best way to do this is to write a grammar that we can interpret in this function.
     * Grammar:
     * List_Students
     *   List_Students \n Student
     * Student
     *   Name (String), Grade (int) \n "REQUIRED:" \n List_Classes \n "REQUESTED:" \n List_Classes \n
     * List_Classes
     *    List_Classes \n Class (String)
     */
    public static ArrayList<Student> read(String file) { return list_students(new Scanner(file)); }
    
    private static Student student(Scanner file)
    {
        while(file.hasNextLine())
        {
            // Match a comma and then 0 or more spaces
            file.useDelimiter(",\\s*");
        }
    }
    private static ArrayList<Student> list_students(Scanner file)
    {
        ArrayList<Student> res = new ArrayList<Student>();
        while((Student s = student(file)) != null)
        {
            res.add(student);
        }
        return res;
    }
}