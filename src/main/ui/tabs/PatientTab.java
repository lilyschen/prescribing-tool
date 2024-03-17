package ui.tabs;

import model.Condition;
import model.Drug;
import model.Patient;
import ui.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class PatientTab extends Tab {

    private static final String INIT_GREETING = "Would you like search an existing patient or add a new patient?";
    private JLabel greeting;

    public PatientTab(GUI controller) {
        super(controller);
        setLayout(new GridLayout(3, 1));

        placeGreeting();
        placeHomeButtons();
    }

    //EFFECTS: creates greeting at top of console
    private void placeGreeting() {
        greeting = new JLabel(INIT_GREETING, JLabel.CENTER);
        greeting.setSize(WIDTH, HEIGHT / 3);
        this.add(greeting);
    }

    //EFFECTS: creates Arrive and Leave buttons that change greeting message when clicked
    private void placeHomeButtons() {
        JButton b1 = new JButton("Search");
        JButton b2 = new JButton("Add");

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.add(b2);
        buttonRow.setSize(WIDTH, HEIGHT / 6);

        b1.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Please enter the patient's name: ");
            input = input.toLowerCase();
            Patient selPat = findPatient(input);

            if (selPat != null) {
                JOptionPane.showMessageDialog(this, "You have selected " + selPat.getName());
                modifyPatient(selPat);
            } else {
                JOptionPane.showMessageDialog(this, "Patient not found", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        b2.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter the patient name.");
            name = name.toLowerCase();
            Patient newPat = new Patient(name);
            getController().getPrescribingTool().addPatient(newPat);
            modifyPatient(newPat);
        });

        this.add(buttonRow);
    }

    // EFFECTS: returns a patient in patient list that matches given name
    //          returns null if not found
    private Patient findPatient(String name) {
        for (Patient patient : getController().getPrescribingTool().getPatients()) {
            if (patient.getName().equals(name)) {
                return patient;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: processes user commands and allows user to view or
    //          modify the patient's drug list
    private void modifyPatient(Patient patient) {
        String[] options = {"Prescribe", "View Drug List"};
        int selection = JOptionPane.showOptionDialog(this,
                "Please select:", "Patient", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, 0);

        if (selection == 0) {
            prescribe(patient);
        } else if (selection == 1) {
//            displayPatientsDrugs(patient);
//            modifyPatientsDrugs(patient);
        }
    }

    private void prescribe(Patient patient) {
        String[] conditions = getConditionNamesList().toArray(new String[0]);
        JComboBox options = new JComboBox(conditions);
        JOptionPane.showMessageDialog(this, options, "Select a condition to prescribe from",
                JOptionPane.PLAIN_MESSAGE);



//        String selection = input.next();
//        selection = selection.toLowerCase();
//
//        Condition selCond = findCondition(selection);
//
//        if (selCond != null) {
//            System.out.println("Here is a list of recommended medications:");
//            displayDrugs(selCond);
//            System.out.println("Enter the name of the drug you would like to prescribe.");
//            String drugName = input.next();
//            drugName = drugName.toLowerCase();
//            Drug selDrug = selCond.findDrug(drugName);
//
//            if (selDrug != null) {
//                patient.addDrug(selDrug);
//                System.out.println(selDrug.getName() + " has been added to patient's drug list.");
//                System.out.println("side effects: " + selDrug.displaySideEffects());
//            } else {
//                System.out.println("Selection not valid.");
//            }
//
//        } else {
//            System.out.println("Condition not found.");
//        }
    }

    // EFFECTS: returns the names of the conditions in the condition list
    private List<String> getConditionNamesList() {
        List<String> names = new ArrayList<>();
        for (Condition condition : getController().getPrescribingTool().getConditions()) {
            names.add(condition.getName());
        }
        return names;
    }
}