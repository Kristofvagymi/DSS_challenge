package main;

import interfaces.Tickable;

public class Machine implements Tickable {
    private Buffer sourceBuffer;
    private Buffer destinationBuffer;

    private int timeLeft = 0;

    private String stockId;

    private int kidBikeTime;
    private int teenBikeTime;
    private int adultBikeTime;

    public void tick() {

    }
}
