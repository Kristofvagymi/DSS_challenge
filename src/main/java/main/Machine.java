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
    private Order order;

    private int kidBikeTime;
    private int teenBikeTime;
    private int adultBikeTime;

    @Override
    public void tick() {
        if(timeLeft == 0){
            Stock nextStock = sourceBuffer.getStockWithHighestPrio();
            if(nextStock == null) return;
            else {
                destinationBuffer.addReadyStock(order);
                order = null; //TODO: EZ MI A FASZÉR VAN? EZ VOLT: stockId = "", ezér lett ez nyilván
            }

            order = nextStock.getOrder();
            switch (nextStock.getOrder().getBikeType()) {
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
        } else {timeLeft--;}
    }
}
