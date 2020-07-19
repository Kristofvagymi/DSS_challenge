package main;

import enums.BikeType;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@ToString
public class Order {
    @NonNull
    private final String name;
    @NonNull
    private final BikeType bikeType;
    @NonNull
    private final int totalCount;
    @NonNull
    private final LocalDateTime deadLine;
    @NonNull
    private final int profitPerPiece;
    @NonNull
    private final int penaltyForDelay;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
