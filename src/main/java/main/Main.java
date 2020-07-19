package main;

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
        System.out.println("Finish: " + LocalDateTime.now());

        System.out.println(pipeline.getStartBuffer().getStocksInBuffer().get(0));
        System.out.println(pipeline.getEndBuffer().getStocksInBuffer().get(0));
        System.out.println("Execution finished.");
    }
}