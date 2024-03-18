package ui.tabs;

import model.Condition;
import model.Drug;
import ui.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

    // EFFECTS: returns the names of the conditions in the condition list
    protected List<String> getConditionNamesList() {
        List<String> names = new ArrayList<>();
        for (Condition condition : getController().getPrescribingTool().getConditions()) {
            names.add(condition.getName());
        }
        return names;
    }

    // EFFECTS: returns a condition in condition list that matches given name
    //          returns null if not found
    protected Condition findCondition(String name) {
        for (Condition condition : getController().getPrescribingTool().getConditions()) {
            if (condition.getName().equals(name)) {
                return condition;
            }
        }
        return null;
    }

    // EFFECTS: prints out the names of the drugs
    //          used for treatment for the given condition
    protected List<String> getDrugsNamesList(Condition condition) {
        List<String> names = new ArrayList<>();
        for (Drug drug : condition.getDrugs()) {
            names.add(drug.getName());
        }
        return names;
    }
}
