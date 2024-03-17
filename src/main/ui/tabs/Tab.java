package ui.tabs;

import ui.GUI;

import javax.swing.*;
import java.awt.*;

// referenced SmartHome application from CPSC210
public abstract class Tab extends JPanel {
    private final GUI controller;

    //REQUIRES: GUI controller that holds this tab
    public Tab(GUI controller) {
        this.controller = controller;
    }

    //EFFECTS: creates and returns row with button included
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    //EFFECTS: returns the GUI controller for this tab
    public GUI getController() {
        return controller;
    }
}
