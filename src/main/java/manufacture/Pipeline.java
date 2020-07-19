package manufacture;

import interfaces.Tickable;
import lombok.Getter;
import main.Order;
import main.Stock;

import java.util.List;

@Getter
public class Pipeline implements Tickable {

    private ManufactureStep cutter;
    private ManufactureStep bending;
    private ManufactureStep welding;
    private ManufactureStep testing;
    private ManufactureStep painting;
    private ManufactureStep packing;

    private Buffer startBuffer = new Buffer(cutter);
    private Buffer cutToBendingBuffer = new Buffer(bending);
    private Buffer bendingToWelding = new Buffer(welding);
    private Buffer weldingToTesting = new Buffer(testing);
    private Buffer testingToPainting = new Buffer(painting);
    private Buffer paintingToPacking = new Buffer(packing);
    private Buffer endBuffer = new Buffer(null);

    private List<Order> orders;

    private void fillBufferWithStocks(Buffer buffer) {
        for(Order order : orders){
            Stock stock = new Stock(order, 0, 0);
            buffer.addStock(stock);
        }
    }

    public Pipeline(List<Order> orders){
        this.orders = orders;

        for(Order order : orders){
            Stock stock = new Stock(order, order.getTotalCount(), 0);
            startBuffer.addStock(stock);
        }

        fillBufferWithStocks(endBuffer);
        packing = new ManufactureStep(3, 10, 15, 12, paintingToPacking, endBuffer, null);

        fillBufferWithStocks(paintingToPacking);
        painting = new ManufactureStep(3, 12, 20, 15, testingToPainting, paintingToPacking, packing);

        fillBufferWithStocks(testingToPainting);
        testing = new ManufactureStep(3, 5, 5, 5, weldingToTesting, testingToPainting, painting);

        fillBufferWithStocks(weldingToTesting);
        welding = new ManufactureStep(3, 8, 12, 10, bendingToWelding, weldingToTesting, testing);

        fillBufferWithStocks(bendingToWelding);
        bending = new ManufactureStep(2, 10, 16, 15, cutToBendingBuffer, bendingToWelding, welding);

        fillBufferWithStocks(cutToBendingBuffer);
        cutter = new ManufactureStep(6, 5, 8, 6, startBuffer, cutToBendingBuffer, bending);
    }

    @Override
    public void tick() {
        cutter.tick();
    }
}
