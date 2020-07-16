package main;

import enums.BikeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    //Variables from csv
    private String stockId;
    private BikeType bikeType;
    private int totalCount;

    private LocalDateTime deadLine;
    //private DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    //String formattedDate = myDateObj.format(myFormatObj);

    private int profitPerPiece;
    private int penaltyForDelay;

    //Stock size information
    private int count;

    //Derived variables
}
