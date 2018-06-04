// Reads in the file based on the format in the README and creates Student objects
// based on it.
import java.util.ArrayList;
import java.util.Scanner;

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
        if(!file.hasNextLine())
            return null;
        
        // Match the name and the grade.
        file.findInLine("^(\\w+\\s+\\w+),\\s*(\\d+)$");
        MatchResult mres = file.match();
        // We should have matched a name and a grade.
        // If not, throw an error.
        if(!mres.groupCount() == 2)
            // throw
        String name = mres.group(1); // indexes start at 1 for these for some reason
        int grade = Integer.parseInt(mres.group(2));
        file.nextLine();
        try { file.next("^REQUIRED:$"); file.nextLine(); }
        catch(NoSuchElementException exc) { /* throw */ }
        // Now that we have the name and grade, proceed with getting both class lists.
        // By the grammar, this will call another function.
        
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