package org.example.gui;

import org.example.data_models.Server;
import org.example.data_models.Task;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class SimulationView extends JFrame {

    private JTextArea textArea;

    public SimulationView() {
        this.prepareFrame();
        this.textArea = new JTextArea();
        this.textArea.setFont(new Font("Tahoma", Font.PLAIN, 15));
        this.textArea.setBounds(25, 80, 750, 550);
        this.textArea.setBackground(new Color(0xac, 0x86, 0xa8));
        this.getContentPane().add(textArea);
        this.setVisible(true);
    }

    private void prepareFrame() {
        this.setBounds(700, 100, 800, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(0xac, 0x86, 0xa8));
        this.getContentPane().setLayout(null);
        this.setResizable(false);


        JLabel titleLabel = new JLabel("Simulation");
        titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        titleLabel.setBounds(350, 10, 350, 40);
        this.getContentPane().add(titleLabel);

    }

    public void setTextArea(int currentTime, LinkedList<Task> generatedTasks, LinkedList<Server> servers) {
        String string = "Waiting clients: ";
        for (int i = 0; i < generatedTasks.size(); i++) {
            string = string + generatedTasks.get(i).toString();
        }
        string = string + "\n";
        for (int i = 0; i < servers.size(); i++) {
            string = string + "Queue " + (i + 1) + ": ";
            if (servers.get(i).getTasks().isEmpty()) {
                string = string + "blocked" + "\n";
            } else {
                string = string + servers.get(i).toString() + "\n";
            }
        }
        string = string + "\n" + "\n";
        string = string + "Current time: " + currentTime;
        this.textArea.setText(string);
    }

    public void setTextArea(int currentTime, LinkedList<Task> generatedTasks, LinkedList<Server> servers, int serviceTime, int waitingTime, int servedClients, int peakHour) {
        String string = "Waiting clients: ";
        for (int i = 0; i < generatedTasks.size(); i++) {
            string = string + generatedTasks.get(i).toString();
        }
        string = string + "\n";
        for (int i = 0; i < servers.size(); i++) {
            string = string + "Queue " + (i + 1) + ": ";
            if (servers.get(i).getTasks().isEmpty()) {
                string = string + "blocked" + "\n";
            } else {
                string = string + servers.get(i).toString() + "\n";
            }
        }
        string = string + "\n" + "\n";
        string = string + "Current time: " + currentTime + "\n";
        string = string + "Peak hour: " + peakHour + "\n";
        if (servedClients == 0) {
            string = string + "Average service time: " + 0 + "\n";
            string = string + "Average waiting time: " + 0;
        } else {
            string = string + "Average service time: " + (float) serviceTime / servedClients + "\n";
            string = string + "Average waiting time: " + (float) waitingTime / currentTime;
        }
        this.textArea.setText(string);
    }

}
