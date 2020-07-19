package manufacture;

import interfaces.Tickable;
import lombok.*;
import main.Order;
import io_handling.OutputWriter;
import main.SimulatedDate;
import main.Stock;

import java.time.LocalDateTime;

/**
 * Machine class represents a machine in the factory. Machines are connected via buffers. They prefer jobs with high priority.
 */
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
    private LocalDateTime started;

    // Required time for given job.
    @NonNull
    private int kidBikeTime;
    @NonNull
    private int teenBikeTime;
    @NonNull
    private int adultBikeTime;
    @NonNull
    private String name;

    private void setStockWorkInProgress(){
        //Find the most important job from the source buffer.
        Stock nextStock = sourceBuffer.getStockWithHighestPrio();

        //There are no inputs.
        if(nextStock == null) return;

        started = SimulatedDate.getDate();
        order = nextStock.getOrder();
        priority = nextStock.getPrio();

        // If this is the first appearance of a job saves start time.
        if (order.getStartDate() == null) {
            order.setStartDate(SimulatedDate.getDate());
        }

        // Set the remaining time
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
            //The machine works on a job.
            timeLeft--;
        } else if (destinationBuffer.hasCapacity()) {
            // The destination buffer can handle the ready output.

            if(order != null) {
                // Add the output to the buffer.
                destinationBuffer.addReadyStock(order);

                OutputWriter.logWork(name, order.getName(), started, SimulatedDate.getDate());

                order = null;
            }

            //Start work on another job.
            setStockWorkInProgress();
        }
    }

    public boolean isFree(){return (order == null);}
}
