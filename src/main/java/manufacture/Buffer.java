package manufacture;

import lombok.Data;
import main.Order;
import main.SimulatedDate;
import main.Stock;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Buffer connects machines in the simulation of factory.
 * Instead of connecting every machine with every machine between layers which means n * m connection,
 * this solution requires only n + m connection.
 *
 * This object does not store semi - finished product it just connects the machines.
 */
@Data
public class Buffer {

    //Stock sizes
    private ArrayList<Stock> stocksInBuffer = new ArrayList<>();

    //Next step after buffer.
    private ManufactureStep nextStep;

    public Buffer(ManufactureStep nextStep) {
        this.nextStep = nextStep;
    }

    public void addStock(Stock newStock) {
        stocksInBuffer.add(newStock);
    }

    //Get the stored stocks wtih the highest prioriies
    public Stock getStockWithHighestPrio() {
        List<Stock> notEmptyStocks = stocksInBuffer.stream().filter(p -> p.getCount() > 0).collect(Collectors.toList());
        if(notEmptyStocks.isEmpty()) return null;
        Stock stockWithMaxPrio = notEmptyStocks.stream().max(Comparator.comparing(Stock::getPrio)).get();
        if(stockWithMaxPrio.getCount() == 0){
            return null;
        }
        stockWithMaxPrio.setCount(stockWithMaxPrio.getCount() - 1);
        return stockWithMaxPrio;
    }

    public void addReadyStock(Order order) {
        for (Stock stock : stocksInBuffer) {
            if(stock.getOrder().equals(order)){
                stock.setCount(stock.getCount() + 1);
            }
        }
    }

    public int stockSize() {
        int sizeCounter = 0;
        for (Stock stock : stocksInBuffer) {
            sizeCounter += stock.getCount();
        }
        return sizeCounter;
    }

    //There are enough free machines at the sink of the buffer.
    public boolean hasCapacity() {
        if(nextStep == null) return true;
        return (nextStep.getFreeMachines() > stockSize());
    }

    public boolean isFull(){
        for (Stock stock : stocksInBuffer) {
            if (stock.getCount() != stock.getOrder().getTotalCount()) {
                return false;
            } else {
                if(stock.getOrder().getEndDate() == null)
                    stock.getOrder().setEndDate(SimulatedDate.getDate());
            }
        }
        return true;
    }
}
