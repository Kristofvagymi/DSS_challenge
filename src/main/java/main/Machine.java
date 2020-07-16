package main;

import interfaces.Tickable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Machine implements Tickable {
    private Buffer sourceBuffer;
    private Buffer destinationBuffer;

    private int timeLeft = 0;
    private String orderId = "";

    private int kidBikeTime;
    private int teenBikeTime;
    private int adultBikeTime;

    private void setStockWorkInProgress(){
        Stock nextStock = sourceBuffer.getStockWithHighestPrio();
        if(nextStock == null) return;
        orderId = nextStock.getStockId();
        orderId = nextStock.getStockId();
        switch (nextStock.getBikeType()) {
            case KID:
                timeLeft = kidBikeTime;
                break;
            case ADULT:
                timeLeft = adultBikeTime;
                break;
            case TEEN:
                timeLeft = teenBikeTime;
                break;
        }
        timeLeft--;
    }
    @Override
    public void tick() {
        if (timeLeft != 0) {timeLeft--;}
        else if(timeLeft == 0 && !orderId.equals("")) {
            destinationBuffer.addReadyStock(orderId);
            orderId = "";
            setStockWorkInProgress();
        } else if(timeLeft == 0 && orderId.equals("")) {
            setStockWorkInProgress();
        }
    }
}
