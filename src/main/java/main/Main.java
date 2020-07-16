package main;

import enums.BikeType;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("Execution started.");
        Stock stock = new Stock("MEGR01", BikeType.KID, 10, LocalDateTime.now(), 1500, 40000, 10);
        System.out.println(stock.getStockId());
        System.out.println("Execution finished.");
    }
}