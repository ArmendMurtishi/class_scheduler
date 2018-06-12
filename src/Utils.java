import java.util.ArrayList;

public abstract class Utils
{
    // Copy an ArrayList of String.
    public static ArrayList<String> copyStrings(ArrayList<String> list)
    {
        ArrayList<String> res = new ArrayList<String>();
        for(String s : list)
            res.add(new String(s));
        return res;
    }
    // Copy an ArrayList of Student.
    public static ArrayList<Student> copyStudents(ArrayList<Student> list)
    {
        ArrayList<Student> res = new ArrayList<Student>();
        for(Student s : list)
            res.add(new Student(s));
        return res;
    }
}