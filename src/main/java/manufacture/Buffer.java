package manufacture;

import lombok.Data;
import main.Order;
import main.Stock;

import java.util.ArrayList;
import java.util.Comparator;

@Data
public class Buffer {
    private ArrayList<Stock> stocksInBuffer = new ArrayList<>();
    private ManufactureStep nextStep;

    public Buffer(ManufactureStep nextStep) {
        this.nextStep = nextStep;
    }

    public void addStock(Stock newStock) {
        stocksInBuffer.add(newStock);
    }

    public Stock getStockWithHighestPrio() {
        Stock stockWithMaxPrio = stocksInBuffer.stream().max(Comparator.comparing(Stock::getPrio)).get();
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

    public boolean hasCapacity() {
        if(nextStep == null) return true;
        return (nextStep.getFreeMachines() > stockSize());
    }
}
