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

        System.out.println("Start: " + LocalDateTime.now());
        while (!endBuffer.isFull()) {
            pipeline.calculatePriorities();
            pipeline.tick();
            SimulatedDate.tick();
        }
        OutputWriter.initWriter("output/output.csv");
        createOutputCsv(orders);
        System.out.println("Finish: " + LocalDateTime.now());

        OutputWriter.closeStream();
        System.out.println("Execution finished.");
    }

    private static void createOutputCsv(List<Order> orders) {
        for (Order order : orders) {
            OutputWriter.logOrders(order);
        }
    }
}