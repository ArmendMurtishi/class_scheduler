import java.util.ArrayList;
public class Student
{
    private String name;
    private int grade;
    private ArrayList<String /*class name*/> required;
    private ArrayList<String /*class name*/> requested;
    
    public Student(String name, int grade, ArrayList<String> required, ArrayList<String> requested)
    {
        this.name = name;
        this.grade = grade;
        this.required = required;
        this.requested = requested;
    }
    
    public boolean hasAnyRequired() { return !required.isEmpty(); }
    public boolean hasAnyRequested() { return !requested.isEmpty(); }
    
    // Check if a class is part of the required ArrayList.
    public boolean hasRequired(String s) { return required.contains(s); }
    public boolean hasRequested(String s) { return requested.contains(s); }
    
    public void removeRequired(String s) { required.remove(s); }
    public void removeRequested(String s) { requested.remove(s); }
    
    public String getName() { return name; }
    public int getGrade() { return grade; }
    
    public String toString() { return name; }
}