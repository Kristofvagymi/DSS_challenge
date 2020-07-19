package main;

import java.time.LocalDateTime;
import java.time.Month;

// This class stores current time in the simulation.
public class SimulatedDate {

    private static LocalDateTime dateTime = LocalDateTime.of(2020, Month.JULY, 20, 6, 0, 0);

    public static LocalDateTime getDate(){
        return dateTime;
    }

    public static void tick() {
        dateTime = dateTime.plusMinutes(1);

        //Simulates nights when the factory does not produce
        if (dateTime.getHour() == 22){
            dateTime = dateTime.plusDays(1);
            dateTime = dateTime.minusHours(16);
        }
    }
}
