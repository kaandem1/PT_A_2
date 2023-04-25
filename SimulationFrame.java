package view;

import businessLogic.Scheduler;
import businessLogic.SimulationManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SimulationFrame extends JFrame {
    private JPanel panel1;
    private JLabel title;
    private JLabel simTime;
    private JLabel minTime;
    private JLabel nbQ;
    private JLabel nbClients;
    private JTextField textClients;
    private JTextField textQ;
    private JTextField minIntervalArrival;
    private JTextField textSimT;
    private JTextField textMaxT;
    private JTextField minServTime;
    private JTextField maxServTime;

    private JLabel minServLabel;
    private JLabel maxServLabel;
    private JLabel maxArrTimeLabel;
    private JButton startButton;

    public SimulationFrame(){
        panel1.setVisible(true);
        panel1.setSize(300,300);
    }

    public void addStartListener(ActionListener action){startButton.addActionListener(action);}

    public int  getTextClients() {
        return Integer.parseInt(textClients.getText());
    }

    public void setTextClients(int textClients) {
        this.textClients.setText(String.valueOf(textClients));
    }

    public int getTextQ() {
        return Integer.parseInt(textQ.getText());
    }

    public void setTextQ(int textQ) {
        this.textQ.setText(String.valueOf(textQ));
    }

    public int getMinIntervalArrival() {
        return  Integer.parseInt(minIntervalArrival.getText());
    }

    public void setMinIntervalArrival(int minIntervalArrival) {
        this.minIntervalArrival.setText(String.valueOf(minIntervalArrival));
    }

    public int getTextSimT() {
        return Integer.parseInt(simTime.getText());
    }

    public void setTextSimT(int textSimT) {
        this.textSimT.setText(String.valueOf(minIntervalArrival));
    }

    public int getTextMaxT() {
        return Integer.parseInt(textMaxT.getText());
    }

    public void setTextMaxT(int textMaxT) {
        this.textMaxT.setText(String.valueOf(textMaxT));
    }

    public int getMinServTime() {
        return Integer.parseInt(minServTime.getText());
    }

    public void setMinServTime(int minServTime) {
        this.minServTime.setText(String.valueOf(minServTime));
    }

    public int getMaxServTime() {
        return Integer.parseInt(maxServTime.getText());
    }

    public void setMaxServTime(int maxServTime) {
        this.maxServTime.setText(String.valueOf(maxServTime));
    }

}
