package ui.tabs;

import model.Condition;
import model.Drug;
import model.Patient;
import ui.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

// referenced SmartHome application from CPSC210
public class PatientTab extends Tab {

    private static final String INIT_QUESTION = "Would you like search an existing patient or add a new patient?";
    private JLabel question;
    JButton b1;
    JButton b2;
    private JLabel imageLabel;
    private ImageIcon image;

    //EFFECTS: constructs a Patient tab for console with buttons and a question
    public PatientTab(GUI controller) {
        super(controller);
        setLayout(new GridLayout(3, 1));

        b1 = new JButton("Search");
        b2 = new JButton("Add");

        // reference: https://pixabay.com/vectors/drug-icon-pill-icon-medicine-icon-2316244/
        image = loadImage("drugIcon.png");
        imageLabel = new JLabel();
        imageLabel.setIcon(image);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        add(imageLabel);

        placeQuestion();
        placeButtons();
    }

    //EFFECTS: creates question at top of console
    private void placeQuestion() {
        question = new JLabel(INIT_QUESTION, JLabel.CENTER);
        question.setSize(WIDTH, HEIGHT / 3);
        this.add(question);
    }

    //MODIFIES: this
    //EFFECTS: places Search and Add buttons that performs associated actions when clicked
    private void placeButtons() {
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
                JOptionPane.showMessageDialog(this, "Patient not found", "Warning",
                        JOptionPane.WARNING_MESSAGE);
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
                "Please select an option:", "Patient: " + patient.getName(),
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, 0);

        if (selection == 0) {
            prescribe(patient);
        } else if (selection == 1) {

            if (patient.getDrugs().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "This patient is currently not taking any medications.",
                        "View patient: " + patient.getName(), JOptionPane.WARNING_MESSAGE);
            } else {
                JTextArea textArea = new JTextArea();
                textArea.setText(displayDrugList(patient));
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                JOptionPane.showMessageDialog(this, scrollPane, "View patient: " + patient.getName(),
                        JOptionPane.PLAIN_MESSAGE);
                removePatientsDrug(patient);
            }
        }
    }

    // EFFECTS: returns a string of given patient's drugs
    private String displayDrugList(Patient patient) {
        StringBuilder drugNames = new StringBuilder("Medication List:");
        for (Drug drug : patient.getDrugs()) {
            drugNames.append("\n" + drug.getName());
        }
        return drugNames.toString();
    }

    // MODIFIES: this
    // EFFECTS: allows user to remove a drug that the given patient is taking
    private void removePatientsDrug(Patient patient) {
        int choice = JOptionPane.showOptionDialog(this,
                "Would you like to delete a drug on " + patient.getName() + "'s drug list?",
                "Patient: " + patient.getName(), JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, null, 0);

        if (choice == 0) {
            String[] drugs = getPatientsDrugList(patient).toArray(new String[0]);
            JComboBox drugOptions = new JComboBox(drugs);
            JOptionPane.showMessageDialog(this, drugOptions, "Select a drug to remove",
                    JOptionPane.PLAIN_MESSAGE);
            String drugName = (String) drugOptions.getSelectedItem();
            Drug drug = patient.findDrugInPatientList(drugName);
            patient.removeDrug(drug);
            String message = drug.getName() + " has been successfully deleted";
            JOptionPane.showMessageDialog(this, message, "Success!",
                    JOptionPane.PLAIN_MESSAGE);
        }

    }

    // MODIFIES: this
    // EFFECTS: processes user commands to allows user to add/prescribe drug to the given patient
    private void prescribe(Patient patient) {
        String[] conditions = getConditionNamesList().toArray(new String[0]);
        JComboBox conditionOptions = new JComboBox(conditions);
        JOptionPane.showMessageDialog(this, conditionOptions, "Select a condition to prescribe from",
                JOptionPane.PLAIN_MESSAGE);
        String selection = (String) conditionOptions.getSelectedItem();
        Condition selCond = findCondition(selection);

        String[] drugs = getDrugsNamesList(selCond).toArray(new String[0]);
        JComboBox drugOptions = new JComboBox(drugs);
        JOptionPane.showMessageDialog(this, drugOptions, "Select a drug to prescribe to the patient",
                JOptionPane.PLAIN_MESSAGE);
        String drugName = (String) drugOptions.getSelectedItem();
        Drug selDrug = selCond.findDrug(drugName);
        patient.addDrug(selDrug);
        String message = selDrug.getName() + " has been added to patient's drug list."
                + "\nSide effects: " + selDrug.displaySideEffects();
        JOptionPane.showMessageDialog(this, message, "Success!",
                JOptionPane.PLAIN_MESSAGE);
    }

    // EFFECTS: prints out the names of the drugs in the patient's drug list
    private List<String> getPatientsDrugList(Patient patient) {
        List<String> names = new ArrayList<>();
        for (Drug drug : patient.getDrugs()) {
            names.add(drug.getName());
        }
        return names;
    }

}