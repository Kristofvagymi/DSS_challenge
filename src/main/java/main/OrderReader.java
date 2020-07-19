package main;

import enums.BikeType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class reads the input csv and create order object.
 */
public class OrderReader{

    public List<Order> readOrders(String path, String cvsSplitBy){
        List<Order> orders = new ArrayList<>();

        BufferedReader br = null;
        String line = "";

        try {

            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                String[] orderDetails = line.split(cvsSplitBy);
                String name = orderDetails[0];
                BikeType bikeType = BikeType.valueOf(orderDetails[1]);
                int totalCount = Integer.parseInt(orderDetails[2]);
                String[] date = orderDetails[3].split("\\.");
                String[] time = date[2].split("\\:");
                LocalDateTime deadLine = LocalDateTime.of(
                        2020,
                        Integer.parseInt(date[0]),
                        Integer.parseInt(date[1]),
                        Integer.parseInt(time[0].trim()),
                        Integer.parseInt(time[1]));
                int profitPerPiece = Integer.parseInt(orderDetails[4]);
                int penaltyForDelay = Integer.parseInt(orderDetails[5].replaceAll("\\s+",""));
                orders.add(new Order(name, bikeType, totalCount, deadLine, profitPerPiece, penaltyForDelay));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) { //TODO: -
            e.printStackTrace();
        }finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return orders;
    }
}
