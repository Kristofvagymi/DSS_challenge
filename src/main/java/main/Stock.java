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
    //Variables from csv
    private String stockId;
    private BikeType bikeType;
    private int totalCount;

    private LocalDateTime deadLine;

    private int profitPerPiece;
    private int penaltyForDelay;

    //Stock size information
    private int count;

    //Derived variables
    private int prio;
}
