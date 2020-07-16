package main;

import enums.BikeType;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("Execution started.");
        Order order = new Order("MEGR01", BikeType.KID, 10, LocalDateTime.now(), 1500, 40000);
        Stock startStock = new Stock(10, 5, order);
        Stock endStock = new Stock(0, 5, order);

        Buffer startBuffer = new Buffer();
        Buffer cutToBending = new Buffer();
        startBuffer.addStock(startStock);
        cutToBending.addStock(endStock);

        ManufactureStep cutter = new ManufactureStep(6, 5, 8, 6, startBuffer, cutToBending);

        for (int i = 0; i < 11; i++) {
            cutter.tick();
        }
        System.out.println(startStock);
        System.out.println(endStock);
        System.out.println("Execution finished.");
    }
}