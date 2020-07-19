package main;

import enums.BikeType;
import manufacture.Buffer;
import manufacture.ManufactureStep;
import manufacture.Pipeline;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ListResourceBundle;

public class Main {
    public static void main(String[] args) {
        System.out.println("Execution started.");

        OrderReader orderReader = new OrderReader("input/example.csv");
        List<Order> orders = orderReader.readOrders();

        Stock startStock = new Stock(10, 5, orders.get(0));
        Buffer startBuffer = new Buffer();
        startBuffer.addStock(startStock);

        Pipeline pipeline = new Pipeline(startBuffer, orders.get(0));
        System.out.println("Start: " + LocalDateTime.now());
        for (int i = 0; i < 500; i++) {
            pipeline.tick();
        }
        System.out.println("Finish: " + LocalDateTime.now());

        System.out.println(startStock);
        System.out.println(pipeline.getEndBuffer().getStocksInBuffer().get(0));
        System.out.println("Execution finished.");
    }
}