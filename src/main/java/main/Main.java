package main;

import io_handling.AppProperties;
import io_handling.OrderReader;
import io_handling.OutputWriter;
import manufacture.Buffer;
import manufacture.Pipeline;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Execution started.");
        AppProperties.loadProperties();

        OrderReader orderReader = new OrderReader();
        List<Order> orders = orderReader.readOrders(AppProperties.getOrdersPath(), ",");

        Pipeline pipeline = new Pipeline(orders);
        Buffer endBuffer = pipeline.getEndBuffer();

        String[] worklogHeaders = {"Dátum", "Gép", "Kezdő időpont", "Záró időpont", "Megrendelés szám"};
        OutputWriter.initWriter(AppProperties.getWorkLogPath(), worklogHeaders);

        while (!endBuffer.isFull()) {
            pipeline.calculatePriorities();
            pipeline.tick();
            SimulatedDate.tick();
        }
        // Save worklog.csv
        OutputWriter.closeStream();

        // Write output csv
        String[] outputHeader = {"Megrendelésszám", "Profit összesen", "Levont kötbér", "Munka megkezdése", "Készre jelentés ideje", "Megrendel és eredeti határideje"};
        OutputWriter.initWriter(AppProperties.getOrderResultPath(), outputHeader);
        createOutputCsv(orders);
        OutputWriter.closeStream();

        System.out.println("Execution finished.");
    }

    private static void createOutputCsv(List<Order> orders) {
        for (Order order : orders) {
            OutputWriter.logOrders(order);
        }
    }
}