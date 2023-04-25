package model;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private BlockingQueue<Task> tasks;

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    private AtomicInteger waitingPeriod;

    public Server() {
        tasks = new LinkedBlockingQueue<Task>();
        waitingPeriod = new AtomicInteger(0);
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
        waitingPeriod.incrementAndGet();
    }


    public void run() {
        while (true) {
            try {
                // Take the next task from the queue
                Task nextTask = tasks.take();
                // Increment the waiting period
                waitingPeriod.incrementAndGet();
                // Simulate the processing of the task
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Handle interrupted exception
                e.printStackTrace();
            }
        }
    }


    public Task[] getTasks() {
        // Create a new array of Tasks with the same size as the current tasks queue
        Task[] taskArray = new Task[tasks.size()];

        // Copy the tasks from the queue to the array
        Iterator<Task> iterator = tasks.iterator();
        for (int i = 0; i < taskArray.length && iterator.hasNext(); i++) {
            taskArray[i] = iterator.next();
        }

        return taskArray;
    }

}