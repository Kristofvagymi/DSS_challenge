package main;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class OutputWriter {
    public static FileWriter workLogWriter;

    static {
        try {
            workLogWriter = new FileWriter("output/worklog.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logWork(String machineName, String orderName, LocalDateTime started, LocalDateTime finished){
        try {
            workLogWriter.append(started.getYear() + "." + started.getMonth() + "." + started.getDayOfMonth());
            workLogWriter.append(",");
            workLogWriter.append(machineName);
            workLogWriter.append(",");
            workLogWriter.append(started.getHour() + ":" + started.getMinute());
            workLogWriter.append(",");
            workLogWriter.append(finished.getHour() + ":" + finished.getMinute());
            workLogWriter.append(",");
            workLogWriter.append(orderName);
            workLogWriter.append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void closeStream(){
        try {
            workLogWriter.flush();
            workLogWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
