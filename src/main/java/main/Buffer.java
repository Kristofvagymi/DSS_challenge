package main;

import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;

@Data
public class Buffer {
    private ArrayList<Stock> stocksInBuffer = new ArrayList<>();

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

    public void addReadyStock(String stockId) {
        for (int i = 0; i < stocksInBuffer.size(); i++) {
            if(stocksInBuffer.get(i).getStockId().equals(stockId)) {
                Stock arrivedStock = stocksInBuffer.get(i);
                arrivedStock.setCount(arrivedStock.getCount() + 1);
            }
        }
    }
}
