package ui.tabs;

import model.Condition;
import model.Drug;
import ui.GUI;

import javax.swing.*;
import java.awt.*;

// referenced SmartHome application from CPSC210
public class DatabaseTab extends Tab {

    private static final String INIT_QUESTION = "Selection an option:";
    private JLabel prompt;
    JButton b1;
    JButton b2;
    JButton b3;
    JButton b4;
    JButton b5;
    JButton b6;

    //EFFECTS: constructs a Patient tab for console with buttons and a question
    public DatabaseTab(GUI controller) {
        super(controller);
        setLayout(new GridLayout(3, 1));

        b1 = new JButton("View Conditions");
        b2 = new JButton("View Drugs");
        b3 = new JButton("View Side Effects");
        b4 = new JButton("Add new Condition");
        b5 = new JButton("Add new Drug");
        b6 = new JButton("Add new Side Effect");

        placeQuestion();
        placeButtons();
    }

    //EFFECTS: creates question at top of console
    private void placeQuestion() {
        prompt = new JLabel(INIT_QUESTION, JLabel.CENTER);
        prompt.setSize(WIDTH, HEIGHT / 3);
        this.add(prompt);
    }

    //MODIFIES: this
    //EFFECTS: places Search and Add buttons that performs associated actions when clicked
    private void placeButtons() {
        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.add(b2);
        buttonRow.add(b3);
        buttonRow.setSize(WIDTH, HEIGHT / 6);

        JPanel buttonRow2 = formatButtonRow(b4);
        buttonRow2.add(b5);
        buttonRow2.add(b6);
        buttonRow.setSize(WIDTH, HEIGHT / 6);

        b1.addActionListener(e -> {
            viewConditions();
        });

        b2.addActionListener(e -> {
            viewDrugs();
        });

        b3.addActionListener(e -> {

        });

        b4.addActionListener(e -> {

        });

        b5.addActionListener(e -> {

        });

        b6.addActionListener(e -> {

        });

        this.add(buttonRow);
        this.add(buttonRow2);
    }

    private void viewConditions() {
        StringBuilder conditionNames = new StringBuilder("Conditions:");
        for (Condition condition : getController().getPrescribingTool().getConditions()) {
            conditionNames.append("\n" + condition.getName());
        }
        JOptionPane.showMessageDialog(this, conditionNames,
                "View Conditions", JOptionPane.PLAIN_MESSAGE);
    }

    private void viewDrugs() {
        String[] conditions = getConditionNamesList().toArray(new String[0]);
        JComboBox conditionOptions = new JComboBox(conditions);
        JOptionPane.showMessageDialog(this, conditionOptions,
                "Select a condition to its view drug list",
                JOptionPane.PLAIN_MESSAGE);
        String selection = (String) conditionOptions.getSelectedItem();
        Condition selCond = findCondition(selection);

        StringBuilder drugNames = new StringBuilder("Drug List:");
        for (Drug drug : selCond.getDrugs()) {
            drugNames.append("\n" + drug.getName());
        }
        JOptionPane.showMessageDialog(this, drugNames,
                "Drug List", JOptionPane.PLAIN_MESSAGE);
    }
}
