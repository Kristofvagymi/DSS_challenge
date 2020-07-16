package main;

import interfaces.Tickable;
import lombok.ToString;

import java.util.ArrayList;

@ToString
public class ManufactureStep implements Tickable {

    private ArrayList<Machine> machines = new ArrayList<>();
    private int numOfMachines;

    public ManufactureStep(int numOfMachines, int kidBikeTime, int adultBikeTime, int teenBikeTime,  Buffer sourceBuffer, Buffer destinationBuffer){
        this.numOfMachines = numOfMachines;
        for (int i = 0; i < numOfMachines; i++){
            machines.add(new Machine(sourceBuffer, destinationBuffer, 0, null, kidBikeTime, teenBikeTime, adultBikeTime));
        }
    }

    @Override
    public void tick() {
        for (int i = 0; i < numOfMachines; i++){
            machines.get(i).tick();
        }
    }
}
