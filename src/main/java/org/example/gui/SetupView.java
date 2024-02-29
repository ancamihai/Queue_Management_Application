package org.example.gui;

import org.example.business_logic.Strategy;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class SetupView extends JFrame {

    private JLabel limit;
    private JTextField timeLimit;

    private JLabel clients;
    private JTextField nrOfClients;

    private JLabel queues;
    private JTextField nrOfQueues;

    private JLabel minService;
    private JTextField minServiceTime;

    private JLabel maxService;
    private JTextField maxServiceTime;

    private JLabel minArrival;
    private JTextField minArrivalTime;

    private JLabel maxArrival;
    private JTextField maxArrivalTime;

    private JLabel strategy;
    private JList strategyQueue;

    private JButton start;

    private ListSelectionListener listSelectionListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (strategyQueue.getSelectedIndex() == 0) {
                selectionPolicy = Strategy.SelectionPolicy.SHORTEST_QUEUE;
            } else {
                selectionPolicy = Strategy.SelectionPolicy.SHORTEST_TIME;
            }

        }
    };

    Controller controller = new Controller(this);

    public Strategy.SelectionPolicy selectionPolicy = Strategy.SelectionPolicy.SHORTEST_QUEUE;

    public SetupView() {
        this.prepareFrame();
        this.prepareElements();
        this.prepareMin();
        this.prepareMax();
        this.prepareSimulation();
        this.setVisible(true);
    }

    private void prepareFrame() {
        this.setBounds(100, 100, 500, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(0xac, 0x86, 0xa8));
        this.getContentPane().setLayout(null);
        this.setResizable(false);

        JLabel titleLabel = new JLabel("Queue management system");
        titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        titleLabel.setBounds(100, 10, 350, 40);
        this.getContentPane().add(titleLabel);

        start = new JButton("Start simulation");
        start.setFont(new Font("Tahoma", Font.PLAIN, 12));
        start.setBounds(150, 320, 180, 25);
        start.setActionCommand("START");
        start.addActionListener(this.controller);
        this.getContentPane().add(start);
    }

    private void prepareElements() {
        nrOfClients = new JTextField();
        nrOfClients.setBounds(140, 70, 45, 25);
        this.getContentPane().add(nrOfClients);

        nrOfQueues = new JTextField();
        nrOfQueues.setBounds(390, 70, 45, 25);
        this.getContentPane().add(nrOfQueues);

        clients = new JLabel("Nr of clients");
        clients.setFont(new Font("Tahoma", Font.PLAIN, 12));
        clients.setBounds(30, 70, 100, 25);
        this.getContentPane().add(clients);

        queues = new JLabel("Nr of queues");
        queues.setFont(new Font("Tahoma", Font.PLAIN, 12));
        queues.setBounds(280, 70, 100, 25);
        this.getContentPane().add(queues);
    }

    private void prepareMin() {
        minArrivalTime = new JTextField();
        minArrivalTime.setBounds(140, 130, 45, 25);
        this.getContentPane().add(minArrivalTime);

        minServiceTime = new JTextField();
        minServiceTime.setBounds(390, 130, 45, 25);
        this.getContentPane().add(minServiceTime);

        minArrival = new JLabel("Min arrival time");
        minArrival.setFont(new Font("Tahoma", Font.PLAIN, 12));
        minArrival.setBounds(30, 130, 100, 25);
        this.getContentPane().add(minArrival);

        minService = new JLabel("Min service time");
        minService.setFont(new Font("Tahoma", Font.PLAIN, 12));
        minService.setBounds(280, 130, 100, 25);
        this.getContentPane().add(minService);
    }

    private void prepareMax() {
        maxArrivalTime = new JTextField();
        maxArrivalTime.setBounds(140, 160, 45, 25);
        this.getContentPane().add(maxArrivalTime);

        maxServiceTime = new JTextField();
        maxServiceTime.setBounds(390, 160, 45, 25);
        this.getContentPane().add(maxServiceTime);

        maxArrival = new JLabel("Max arrival time");
        maxArrival.setFont(new Font("Tahoma", Font.PLAIN, 12));
        maxArrival.setBounds(30, 160, 100, 25);
        this.getContentPane().add(maxArrival);

        maxService = new JLabel("Max service time");
        maxService.setFont(new Font("Tahoma", Font.PLAIN, 12));
        maxService.setBounds(280, 160, 100, 25);
        this.getContentPane().add(maxService);
    }

    private void prepareSimulation() {
        timeLimit = new JTextField();
        timeLimit.setBounds(140, 220, 45, 25);
        this.getContentPane().add(timeLimit);

        limit = new JLabel("Simulation time");
        limit.setFont(new Font("Tahoma", Font.PLAIN, 12));
        limit.setBounds(30, 220, 100, 25);
        this.getContentPane().add(limit);

        strategy = new JLabel("Strategy");
        strategy.setFont(new Font("Tahoma", Font.PLAIN, 12));
        strategy.setBounds(280, 220, 100, 25);
        this.getContentPane().add(strategy);

        String[] strategyElements = {"Queue Strategy", "Time Strategy"};
        strategyQueue = new JList<>(strategyElements);
        strategyQueue.setBounds(350, 220, 100, 40);
        strategyQueue.setSelectedIndex(0);
        strategyQueue.addListSelectionListener(listSelectionListener);
        this.getContentPane().add(strategyQueue);
    }

    public String getLimit() {
        return timeLimit.getText();
    }

    public String getClients() {
        return nrOfClients.getText();
    }

    public String getQueues() {
        return nrOfQueues.getText();
    }

    public String getMinArrival() {
        return minArrivalTime.getText();
    }

    public String getMaxArrival() {
        return maxArrivalTime.getText();
    }

    public String getMinService() {
        return minServiceTime.getText();
    }

    public String getMaxService() {
        return maxServiceTime.getText();
    }

}
