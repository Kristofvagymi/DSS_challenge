package manufacture;

import interfaces.Tickable;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Collects the same type of machines.
 */
@ToString
public class ManufactureStep implements Tickable {

    private ArrayList<Machine> machines = new ArrayList<>();
    private int numOfMachines;
    private ManufactureStep nextStep;

    public ManufactureStep(String name, int numOfMachines, int kidBikeTime, int adultBikeTime, int teenBikeTime, Buffer sourceBuffer, Buffer destinationBuffer, ManufactureStep nextStep){
        this.nextStep = nextStep;
        this.numOfMachines = numOfMachines;
        for (int i = 0; i < numOfMachines; i++){
            machines.add(new Machine(sourceBuffer, destinationBuffer, kidBikeTime, teenBikeTime, adultBikeTime, name + "-" + (i+1)));
        }
    }

    @Override
    public void tick() {
        // Orders the machines by how important is what they are doing.
        Collections.sort(machines, Comparator.comparingInt(Machine::getPriority));

        for (int i = 0; i < numOfMachines; i++){
            // Tick every machine.
            machines.get(i).tick();
        }
        if(nextStep != null) {
            nextStep.tick();
        }
    }

    public int getFreeMachines(){
        int freeMachines = 0;
        for(Machine machine : machines){
            if (machine.isFree())
                freeMachines++;
        }
        return freeMachines;
    }
}
