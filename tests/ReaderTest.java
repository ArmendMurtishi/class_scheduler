import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
        ArrayList<Student> a = Reader.read(correct);
        assertEquals(a.size(), 1);
        assertEquals(a.get(0).getName(), "Armend Murtishi");
        assertEquals(a.get(0).getGrade(), 10);
        assertTrue(a.get(0).hasRequired("Math"));
        assertTrue(a.get(0).hasRequired("Science"));
        assertTrue(a.get(0).hasRequested("An elective"));
    }
    @Test
    public void testNoComma()
    {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(containsString(Reader.ERROR_IMPROPER_NAME_GRADE));
        Reader.read(nocomma);
    }
    @Test
    public void testMissingRequired()
    {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(containsString(Reader.ERROR_MISSING_REQUIRED));
        Reader.read(missingrequired);
    }
}