// Reads in the file based on the format in the README and creates Student objects
// based on it.
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.NoSuchElementException;

public class Reader
{
    public static final String REQUIRED_SEPARATOR = "REQUIRED:", REQUESTED_SEPARATOR = "REQUESTED:"; // constants to avoid code error and for ease of possible future change
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
    
    private static String class_name(Scanner file)
    {
        String s = file.nextLine();
        if(s.equals(REQUESTED_SEPARATOR) || s.equals("\n"))
            return null;
        // Check that the class is made up only of alphanumeric characters.
        // It is very difficult to figure out if the user missed a separator,
        // so the best we can do is warn them when we find a line not following the class criteria.
        if(!s.matches("^\\w+$"))
            throw new RuntimeException("Found non-alphanumeric character in class name! Did you forget a \"" + REQUESTED_SEPARATOR + "\" or a blank line?");
        return s;
    }
    private static ArrayList<String> list_classes(Scanner file)
    {
        ArrayList<String> res = new ArrayList<String>();
        String c;
        while((c = class_name(file)) != null)
            res.add(c);
        return res;
    }
    private static Student student(Scanner file)
    {
        if(!file.hasNextLine())
            return null;
        
        // Match the name and the grade.
        file.findInLine("^(\\w+\\s+\\w+),\\s*(\\d+)$");
        MatchResult mres = file.match();
        // We should have matched a name and a grade.
        // If not, throw an error.
        if(mres.groupCount() < 2)
            throw new RuntimeException("Missing name or grade.");
        else if(mres.groupCount() > 2)
            throw new RuntimeException("Extra input than the expected \"Name, Grade\".");
        String name = mres.group(1); // indexes start at 1 for these for some reason
        int grade = Integer.parseInt(mres.group(2));
        try { file.skip("^" + REQUIRED_SEPARATOR + "$"); }
        catch(NoSuchElementException exc) { throw new RuntimeException("Missing \"" + REQUIRED_SEPARATOR + "\"."); }
        // Now that we have the name and grade, proceed with getting both class lists.
        // By the grammar, this will call another function.
        ArrayList<String> required = list_classes(file);
        ArrayList<String> requested = list_classes(file);
        //file.nextLine(); - no need to call; list_classes will eat this extra line
        
        return new Student(name, grade, required, requested);
    }
    private static ArrayList<Student> list_students(Scanner file)
    {
        ArrayList<Student> res = new ArrayList<Student>();
        Student s;
        while((s = student(file)) != null)
            res.add(s);
        return res;
    }
}