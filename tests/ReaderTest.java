import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.ArrayList;

public class ReaderTest
{
    public static String correct = "Armend Murtishi, 10\n"
                                 + "REQUIRED:\n"
                                 + "Math"
                                 + "Science"
                                 + "REQUESTED:\n"
                                 + "An elective\n\n";
    public static String nocomma = "Armend Murtishi 10\n";
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void testCorrect()
    {
        ArrayList<Student> a = Reader.read(correct);
    }
    @Test
    public void testNoComma()
    {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Improper format for name and grade. Expected \"(Name), (Grade)\".");
        ArrayList<Student> a = Reader.read(nocomma);
    }
}