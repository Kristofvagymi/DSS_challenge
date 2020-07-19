package manufacture;

import interfaces.Tickable;
import lombok.Getter;
import main.Order;
import main.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pipeline implements Tickable {

    // Manufacture steps in the factory
    private ManufactureStep cutter;
    private ManufactureStep bending;
    private ManufactureStep welding;
    private ManufactureStep testing;
    private ManufactureStep painting;
    private ManufactureStep packing;

    // Start and end buffers
    @Getter
    private Buffer startBuffer;
    @Getter
    private Buffer endBuffer;

    private List<Order> orders;

    private List<Buffer> buffers;

    private void fillBufferWithStocks(Buffer buffer) {
        for(Order order : orders){
            Stock stock = new Stock(order, 0, 0);
            buffer.addStock(stock);
        }
    }
    private final static Logger LOGGER = Logger.getLogger(Pipeline.class.getName());

    public Pipeline(List<Order> orders){
        LOGGER.log(Level.INFO, "Building the pipeline.");

        this.orders = orders;

        buffers = new ArrayList<>();

        // Init buffers.
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

        LOGGER.log(Level.INFO, "Pipeline building finished successfully.");
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

    // Update priorities
    public void calculatePriorities() {
        for (Buffer buffer : buffers) {
            calculatePriorityByBuffer(buffer);
        }
    }
}
