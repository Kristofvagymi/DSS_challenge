package main;

import enums.BikeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    //Type of the stock
    private Order order;

    //Stock size information
    private int count;

    //Derived variables
    private int prio;
}
