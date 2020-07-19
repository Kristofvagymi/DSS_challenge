package main;

import manufacture.Pipeline;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Execution started.");

        OrderReader orderReader = new OrderReader();
        List<Order> orders = orderReader.readOrders("input/example.csv", ",");

        Pipeline pipeline = new Pipeline(orders);

        System.out.println("Start: " + LocalDateTime.now());
        for (int i = 0; i < 500; i++) {
            pipeline.tick();
        }
        System.out.println("Finish: " + LocalDateTime.now());

        System.out.println(pipeline.getStartBuffer().getStocksInBuffer().get(0));
        System.out.println(pipeline.getEndBuffer().getStocksInBuffer().get(0));
        System.out.println("Execution finished.");
    }
}