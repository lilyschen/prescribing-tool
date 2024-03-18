package ui.tabs;

import model.Condition;
import model.Drug;
import ui.GUI;

import javax.swing.*;
import java.awt.*;

// referenced SmartHome application from CPSC210
public class DatabaseTab extends Tab {

    private static final String INIT_QUESTION = "Select an option:";
    private JLabel prompt;
    JButton b1;
    JButton b2;
    JButton b3;
    JButton b4;
    JButton b5;
    JButton b6;
    JPanel buttonRow;
    JPanel buttonRow2;

    //EFFECTS: constructs a Database tab for console with buttons and a question
    public DatabaseTab(GUI controller) {
        super(controller);
        setLayout(new GridLayout(3, 1));

        b1 = new JButton("View Conditions");
        b2 = new JButton("View Drugs");
        b3 = new JButton("View Side Effects");
        b4 = new JButton("Add new Condition");
        b5 = new JButton("Add new Drug");
        b6 = new JButton("Add new Side Effect");

        buttonRow = formatButtonRow(b1);
        buttonRow.add(b2);
        buttonRow.add(b3);
        buttonRow.setSize(WIDTH, HEIGHT / 6);

        buttonRow2 = formatButtonRow(b4);
        buttonRow2.add(b5);
        buttonRow2.add(b6);
        buttonRow.setSize(WIDTH, HEIGHT / 6);

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
    //EFFECTS: places buttons that performs associated actions when clicked
    private void placeButtons() {

        b1.addActionListener(e -> {
            viewConditions();
        });

        b2.addActionListener(e -> {
            viewDrugs();
        });

        b3.addActionListener(e -> {
            viewSideEffects();
        });

        b4.addActionListener(e -> {
            addCondition();
        });

        b5.addActionListener(e -> {
            addDrug();
        });

        b6.addActionListener(e -> {
            addSideEffect();
        });

        this.add(buttonRow);
        this.add(buttonRow2);
    }

    //EFFECTS: displays all conditions names
    private void viewConditions() {
        StringBuilder conditionNames = new StringBuilder("Conditions:");
        for (Condition condition : getController().getPrescribingTool().getConditions()) {
            conditionNames.append("\n" + condition.getName());
        }
        JOptionPane.showMessageDialog(this, conditionNames,
                "View Conditions", JOptionPane.PLAIN_MESSAGE);
    }

    //EFFECTS: lets user select a condition and displays all drug names for
    //         the selected condition
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

    // EFFECTS: lets user select a drug and displays all side effects of the
    //          selected drug
    private void viewSideEffects() {
        String[] conditions = getConditionNamesList().toArray(new String[0]);
        JComboBox conditionOptions = new JComboBox(conditions);
        JOptionPane.showMessageDialog(this, conditionOptions,
                "Select a condition to its view drug list",
                JOptionPane.PLAIN_MESSAGE);
        String selection = (String) conditionOptions.getSelectedItem();
        Condition selCond = findCondition(selection);

        String[] drugs = getDrugsNamesList(selCond).toArray(new String[0]);
        JComboBox drugOptions = new JComboBox(drugs);
        JOptionPane.showMessageDialog(this, drugOptions,
                "Select a drug to view its side effects",
                JOptionPane.PLAIN_MESSAGE);
        String chosen = (String) drugOptions.getSelectedItem();
        Drug selDrug = selCond.findDrug(chosen);

        StringBuilder sideEffects = new StringBuilder("Side effects for " + chosen + ":");
        for (String se : selDrug.getSideEffects()) {
            sideEffects.append("\n" + se);
        }
        JOptionPane.showMessageDialog(this, sideEffects,
                "View side effects - " + chosen, JOptionPane.PLAIN_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: processes user commands to allow user to add a new condition
    //          to the database
    private void addCondition() {
        String name = JOptionPane.showInputDialog("Enter the condition name.");
        name = name.toLowerCase();
        Condition newCond = new Condition(name);
        getController().getPrescribingTool().addCondition(newCond);

        int selection = JOptionPane.showOptionDialog(this,
                "Would you like to add a drug to this condition?", "Condition",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, null, 0);

        if (selection == 0) {
            addDrugToCondition(newCond);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user commands to allow user to select a condition
    //          and add a drug to it
    private void addDrug() {
        String[] conditions = getConditionNamesList().toArray(new String[0]);
        JComboBox conditionOptions = new JComboBox(conditions);
        JOptionPane.showMessageDialog(this, conditionOptions,
                "Select a condition to add a new drug to",
                JOptionPane.PLAIN_MESSAGE);
        String selection = (String) conditionOptions.getSelectedItem();
        Condition selCond = findCondition(selection);

        addDrugToCondition(selCond);
    }

    // MODIFIES: this
    // EFFECTS: processes user commands to allow user to select a drug from
    //          a condition and add side effect to it
    private void addSideEffect() {
        String[] conditions = getConditionNamesList().toArray(new String[0]);
        JComboBox conditionOptions = new JComboBox(conditions);
        JOptionPane.showMessageDialog(this, conditionOptions,
                "Select a condition to its view drug list",
                JOptionPane.PLAIN_MESSAGE);
        String selection = (String) conditionOptions.getSelectedItem();
        Condition selCond = findCondition(selection);

        String[] drugs = getDrugsNamesList(selCond).toArray(new String[0]);
        JComboBox drugOptions = new JComboBox(drugs);
        JOptionPane.showMessageDialog(this, drugOptions,
                "Select a drug to add a side effect to",
                JOptionPane.PLAIN_MESSAGE);
        String chosen = (String) drugOptions.getSelectedItem();
        Drug selDrug = selCond.findDrug(chosen);

        addSideEffectToDrug(selDrug);
    }

    // MODIFIES: this
    // EFFECTS: allows user to add a drug and its side effect to the given condition
    private void addDrugToCondition(Condition condition) {
        String name = JOptionPane.showInputDialog("Enter the drug name.");
        name = name.toLowerCase();
        Drug newDrug = new Drug(name);
        condition.addDrug(newDrug);
        String message = name + " has been added successfully to " + condition.getName();
        JOptionPane.showMessageDialog(this, message, "Success!", JOptionPane.PLAIN_MESSAGE);

        int selection = JOptionPane.showOptionDialog(this,
                "Would you like to add a side effect to " + name + "?", "Drug: " + name,
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, null, 0);

        if (selection == 0) {
            addSideEffectToDrug(newDrug);
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to add a side effect to the given drug
    private void addSideEffectToDrug(Drug drug) {
        String sideEffect = JOptionPane.showInputDialog("Enter the side effect you would like to add");
        sideEffect = sideEffect.toLowerCase();
        drug.addSideEffect(sideEffect);
        String message = sideEffect + " has been added successfully to " + drug.getName();
        JOptionPane.showMessageDialog(this, message, "Success!", JOptionPane.PLAIN_MESSAGE);
    }

}
