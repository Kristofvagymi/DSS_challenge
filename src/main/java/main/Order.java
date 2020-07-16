package main;

import enums.BikeType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {
    private final String name;
    private final BikeType bikeType;
    private final int totalCount;
    private final LocalDateTime deadLine;
    private final int profitPerPiece;
    private final int penaltyForDelay;
}
