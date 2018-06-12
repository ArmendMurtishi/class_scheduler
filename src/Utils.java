import java.util.ArrayList;
import java.util.Arrays;

public abstract class Utils
{
    // Copy an ArrayList of String.
    public static ArrayList<String> copyStrings(ArrayList<String> list)
    {
        String[] array = new String[list.size()];
        for(int i = 0; i < list.size(); i++)
            array[i] = new String(list.get(i));
        return new ArrayList<String>(Arrays.asList(array));
    }
    // Copy an ArrayList of Student.
    public static ArrayList<Student> copyStudents(ArrayList<Student> list)
    {
        Student[] array = new Student[list.size()];
        for(int i = 0; i < list.size(); i++)
            array[i] = new Student(list.get(i));
        return new ArrayList<Student>(Arrays.asList(array));
    }
}