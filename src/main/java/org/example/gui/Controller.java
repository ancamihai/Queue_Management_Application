package org.example.gui;

import org.example.business_logic.SimulationManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

public class Controller implements ActionListener {

    private SetupView setup;

    public Controller(SetupView setup) {
        this.setup = setup;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command == "START") {
            try {
                int timeLimit = parseInt(this.setup.getLimit());
                int clients = parseInt(this.setup.getClients());
                int queues = parseInt(this.setup.getQueues());
                int minArrival = parseInt(this.setup.getMinArrival());
                int maxArrival = parseInt(this.setup.getMaxArrival());
                int minService = parseInt(this.setup.getMinService());
                int maxService = parseInt(this.setup.getMaxService());
                SimulationManager sim = new SimulationManager(timeLimit, maxService, minService, maxArrival, minArrival, queues, clients, this.setup.selectionPolicy);
                Thread t = new Thread(sim);
                t.start();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(setup, "The information entered is not valid; make sure only integers have been introduced.");
            }
        }
    }
}
