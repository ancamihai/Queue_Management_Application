package org.example.business_logic;

import org.example.data_models.Server;
import org.example.data_models.Task;

import java.util.LinkedList;

public class Scheduler {
    private LinkedList<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer, SimulationManager simulationManager1) {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        this.servers = new LinkedList<Server>();
        for(int i=0; i<maxNoServers; i++){
            Server server=new Server(maxTasksPerServer,simulationManager1);
            servers.add(server);
            Thread s=new Thread(server);
            s.start();
        }
    }

    public void changeStrategy(Strategy.SelectionPolicy policy) {
        if (policy == Strategy.SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new Strategy.QueueStrategy();
        }

        if (policy == Strategy.SelectionPolicy.SHORTEST_TIME) {
            strategy = new Strategy.TimeStrategy();
        }
    }

    public void dispatchTask(Task t) {
        strategy.addTask(this.servers, t);
    }

    public LinkedList<Server> getServers() {
        return servers;
    }
}
