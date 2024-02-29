package org.example.data_models;

import org.example.business_logic.SimulationManager;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {

    ArrayBlockingQueue<Task> tasks;
    AtomicInteger waitingPeriod;
    private int servedClients;
    private int serviceTime;

    private SimulationManager simulationManager;

    public Server(int maxTasksPerServer, SimulationManager simulationManager1) {
        this.tasks = new ArrayBlockingQueue<Task>(maxTasksPerServer);
        this.waitingPeriod = new AtomicInteger(0);
        this.servedClients = 0;
        this.serviceTime = 0;
        this.simulationManager = simulationManager1;
    }


    public void addTask(Task newTask) {
        tasks.add(newTask);
        waitingPeriod.getAndAdd(newTask.getServiceTime().get());
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }


    public ArrayBlockingQueue<Task> getTasks() {
        return tasks;
    }

    public int getServedClients() {
        return servedClients;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    @Override
    public String toString() {
        if (tasks.peek().getServiceTime().get() == 0) {
            tasks.remove();
        }
        String string = "";
        Task[] tasks = this.tasks.toArray(new Task[this.tasks.size()]);
        for (int j = 0; j < tasks.length; j++) {
            string = string + tasks[j].toString();
        }
        return string;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (!tasks.isEmpty()) {
                    Task task = tasks.peek();
                    Thread.currentThread().sleep(1000 * task.getInitialServiceTime());
                    if (tasks.peek().getServiceTime().get() == 0) {
                        tasks.remove();
                    }
                    if (simulationManager.getCurrentTime() <= simulationManager.getTimeLimit()) {
                        serviceTime += task.getInitialServiceTime();
                        servedClients++;
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
