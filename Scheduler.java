package businessLogic;

import model.Server;
import model.Task;

import java.util.*;

public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        servers = new ArrayList<>();

        // Create maxNoServers servers and threads
        for (int i = 0; i < maxNoServers; i++) {
            Server server = new Server();
            servers.add(server);
            Thread thread = new Thread(server);
            thread.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStrategyTime();
        }
    }

    public void dispatchTask(Task task) {
        // Call the strategy addTask method
        strategy.addTask(servers, task);
    }

    public List<Server> getServers() {
        return servers;
    }
}

interface Strategy {
    void addTask(List<Server> servers, Task task);
}

class ConcreteStrategyTime implements Strategy {
    public void addTask(List<Server> servers, Task task) {
        Server shortestQueueServer = servers.get(0);
        for (Server server : servers) {
            if (server.getWaitingPeriod().get() < shortestQueueServer.getWaitingPeriod().get()) {
                shortestQueueServer = server;
            }
        }
        shortestQueueServer.addTask(task);
    }
}


