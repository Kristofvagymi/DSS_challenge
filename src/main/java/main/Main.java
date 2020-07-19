package main;

import io_handling.OrderReader;
import io_handling.OutputWriter;
import manufacture.Buffer;
import manufacture.Pipeline;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Execution started.");

        OrderReader orderReader = new OrderReader();
        List<Order> orders = orderReader.readOrders("input/example.csv", ",");

        Pipeline pipeline = new Pipeline(orders);
        Buffer endBuffer = pipeline.getEndBuffer();

        String[] worklogHeaders = {"Dátum", "Gép", "Kezdő időpont", "Záró időpont", "Megrendelés szám"};
        OutputWriter.initWriter("output/worklog.csv", worklogHeaders);

        while (!endBuffer.isFull()) {
            pipeline.calculatePriorities();
            pipeline.tick();
            SimulatedDate.tick();
        }
        // Save worklog.csv
        OutputWriter.closeStream();

        // Write output csv
        String[] outputHeader = {"Megrendelésszám", "Profit összesen", "Levont kötbér", "Munka megkezdése", "Készre jelentés ideje", "Megrendel és eredeti határideje"};
        OutputWriter.initWriter("output/output.csv", outputHeader);
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