import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.hamcrest.CoreMatchers.containsString;

import java.util.ArrayList;

public class ReaderTest
{
    public static String correct = "Armend Murtishi, 10\n"
                                 + "REQUIRED:\n"
                                 + "Math\n"
                                 + "Science\n"
                                 + "REQUESTED:\n"
                                 + "An elective\n\n";
    public static String correctMultipleStudents = "Armend Murtishi, 10\n"
                                                 + "REQUIRED:\n"
                                                 + "Math\n"
                                                 + "Science\n"
                                                 + "REQUESTED:\n"
                                                 + "An elective\n\n"
                                                 + "Test Student, 11\n"
                                                 + "REQUIRED:\n"
                                                 + "Test Class\n"
                                                 + "Test Class 2\n"
                                                 + "REQUESTED:\n\n";
    public static String nocomma = "Armend Murtishi 10\n";
    public static String missingrequired = "Armend Murtishi, 10\n"
                                         + "Math\n"
                                         + "Science\n"
                                         + "REQUESTED:\n"
                                         + "An elective\n\n";
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void testCorrect()
    {
        Reader r = new Reader(correct);
        r.read();
        // Check to see if everything was imported correctly.
        ArrayList<Student> a = r.getStudents();
        assertEquals(a.size(), 1);
        assertEquals(a.get(0).getName(), "Armend Murtishi");
        assertEquals(a.get(0).getGrade(), 10);
        assertTrue(a.get(0).hasRequired("Math"));
        assertTrue(a.get(0).hasRequired("Science"));
        assertTrue(a.get(0).hasRequested("An elective"));
        // Each class here should be shown in the unique classes list.
        ArrayList<String> u = r.getUniqueClasses();
        assertEquals(u.size(), 3);
        assertEquals(u.get(0), "Math");
        assertEquals(u.get(1), "Science");
        assertEquals(u.get(2), "An elective");
    }
    @Test
    public void testCorrectMultipleStudents()
    {
        Reader r = new Reader(correctMultipleStudents);
        r.read();
        ArrayList<Student> a = r.getStudents();
        
        assertEquals(a.size(), 2);
        
        assertEquals(a.get(0).getName(), "Armend Murtishi");
        assertEquals(a.get(0).getGrade(), 10);
        assertTrue(a.get(0).hasRequired("Math"));
        assertTrue(a.get(0).hasRequired("Science"));
        assertTrue(a.get(0).hasRequested("An elective"));
        // second student
        assertEquals(a.get(1).getName(), "Test Student");
        assertEquals(a.get(1).getGrade(), 11);
        assertTrue(a.get(1).hasRequired("Test Class"));
        assertTrue(a.get(1).hasRequired("Test Class 2"));
        assertFalse(a.get(1).hasAnyRequested());
        
        // Now check the unique classes list.
        ArrayList<String> u = r.getUniqueClasses();
        assertEquals(u.size(), 5);
        assertEquals(u.get(0), "Math");
        assertEquals(u.get(1), "Science");
        assertEquals(u.get(2), "An elective");
        assertEquals(u.get(3), "Test Class");
        assertEquals(u.get(4), "Test Class 2");
    }
    @Test
    public void testNoComma()
    {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(containsString(Reader.ERROR_IMPROPER_NAME_GRADE));
        Reader r = new Reader(nocomma);
        r.read();
    }
    @Test
    public void testMissingRequired()
    {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(containsString(Reader.ERROR_MISSING_REQUIRED));
        Reader r = new Reader(missingrequired);
        r.read();
    }
}