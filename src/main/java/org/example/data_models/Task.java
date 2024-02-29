package org.example.data_models;

import java.util.concurrent.atomic.AtomicInteger;

public class Task {

    private int ID;
    private int arrivalTime;
    private AtomicInteger serviceTime;
    private int initialServiceTime;

    public Task(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = new AtomicInteger(serviceTime);
        this.initialServiceTime = serviceTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getInitialServiceTime() {
        return initialServiceTime;
    }

    public AtomicInteger getServiceTime() {
        return serviceTime;
    }

    public String toString() {
        String string = " (" + this.ID + "; " + this.arrivalTime + "; " + this.serviceTime + ")";
        return string;
    }
}
