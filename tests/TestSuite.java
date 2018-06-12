// A class that allows multiple tests to run at once.
// All tests that run should be listed in here.

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    ReaderTest.class,
    SchedulerTest.class
})

public class TestSuite {}