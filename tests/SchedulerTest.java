import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.hamcrest.CoreMatchers.containsString;

public class SchedulerTest
{
    public static String test1 = "Armend Murtishi, 10\n"
                               + "REQUIRED:\n"
                               + "Math\n"
                               + "Science\n"
                               + "REQUESTED:\n\n"
                               + "Test Student, 11\n"
                               + "REQUIRED:\n"
                               + "Math\n"
                               + "Test Class 2\n"
                               + "REQUESTED:\n\n"
                               + "Test Studen, 11\n"
                               + "REQUIRED:\n"
                               + "Math\n"
                               + "Science\n"
                               + "REQUESTED:\n\n";
    
    @Test
    public void testScheduleMostCommon()
    {
        Scheduler s = new Scheduler(test1);
        s.generate();
        assertEquals(1, s.getGlobalSchedule().get(0).size());
        assertEquals(2, s.getGlobalSchedule().get(1).size());
        assertEquals("Math", s.getGlobalSchedule().get(0).get(0));
        assertEquals("Math", s.getStudentSchedules().get("Armend Murtishi").get(0));
        assertEquals("Math", s.getStudentSchedules().get("Test Student").get(0));
        assertEquals("Math", s.getStudentSchedules().get("Test Studen").get(0));
        assertEquals("Science", s.getGlobalSchedule().get(1).get(0));
        assertEquals("Science", s.getStudentSchedules().get("Armend Murtishi").get(1));
        assertEquals("Science", s.getStudentSchedules().get("Test Studen").get(1));
        assertEquals("Test Class 2", s.getGlobalSchedule().get(1).get(1));
        assertEquals("Test Class 2", s.getStudentSchedules().get("Test Student").get(1));
    }
}