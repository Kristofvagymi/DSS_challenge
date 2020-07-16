package main;

import enums.BikeType;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("Execution started.");
        Stock startStock = new Stock("MEGR01", BikeType.KID, 10, LocalDateTime.now(), 1500, 40000, 10, 5);
        Stock endStock = new Stock("MEGR01", BikeType.KID, 10, LocalDateTime.now(), 1500, 40000, 0, 5);

        Buffer startBuffer = new Buffer();
        Buffer cutToBending = new Buffer();
        startBuffer.addStock(startStock);
        cutToBending.addStock(endStock);

        ManufactureStep cutter = new ManufactureStep(6, 5, 8, 6, startBuffer, cutToBending);

        for (int i = 0; i < 6; i++) {
            cutter.tick();
        }
        System.out.println(startStock);
        System.out.println(endStock);
        System.out.println("Execution finished.");
    }
}