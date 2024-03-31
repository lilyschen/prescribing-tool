package ui.tabs;

import model.Condition;
import model.Drug;
import ui.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// referenced SmartHome application from CPSC210
// represents Database Tab/Panel
public class DatabaseTab extends Tab {

    private static final String INIT_QUESTION = "Select an option:";
    private JLabel prompt;
    private JButton b1;
    private JButton b2;
    private JButton b3;
    private JButton b4;
    private JButton b5;
    private JPanel buttonRow1;
    private JPanel buttonRow2;

    //REQUIRES: GUI controller that holds this tab
    //EFFECTS: constructs a Database tab for console with buttons and a question
    public DatabaseTab(GUI controller) {
        super(controller);
        setLayout(new GridLayout(3, 1));

        b1 = new JButton("View Conditions");
        b2 = new JButton("Add new Condition");
        b3 = new JButton("Add new Drug");
        b4 = new JButton("Add new Side Effect");
        b5 = new JButton("View Conditions in alphabetical order");

        buttonRow1 = formatButtonRow(b1);
        buttonRow1.add(b5);
        buttonRow1.setSize(WIDTH, HEIGHT / 6);

        buttonRow2 = formatButtonRow(b2);
        buttonRow2.add(b3);
        buttonRow2.add(b4);
        buttonRow1.setSize(WIDTH, HEIGHT / 6);

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
            addCondition();
        });

        b3.addActionListener(e -> {
            addDrug();
        });

        b4.addActionListener(e -> {
            addSideEffect();
        });

        b5.addActionListener(e -> {
            viewConditionsInAlphabeticalOrder();
        });

        this.add(buttonRow1);
        this.add(buttonRow2);
    }

    //EFFECTS: displays all the conditions and allows user to click to view drugs
    private void viewConditions() {
        JPanel conditionPanel = new JPanel();
        conditionPanel.setLayout(new BoxLayout(conditionPanel, BoxLayout.Y_AXIS));

        for (Condition condition : getController().getPrescribingTool().getConditions()) {
            JLabel label = new JLabel(condition.getName());
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            label.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    viewDrugs(condition);
                }

            });
            conditionPanel.add(label);
        }

        JScrollPane scrollPane = new JScrollPane(conditionPanel);

        JOptionPane.showMessageDialog(this, scrollPane, "View Conditions",
                JOptionPane.PLAIN_MESSAGE);
    }

    //EFFECTS: displays all the conditions in alphabetical order and allows user to click to view drugs
    private void viewConditionsInAlphabeticalOrder() {
        JPanel conditionPanel = new JPanel();
        conditionPanel.setLayout(new BoxLayout(conditionPanel, BoxLayout.Y_AXIS));

        for (Condition condition : alphabeticalOrderConditionList()) {
            JLabel label = new JLabel(condition.getName());
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            label.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    viewDrugsInAlphabeticalOrder(condition);
                }

            });
            conditionPanel.add(label);
        }

        JScrollPane scrollPane = new JScrollPane(conditionPanel);

        JOptionPane.showMessageDialog(this, scrollPane, "View Conditions",
                JOptionPane.PLAIN_MESSAGE);
    }

    // EFFECTS: returns a list of conditions in alphabetical order
    private List<Condition> alphabeticalOrderConditionList() {
        List<String> conditionNames = new ArrayList<>();
        for (Condition condition : getController().getPrescribingTool().getConditions()) {
            conditionNames.add(condition.getName());
        }
        Collections.sort(conditionNames);
        List<Condition> conditions = new ArrayList<>();
        for (String conditionName : conditionNames) {
            Condition condition = findCondition(conditionName);
            if (condition != null) {
                conditions.add(condition);
            }
        }
        return conditions;
    }

    //EFFECTS: displays all the drugs for the given condition and allows user to click to view side effects
    private void viewDrugs(Condition condition) {
        JPanel drugPanel = new JPanel();
        drugPanel.setLayout(new BoxLayout(drugPanel, BoxLayout.Y_AXIS));

        for (Drug drug : condition.getDrugs()) {
            JLabel label = new JLabel(drug.getName());
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            label.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    viewSideEffects(drug);
                }

            });
            drugPanel.add(label);
        }

        JScrollPane scrollPane = new JScrollPane(drugPanel);

        JOptionPane.showMessageDialog(this, scrollPane,
                "Drug therapy for " + condition.getName() + ":",
                JOptionPane.PLAIN_MESSAGE);
    }

    //EFFECTS: displays all the drugs in alphabetical order for the given condition
    //         and allows user to click to view side effects
    private void viewDrugsInAlphabeticalOrder(Condition condition) {
        JPanel drugPanel = new JPanel();
        drugPanel.setLayout(new BoxLayout(drugPanel, BoxLayout.Y_AXIS));

        for (Drug drug : alphabeticalOrderDrugList(condition)) {
            JLabel label = new JLabel(drug.getName());
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            label.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    viewSideEffectsInAlphabeticalOrder(drug);
                }

            });
            drugPanel.add(label);
        }

        JScrollPane scrollPane = new JScrollPane(drugPanel);

        JOptionPane.showMessageDialog(this, scrollPane,
                "Drug therapy for " + condition.getName() + ":",
                JOptionPane.PLAIN_MESSAGE);
    }

    // EFFECTS: returns a drug list in alphabetical order for the given condition
    private List<Drug> alphabeticalOrderDrugList(Condition condition) {
        List<String> drugNames = new ArrayList<>();
        for (Drug drug : condition.getDrugs()) {
            drugNames.add(drug.getName());
        }
        Collections.sort(drugNames);
        List<Drug> drugs = new ArrayList<>();
        for (String drugName : drugNames) {
            Drug drug = condition.findDrug(drugName);
            if (drug != null) {
                drugs.add(drug);
            }
        }
        return drugs;
    }

    // EFFECTS: displays all side effects of the given drug
    private void viewSideEffects(Drug drug) {
        JPanel sideEffectPanel = new JPanel();
        sideEffectPanel.setLayout(new BoxLayout(sideEffectPanel, BoxLayout.Y_AXIS));

        for (String sideEffect : drug.getSideEffects()) {
            JLabel label = new JLabel(sideEffect);
            sideEffectPanel.add(label);
        }

        JScrollPane scrollPane = new JScrollPane(sideEffectPanel);

        JOptionPane.showMessageDialog(this, scrollPane, "Side effects of " + drug.getName() + ":",
                JOptionPane.PLAIN_MESSAGE);
    }

    // EFFECTS: displays all side effects of the given drug in alphabetical order
    private void viewSideEffectsInAlphabeticalOrder(Drug drug) {
        JPanel sideEffectPanel = new JPanel();
        sideEffectPanel.setLayout(new BoxLayout(sideEffectPanel, BoxLayout.Y_AXIS));

        for (String sideEffect : alphabeticalOrderSideEffectList(drug)) {
            JLabel label = new JLabel(sideEffect);
            sideEffectPanel.add(label);
        }

        JScrollPane scrollPane = new JScrollPane(sideEffectPanel);

        JOptionPane.showMessageDialog(this, scrollPane, "Side effects of " + drug.getName() + ":",
                JOptionPane.PLAIN_MESSAGE);
    }

    // EFFECTS: returns a side effect list in alphabetical order for the given drug
    private List<String> alphabeticalOrderSideEffectList(Drug drug) {
        List<String> sideEffects = new ArrayList<>();
        for (String se : drug.getSideEffects()) {
            sideEffects.add(se);
        }
        Collections.sort(sideEffects);
        return sideEffects;
    }

    // MODIFIES: this
    // EFFECTS: processes user commands to allow user to add a new condition
    //          to the database
    private void addCondition() {
        String name = JOptionPane.showInputDialog("Enter the condition name.");
        if (name != null) {
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

        if (selCond != null) {
            addDrugToCondition(selCond);
        }
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

        if (selCond != null) {
            String[] drugs = getDrugsNamesList(selCond).toArray(new String[0]);
            JComboBox drugOptions = new JComboBox(drugs);
            JOptionPane.showMessageDialog(this, drugOptions,
                    "Select a drug to add a side effect to",
                    JOptionPane.PLAIN_MESSAGE);
            String chosen = (String) drugOptions.getSelectedItem();
            Drug selDrug = selCond.findDrug(chosen);

            addSideEffectToDrug(selDrug);
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to add a drug and its side effect to the given condition
    private void addDrugToCondition(Condition condition) {
        String name = JOptionPane.showInputDialog("Enter the drug name.");
        if (name != null) {
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
    }

    // MODIFIES: this
    // EFFECTS: allows user to add a side effect to the given drug
    private void addSideEffectToDrug(Drug drug) {
        String sideEffect = JOptionPane.showInputDialog("Enter the side effect you would like to add");
        if (sideEffect != null) {
            sideEffect = sideEffect.toLowerCase();
            drug.addSideEffect(sideEffect);
            String message = sideEffect + " has been added successfully to " + drug.getName();
            JOptionPane.showMessageDialog(this, message, "Success!", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
