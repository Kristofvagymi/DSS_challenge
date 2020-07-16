package manufacture;

import enums.BikeType;
import interfaces.Tickable;
import lombok.Getter;
import main.Order;
import main.Stock;

import java.time.LocalDateTime;

@Getter
public class Pipeline implements Tickable {

    private Buffer cutToBending = new Buffer();
    private ManufactureStep cutter;

    public Pipeline(Buffer startBuffer, Order order){
        Stock endStock = new Stock(0, 5, order);
        cutToBending.addStock(endStock);

        cutter = new ManufactureStep(6, 5, 8, 6, startBuffer, cutToBending);
    }

    @Override
    public void tick() {
        cutter.tick();
    }
}
