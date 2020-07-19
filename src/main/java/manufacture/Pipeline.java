package manufacture;

import interfaces.Tickable;
import lombok.Getter;
import main.Order;
import main.Stock;

import java.util.ArrayList;
import java.util.List;

public class Pipeline implements Tickable {

    private ManufactureStep cutter;
    private ManufactureStep bending;
    private ManufactureStep welding;
    private ManufactureStep testing;
    private ManufactureStep painting;
    private ManufactureStep packing;

    @Getter
    private Buffer startBuffer;
    @Getter
    private Buffer endBuffer = new Buffer(null);

    private List<Order> orders;

    private List<Buffer> buffers;

    private void fillBufferWithStocks(Buffer buffer) {
        for(Order order : orders){
            Stock stock = new Stock(order, 0, 0);
            buffer.addStock(stock);
        }
    }

    public Pipeline(List<Order> orders){
        this.orders = orders;

        buffers = new ArrayList<>();

        startBuffer = new Buffer(cutter);
        Buffer cutToBendingBuffer = new Buffer(bending);
        Buffer bendingToWelding = new Buffer(welding);
        Buffer weldingToTesting = new Buffer(testing);
        Buffer testingToPainting = new Buffer(painting);
        Buffer paintingToPacking = new Buffer(packing);
        endBuffer = new Buffer(null);

        for(Order order : orders){
            Stock stock = new Stock(order, order.getTotalCount(), 0);
            startBuffer.addStock(stock);
        }

        buffers.add(startBuffer);
        buffers.add(cutToBendingBuffer);
        buffers.add(bendingToWelding);
        buffers.add(weldingToTesting);
        buffers.add(testingToPainting);
        buffers.add(paintingToPacking);

        fillBufferWithStocks(endBuffer);
        packing = new ManufactureStep("Csomagoló", 3, 10, 15, 12, paintingToPacking, endBuffer, null);

        fillBufferWithStocks(paintingToPacking);
        painting = new ManufactureStep("Festő", 3, 12, 20, 15, testingToPainting, paintingToPacking, packing);

        fillBufferWithStocks(testingToPainting);
        testing = new ManufactureStep("Tesztelő", 3, 5, 5, 5, weldingToTesting, testingToPainting, painting);

        fillBufferWithStocks(weldingToTesting);
        welding = new ManufactureStep("Hegesztő", 3, 8, 12, 10, bendingToWelding, weldingToTesting, testing);

        fillBufferWithStocks(bendingToWelding);
        bending = new ManufactureStep("Hajlító", 2, 10, 16, 15, cutToBendingBuffer, bendingToWelding, welding);

        fillBufferWithStocks(cutToBendingBuffer);
        cutter = new ManufactureStep("Vágó",6, 5, 8, 6, startBuffer, cutToBendingBuffer, bending);
    }

    @Override
    public void tick() {
        cutter.tick();
    }

    private void calculatePriorityByBuffer(Buffer buffer) {
        for (Stock stock : buffer.getStocksInBuffer()){
            if(stock.getCount() > 0) stock.setPrio(1);
            else stock.setPrio(0);
        }
    }

    public void calculatePriorities() {
        for (Buffer buffer : buffers) {
            calculatePriorityByBuffer(buffer);
        }
    }
}
