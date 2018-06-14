import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Main
{
    // Get the file from args, use Scheduler to generate the schedules, and save them to unique files.
    public static void main(String[] args) throws IOException
    {
        String file = String.join("\n", Files.readAllLines(Paths.get(args[0]), Charset.forName("UTF-8")));
        Scheduler s = new Scheduler(file);
        s.generate();
        
        ArrayList<ArrayList<String>> globalSchedule = s.getGlobalSchedule();
        ArrayList<String> lines = new ArrayList<String>();
        for(ArrayList<String> a : globalSchedule)
            lines.add(String.join(", ", a));
        Files.write(Paths.get("global_schedule.txt"), lines, Charset.forName("UTF-8"));
        
        HashMap<String, ArrayList<String>> studentSchedules = s.getStudentSchedules();
        for(Map.Entry<String, ArrayList<String>> entry : studentSchedules.entrySet())
        {
            String path = entry.getKey().replaceAll("\\s", "_") + ".txt";
            lines = new ArrayList<String>();
            for(String st : entry.getValue()) lines.add(st);
            Files.write(Paths.get(path), lines, Charset.forName("UTF-8"));
        }
    }
}