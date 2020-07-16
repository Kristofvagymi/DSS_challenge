package main;

import enums.BikeType;
import manufacture.Buffer;
import manufacture.Pipeline;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("Execution started.");

        Order order = new Order("MEGR01", BikeType.KID, 10, LocalDateTime.now(), 1500, 40000);
        Stock startStock = new Stock(10, 5, order);
        Buffer startBuffer = new Buffer();
        startBuffer.addStock(startStock);

        Pipeline pipeline = new Pipeline(startBuffer, order);
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