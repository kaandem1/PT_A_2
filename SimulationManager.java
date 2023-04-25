package businessLogic;

import model.Server;
import model.Task;
import view.SimulationFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import java.util.List;

public class SimulationManager implements Runnable {
    //data read from UI
    public int timeLimit ;//maximum processing time - read from UI
    public int maxServiceTime ;
    public int minServiceTime ;
    public int numberOfServers;
    public int numberOfClients;
    public int maxArrivalTime;
    public int minArrivalTime;


    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;

    //entity responsible with queue management and client distribution
    private Scheduler scheduler;
    //frame for displaying simulation
    private SimulationFrame frame;
    //pool of tasks (client shopping in the store)
    private List<Task> generatedTasks;

    public SimulationManager() {
        frame = new SimulationFrame();
        numberOfClients = frame.getTextClients();
        numberOfServers = frame.getTextQ();
        //initialize the scheduler
        scheduler = new Scheduler(numberOfServers,numberOfClients);
        //initialize selection strategy
        scheduler.changeStrategy(selectionPolicy);
        //generate numberOfClients clients using generateNRandomTasks()
        //and store them to generatedTasks
        generatedTasks = new ArrayList<Task>();
        generateNRandomTasks(numberOfClients);
        //=>create and start numberOfServers threads
        for(int i=0; i<numberOfServers;i++){
            Thread thread = new Thread();
            thread.start();
        }
        //initialize frame to display simulation
        frame.setVisible(true);

    }

    public void generateNRandomTasks(int numberOfClients){
        frame = new SimulationFrame();
        //n, min max arrival, min max serv
        numberOfClients = frame.getTextClients();
        minArrivalTime = frame.getMinIntervalArrival();
        maxArrivalTime = frame.getTextMaxT();
        minServiceTime = frame.getMinServTime();
        maxServiceTime = frame.getMaxServTime();
//        if (textClients.getText().isEmpty() || textQ.getText().isEmpty()
//                || minIntervalArrival.getText().isEmpty() || textSimT.getText().isEmpty()
//                || textMaxT.getText().isEmpty() || minServTime.getText().isEmpty()
//                || maxServTime.getText().isEmpty()) {
//            JOptionPane.showMessageDialog(SimulationFrame.this, "Please fill in all fields",
//                    "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
        if (minServiceTime > maxServiceTime || minArrivalTime > maxArrivalTime) {
            throw new IllegalArgumentException("Invalid input: minimum value cannot be greater than maximum value.");
        }else{
            //minServiceTime < serviceTime < maxServiceTime
            //random arrivalTime
            //generate N random tasks;
            //-random service time
            int randArrival = (int) (Math.random() * (maxArrivalTime - minArrivalTime + 1)) + minArrivalTime;
            int randService = (int) (Math.random() * (maxServiceTime - minServiceTime + 1)) + minServiceTime;
            for(int i =1 ; i <= numberOfClients; i++){
                generatedTasks.add(new Task(i,randArrival,randService));

            }
        }
        //sort list with respect to arrivalTime
        Collections.sort(generatedTasks, new Comparator<Task>() {
            public int compare(Task t1, Task t2) {
                return t1.getArrivalTime() - t2.getArrivalTime();
            }
        });
    }

    class StartListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // Check if any text fields are left empty
            try {
                    numberOfServers = frame.getTextQ();
                    numberOfClients = frame.getTextClients();
                // sim start code here
                Scheduler scheduler = new Scheduler(numberOfServers, numberOfClients);
                for (int i = 0; i < numberOfServers; i++) {
                    Thread thread = new Thread(scheduler.getServers().get(i));
                    thread.start();
                }
                Thread thread1 = new Thread(SimulationManager.this);
                thread1.start();
            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Error","Error",JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
            }
        }
    }

    public void run() {
        int currentTime = 0;
        int servTime;

        numberOfClients = frame.getTextClients();
        numberOfServers = frame.getTextQ();
        minArrivalTime = frame.getMinIntervalArrival();
        timeLimit = frame.getTextSimT();
        maxArrivalTime = frame.getTextMaxT();
        minServiceTime = frame.getMinServTime();
        maxServiceTime = frame.getMaxServTime();
        while (currentTime < timeLimit) {
            for (Iterator<Task> iterator = generatedTasks.iterator(); iterator.hasNext();) {
                Task task = iterator.next();
                if (task.getArrivalTime() == currentTime) {
                    scheduler.dispatchTask(task);
                    //if task.getID() is equal to the id of the task at the front of the list, decrement serviceTime
                    iterator.remove();
                }
            }
            try {
                // Wait for 1 second before continuing
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentTime++;
        }
    }



}
