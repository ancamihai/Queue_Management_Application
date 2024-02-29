package org.example.business_logic;

import org.example.data_models.Server;
import org.example.data_models.Task;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public interface Strategy {

    public void addTask(LinkedList<Server> servers, Task t);

    public class TimeStrategy implements Strategy {
        @Override
        public void addTask(LinkedList<Server> servers, Task t) {
            AtomicInteger minWaitingPeriod = new AtomicInteger(servers.get(0).getWaitingPeriod().get());
            int index = 0;
            for (int i = 1; i < servers.size(); i++) {
                if (minWaitingPeriod.get() > servers.get(i).getWaitingPeriod().get()) {
                    minWaitingPeriod.set(servers.get(i).getWaitingPeriod().get());
                    index = i;
                }
            }
            servers.get(index).addTask(t);
        }
    }

    public class QueueStrategy implements Strategy {
        @Override
        public void addTask(LinkedList<Server> servers, Task t) {
            int index = 0;
            int queue = servers.get(0).getTasks().size();
            for (int i = 1; i < servers.size(); i++) {
                if (queue > servers.get(i).getTasks().size()) {
                    queue = servers.get(i).getTasks().size();
                    index = i;
                }
            }
            servers.get(index).addTask(t);
        }
    }

    public enum SelectionPolicy {
        SHORTEST_QUEUE, SHORTEST_TIME
    }

}
