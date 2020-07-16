package manufacture;

import interfaces.Tickable;
import lombok.Getter;
import main.Order;
import main.Stock;

@Getter
public class Pipeline implements Tickable {

    private Buffer cutToBendingBuffer = new Buffer();
    private Buffer bendingToWelding = new Buffer();
    private Buffer weldingToTesting = new Buffer();
    private Buffer testingToPainting = new Buffer();
    private Buffer paintingToPacking = new Buffer();
    private Buffer endBuffer = new Buffer();

    private ManufactureStep cutter;
    private ManufactureStep bending;
    private ManufactureStep welding;
    private ManufactureStep testing;
    private ManufactureStep painting;
    private ManufactureStep packing;

    public Pipeline(Buffer startBuffer, Order order){
        Stock cutToBendStock = new Stock(0, 5, order);
        cutToBendingBuffer.addStock(cutToBendStock);
        cutter = new ManufactureStep(6, 5, 8, 6, startBuffer, cutToBendingBuffer);

        Stock benToWeldStock = new Stock(0, 5, order);
        bendingToWelding.addStock(benToWeldStock);
        bending = new ManufactureStep(2, 10, 16, 15, cutToBendingBuffer, bendingToWelding);

        Stock weldingToTestingStock = new Stock(0, 5, order);
        weldingToTesting.addStock(weldingToTestingStock);
        welding = new ManufactureStep(3, 8, 12, 10, bendingToWelding, weldingToTesting);

        Stock testingToPaintingStock = new Stock(0, 5, order);
        testingToPainting.addStock(testingToPaintingStock);
        testing = new ManufactureStep(3, 5, 5, 5, weldingToTesting, testingToPainting);

        Stock paintingToPackingStock = new Stock(0, 5, order);
        paintingToPacking.addStock(paintingToPackingStock);
        painting = new ManufactureStep(3, 12, 20, 15, testingToPainting, paintingToPacking);

        Stock endStock = new Stock(0, 5, order);
        endBuffer.addStock(endStock);
        packing = new ManufactureStep(3, 10, 15, 12, paintingToPacking, endBuffer);
    }

    @Override
    public void tick() {
        cutter.tick();
        bending.tick();
        welding.tick();
        testing.tick();
        painting.tick();
        packing.tick();
    }
}
