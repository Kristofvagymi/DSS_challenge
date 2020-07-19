package main;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This class stores a given sized stock from an order.
 * And has a priority which is updated in every iteration.
 */
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
