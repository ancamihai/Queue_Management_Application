package org.example.business_logic;

import org.example.data_models.Server;
import org.example.data_models.Task;
import org.example.gui.SimulationView;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationManager implements Runnable {
    public int timeLimit;
    public int maxProcessingTime;
    public int minProcessingTime;
    public int maxArrivalTime;
    public int minArrivalTime;
    public int numberOfServers;
    public int numberOfClients;

    private LinkedList<Task> generatedTasks;

    private Scheduler scheduler;

    private SimulationView simulationView;

    private AtomicInteger currentTime;
    private volatile int empty;
    private volatile int empty1;
    private int peakHour;
    private int maxClients;
    private AtomicInteger servedClients;
    private AtomicInteger serviceTime;
    private int waitingTime;

    public SimulationManager(int timeLimit, int maxProcessingTime, int minProcessingTime, int maxArrivalTime, int minArrivalTime, int numberOfServers, int numberOfClients, Strategy.SelectionPolicy selectionPolicy) {
        this.timeLimit = timeLimit;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minArrivalTime = minArrivalTime;
        this.numberOfServers = numberOfServers;
        this.numberOfClients = numberOfClients;
        scheduler = new Scheduler(numberOfServers, numberOfClients, this);
        scheduler.changeStrategy(selectionPolicy);
        this.generatedTasks = new LinkedList<Task>();
        generateRandomTasks();
        this.simulationView = new SimulationView();
        this.currentTime = new AtomicInteger(0);
        this.simulationView.setTextArea(currentTime.get(), generatedTasks, scheduler.getServers());
        this.empty = 0;
        this.empty1 = 0;
        this.peakHour = 0;
        this.maxClients = 0;
        this.servedClients = new AtomicInteger(0);
        this.serviceTime = new AtomicInteger(0);
        this.waitingTime = 0;
    }

    private void generateRandomTasks() {
        for (int i = 1; i <= numberOfClients; i++) {
            int arrivalTime = (int) (Math.random() * (maxArrivalTime + 1 - minArrivalTime)) + minArrivalTime;
            int processingTime = (int) (Math.random() * (maxProcessingTime + 1 - minProcessingTime)) + minProcessingTime;
            Task task = new Task(i, arrivalTime, processingTime);
            generatedTasks.add(task);
        }
        java.util.Comparator<Task> timeComparator = (c1, c2) -> (int) (c1.getArrivalTime() - c2.getArrivalTime());
        generatedTasks.sort(timeComparator);
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public int getCurrentTime() {
        return currentTime.get();
    }

    @Override
    public void run() {
        try {
            FileWriter fileWriter = new FileWriter("outputFile.txt");
            BufferedWriter writer = new BufferedWriter(fileWriter);
            while (currentTime.get() <= timeLimit && (empty == 0 || empty1 == 0)) {
                int clients = 0;
                empty=1;
                empty1=1;
                if (!generatedTasks.isEmpty()) {
                    scheduling();
                }
                writeWaitingClients(writer);
                clients = writeQueues(writer, clients);
                if (clients > maxClients) {
                    peakHour = currentTime.get();
                    maxClients = clients;
                }
                if (currentTime.get() < timeLimit && (empty == 0 || empty1 == 0)) {
                    this.simulationView.setTextArea(currentTime.get(), generatedTasks, scheduler.getServers());
                    updateQueues();
                } else {
                    writeStatistics(writer);
                }
                currentTime.getAndIncrement();
                Thread.sleep(1000);
            }
            writer.close();
            fileWriter.close();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void scheduling() {
        empty = 0;
        Iterator<Task> iterator = generatedTasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getArrivalTime() == currentTime.get()) {
                iterator.remove();
                scheduler.dispatchTask(task);
            }
        }
    }

    private void writeWaitingClients(BufferedWriter writer) {
        try {
            writer.write("Time:" + currentTime + "\n");
            String string = "";
            for (int i = 0; i < generatedTasks.size(); i++) {
                string = string + generatedTasks.get(i).toString();
            }
            writer.write("Waiting clients:" + string + "\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private int writeQueues(BufferedWriter writer, int clients) {
        try {
            int currentAvgWaitingTime = 0;
            LinkedList<Server> servers = scheduler.getServers();
            for (int i = 0; i < servers.size(); i++) {
                currentAvgWaitingTime += servers.get(i).getWaitingPeriod().get();
                if (servers.get(i).getTasks().isEmpty()) {
                    writer.write("Queue " + (i + 1) + ": blocked" + "\n");
                } else {
                    empty1 = 0;
                    writer.write("Queue " + (i + 1) + ": " + servers.get(i).toString() + "\n");
                    clients += servers.get(i).getTasks().size();
                }
            }
            waitingTime += (currentAvgWaitingTime / servers.size());
            writer.write("\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return clients;
    }

    private void updateQueues() {
        LinkedList<Server> servers = scheduler.getServers();
        for (int i = 0; i < servers.size(); i++) {
            if (!servers.get(i).getTasks().isEmpty()) {
                servers.get(i).getTasks().peek().getServiceTime().getAndDecrement();
                servers.get(i).getWaitingPeriod().getAndDecrement();
            }
        }
    }

    private void writeStatistics(BufferedWriter writer) {
        try {
            writer.write("Peak Hour: " + peakHour + "\n");
            LinkedList<Server> servers = scheduler.getServers();
            for (int i = 0; i < servers.size(); i++) {
                servedClients.getAndAdd(servers.get(i).getServedClients());
                serviceTime.getAndAdd(servers.get(i).getServiceTime());
            }
            if (servedClients.get() == 0) {
                writer.write("Average service time: " + 0 + "\n");
                writer.write("Average waiting time: " + 0 + "\n");
            } else {
                writer.write("Average service time: " + (float) serviceTime.get() / servedClients.get() + "\n");
                writer.write("Average waiting time: " + (float) waitingTime / currentTime.get() + "\n");
            }
            simulationView.setTextArea(currentTime.get(), generatedTasks, scheduler.getServers(), serviceTime.get(), waitingTime, servedClients.get(), peakHour);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

