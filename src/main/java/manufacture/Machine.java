package manufacture;

import interfaces.Tickable;
import lombok.*;
import main.Order;
import main.SimulatedDate;
import main.Stock;

import java.time.LocalDateTime;

@ToString
@Data
@RequiredArgsConstructor
public class Machine implements Tickable {
    @NonNull
    private Buffer sourceBuffer;
    @NonNull
    private Buffer destinationBuffer;

    private int timeLeft = 0;
    private Order order;
    private int priority = 0;

    @NonNull
    private int kidBikeTime;
    @NonNull
    private int teenBikeTime;
    @NonNull
    private int adultBikeTime;

    private void setStockWorkInProgress(){
        Stock nextStock = sourceBuffer.getStockWithHighestPrio();
        if(nextStock == null) return;
        order = nextStock.getOrder();
        priority = nextStock.getPrio();
        if (order.getStartDate() == null) {
            order.setStartDate(SimulatedDate.getDate());
        }
        switch (order.getBikeType()) {
            case GYB:
                timeLeft = kidBikeTime;
                break;
            case FB:
                timeLeft = adultBikeTime;
                break;
            case SB:
                timeLeft = teenBikeTime;
                break;
        }
        timeLeft--;
    }
    @Override
    public void tick() {
        if (timeLeft != 0) {
            timeLeft--;
        } else if (destinationBuffer.hasCapacity()) {
            if(order != null) {
                destinationBuffer.addReadyStock(order);
                order = null;
            }
            setStockWorkInProgress();
        }
    }

    public boolean isFree(){return (order == null);}
}
