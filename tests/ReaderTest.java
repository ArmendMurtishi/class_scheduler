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
        assertEquals(1, a.size());
        assertEquals("Armend Murtishi", a.get(0).getName());
        assertEquals(10, a.get(0).getGrade());
        assertTrue(a.get(0).hasRequired("Math"));
        assertTrue(a.get(0).hasRequired("Science"));
        assertTrue(a.get(0).hasRequested("An elective"));
        // Each class here should be shown in the unique classes list.
        ArrayList<String> u = r.getUniqueClasses();
        assertEquals(3, u.size());
        assertEquals("Math", u.get(0));
        assertEquals("Science", u.get(1));
        assertEquals("An elective", u.get(2));
    }
    @Test
    public void testCorrectMultipleStudents()
    {
        Reader r = new Reader(correctMultipleStudents);
        r.read();
        ArrayList<Student> a = r.getStudents();
        
        assertEquals(2, a.size());
        
        assertEquals("Armend Murtishi", a.get(0).getName());
        assertEquals(10, a.get(0).getGrade());
        assertTrue(a.get(0).hasRequired("Math"));
        assertTrue(a.get(0).hasRequired("Science"));
        assertTrue(a.get(0).hasRequested("An elective"));
        // second student
        assertEquals("Test Student", a.get(1).getName());
        assertEquals(11, a.get(1).getGrade());
        assertTrue(a.get(1).hasRequired("Test Class"));
        assertTrue(a.get(1).hasRequired("Test Class 2"));
        assertFalse(a.get(1).hasAnyRequested());
        
        // Now check the unique classes list.
        ArrayList<String> u = r.getUniqueClasses();
        assertEquals(5, u.size());
        assertEquals("Math", u.get(0));
        assertEquals("Science", u.get(1));
        assertEquals("An elective", u.get(2));
        assertEquals("Test Class", u.get(3));
        assertEquals("Test Class 2", u.get(4));
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