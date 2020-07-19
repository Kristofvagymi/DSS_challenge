package main;

import interfaces.Tickable;

import java.time.LocalDateTime;
import java.time.Month;

public class SimulatedDate {

    private static LocalDateTime dateTime = LocalDateTime.of(2020, Month.JULY, 20, 6, 0, 0);

    public static LocalDateTime getDate(){
        return dateTime;
    }

    public static void tick() {
        dateTime = dateTime.plusSeconds(1);
        if (dateTime.getHour() == 22){
            int year = dateTime.getYear();
            Month month = dateTime.getMonth();
            int day = dateTime.getDayOfMonth();
            dateTime = LocalDateTime.of(year, month, day+1, 6, 0, 0);
        }
    }
}
