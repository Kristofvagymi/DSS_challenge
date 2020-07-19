package io_handling;

import main.Order;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class OutputWriter {
    public static FileWriter workLogWriter;

    public static void initWriter(String path){
        try {
            workLogWriter = new FileWriter("output/worklog.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void logOrders(Order order) {
        try {
            int total_profit = order.getTotalCount() * order.getProfitPerPiece();

            long overDays = ChronoUnit.DAYS.between(order.getDeadLine(),order.getEndDate());

            long loss = (overDays + 1) * order.getPenaltyForDelay();
            loss = Math.max(0, loss);

            workLogWriter.append(order.getName());
            workLogWriter.append(",");
            workLogWriter.append(total_profit+" Ft ");
            workLogWriter.append(",");
            workLogWriter.append(loss+" Ft ");
            workLogWriter.append(",");
            workLogWriter.append(order.getStartDate().getMonthValue()+ "." +order.getStartDate().getDayOfMonth()+ "." + order.getStartDate().getHour()+":"+order.getStartDate().getMinute());
            workLogWriter.append(",");
            workLogWriter.append(order.getEndDate().getMonthValue()+ "." +order.getEndDate().getDayOfMonth()+ "." + order.getEndDate().getHour()+":"+order.getEndDate().getMinute());
            workLogWriter.append(",");
            workLogWriter.append(order.getDeadLine().getMonthValue()+ "." +order.getDeadLine().getDayOfMonth()+ "." + order.getDeadLine().getHour()+":"+order.getDeadLine().getMinute());
            workLogWriter.append("\n");
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
