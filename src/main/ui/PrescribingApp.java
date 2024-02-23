package ui;

import model.Condition;
import model.Drug;
import model.Patient;
import model.PrescribingTool;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Prescribing application
// Note: modeled the JsonSerializationDemo example from Phase 2
public class PrescribingApp {
//    private List<Condition> conditions;  // a list of conditions available to prescribe from
//    private List<Patient> patients;      // a list of patients
    private static final String JSON_STORE = "./data/prescribingtool.json";
    private Scanner input;
    private PrescribingTool prescribingTool;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private Condition acne;      // test condition
    private Condition headache;  // test condition
    private Drug tretinoin;      // test drug
    private Drug adapalene;      // test drug
    private Drug acetaminophen;  // test drug
    private Drug ibuprofen;      // test drug
    private Patient emily;       // test patient
    private Patient molly;       // test patient

    // EFFECTS: runs the prescribing application
    public PrescribingApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        prescribingTool = new PrescribingTool("My Prescribing Tool");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
//        conditions = prescribingTool.getConditions();
//        patients = prescribingTool.getPatients();
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input from main menu
    private void runApp() {
        boolean keepRunning = true;
        String command;

//        init();

        while (keepRunning) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepRunning = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command from main menu
    private void processCommand(String command) {
        if (command.equals("p")) {
            searchPatient();
        } else if (command.equals("d")) {
            openDatabase();
        } else if (command.equals("s")) {
            savePrescribingTool();
        } else if (command.equals("l")) {
            loadPrescribingTool();
        } else {
            System.out.println("Selection not valid.");
        }
    }

//    // MODIFIES: this
//    // EFFECTS: initializes patient list and condition list and test drugs, conditions, and patients
//    private void init() {
//        conditions = new ArrayList<>();
//        patients = new ArrayList<>();
//        input = new Scanner(System.in);
//        input.useDelimiter("\n");
//
//        acne = new Condition("acne");
//        headache = new Condition("headache");
//        tretinoin = new Drug("tretinoin");
//        adapalene = new Drug("adapalene");
//        acetaminophen = new Drug("acetaminophen");
//        ibuprofen = new Drug("ibuprofen");
//
//        conditions.add(acne);
//        conditions.add(headache);
//
//        acne.addDrug(tretinoin);
//        acne.addDrug(adapalene);
//        headache.addDrug(acetaminophen);
//        headache.addDrug(ibuprofen);
//
//        molly = new Patient("molly");
//        emily = new Patient("emily");
//        molly.addDrug(tretinoin);
//        emily.addDrug(acetaminophen);
//        patients.add(emily);
//        patients.add(molly);
//    }

    // EFFECTS: displays menus of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tp -> find or create patient");
        System.out.println("\td -> view database");
        System.out.println("\ts -> save updates to file");
        System.out.println("\tl -> load last update from file");
        System.out.println("\tq -> quit App");
    }

    // MODIFIES: this
    // EFFECTS: processes user commands to allow user to view or add a patient
    //          and modify the patient's list of medications
    private void searchPatient() {
        System.out.println("\nEnter a patient name to search or enter 'add' to add a new patient.");
        String selection = input.next();
        selection = selection.toLowerCase();
        Patient selPat = selectPatient(selection);

        if (selPat != null) {
            System.out.println("You have selected " + selPat.getName());
            modifyPatient(selPat);
        } else {
            System.out.println("Patient not found.");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user commands and allows user to view or
    //          modify the patient's drug list
    private void modifyPatient(Patient patient) {
        System.out.println("\nSelect from:");
        System.out.println("\tp -> prescribe for minor ailments");
        System.out.println("\td -> view drug list");
        String command = input.next();
        command = command.toLowerCase();

        if (command.equals("p")) {
            prescribe(patient);
        } else if (command.equals("d")) {
            displayPatientsDrugs(patient);
            modifyPatientsDrugs(patient);
        } else {
            System.out.println("Selection not valid.");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user commands to allows user to add/prescribe drug to the
    //          given patient
    private void prescribe(Patient patient) {
        System.out.println("Here is a list of available conditions to choose from:");
        displayConditions();
        System.out.println("Enter the name of the condition you are prescribing for.");

        String selection = input.next();
        selection = selection.toLowerCase();

        Condition selCond = findCondition(selection);

        if (selCond != null) {
            System.out.println("Here is a list of recommended medications:");
            displayDrugs(selCond);
            System.out.println("Enter the name of the drug you would like to prescribe.");
            String drugName = input.next();
            drugName = drugName.toLowerCase();
            Drug selDrug = selCond.findDrug(drugName);

            if (selDrug != null) {
                patient.addDrug(selDrug);
                System.out.println(selDrug.getName() + " has been added to patient's drug list.");
                System.out.println("side effects: " + selDrug.displaySideEffects());
            } else {
                System.out.println("Selection not valid.");
            }

        } else {
            System.out.println("Condition not found.");
        }
    }

    // EFFECTS: prints out the names of the medications
    //          the patient is taking
    private void displayPatientsDrugs(Patient patient) {
        if (patient.getDrugs().isEmpty()) {
            System.out.println("This patient is currently not taking any medications.");
        } else {
            for (Drug drug : patient.getDrugs()) {
                System.out.println(drug.getName());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: asks if user would like to delete a drug from a patient's drug list
    //          and processes the user command
    private void modifyPatientsDrugs(Patient patient) {
        System.out.println("\nWould you like to delete a drug on " + patient.getName() + "'s drug list?");
        System.out.println("\ty -> yes");
        System.out.println("\tn -> no");
        String command = input.next();
        command = command.toLowerCase();

        if (command.equals("y")) {
            removePatientsDrug(patient);
        } else if (command.equals("n")) {
            System.out.println("Returning to main page.");
        } else {
            System.out.println("Selection not valid.");
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to remove a drug that the given patient is taking
    private void removePatientsDrug(Patient patient) {
        System.out.println("Enter the name of the drug you would like to remove,");
        String drugName = input.next();
        drugName = drugName.toLowerCase();
        Drug drug = patient.findDrugInPatientList(drugName);

        if (drug != null) {
            patient.removeDrug(drug);
            System.out.println(drug.getName() + " has been successfully deleted.");
        } else {
            System.out.println("Selection not valid.");
        }
    }

    // MODIFIES: this
    // EFFECTS: if user inputs "add", allows user to add a new patient
    //          to the patient list and returns the new patient
    //          otherwise, allows user to find existing patient and return it
    private Patient selectPatient(String selection) {
        if (selection.equals("add")) {
            System.out.println("Enter the patient name.");
            String patName = input.next();
            patName = patName.toLowerCase();
            Patient newPat = new Patient(patName);
//            patients.add(newPat);
            prescribingTool.addPatient(newPat);
            return newPat;
        } else {
            return findPatient(selection);
        }
    }

    // EFFECTS: returns a patient in patient list that matches given name
    //          returns null if not found
    private Patient findPatient(String name) {
        for (Patient patient : prescribingTool.getPatients()) {
            if (patient.getName().equals(name)) {
                return patient;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: displays a list of conditions in the database
    //          and processes user command to modify the database
    private void openDatabase() {
        displayConditions();
        System.out.println("\nSelect a condition by entering its name or enter 'add' to add a new condition");
        String selection = input.next();
        selection = selection.toLowerCase();
        Condition selCond = selectCondition(selection);

        if (selCond != null) {
            displayDrugs(selCond);
            modifyDrugList(selCond);
        } else {
            System.out.println("Condition not found.");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command to view or modify drug list for
    //          the given condition
    private void modifyDrugList(Condition selCond) {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add a drug to the condition");
        System.out.println("\tc -> check how many people is taking a drug");
        System.out.println("\ts -> view or add side effect(s) to a drug");
        String selection = input.next();
        selection = selection.toLowerCase();

        if (selection.equals("a")) {
            System.out.println("Enter the drug name.");
            selection = input.next();
            selection = selection.toLowerCase();
            selCond.addDrug(new Drug(selection));
            System.out.println(selection + " has been added successfully.");
        } else if (selection.equals("c")) {
            checkStat(selCond);
        } else if (selection.equals("s")) {
            modifySideEffects(selCond);
        } else {
            System.out.println("Selection not valid.");
        }
    }

    // EFFECTS: allows user to select a drug and display how many people are taking it
    private void checkStat(Condition selCond) {
        System.out.println("\nSelect a drug to see how many people are taking it.");
        String selection = input.next();
        selection = selection.toLowerCase();

        Drug selDrug = selCond.findDrug(selection);
        displayNumTimesPrescribed(selDrug);
    }

    // MODIFIES: this
    // EFFECTS: allows user to select a drug and view the side effects
    //          and add side effects
    private void modifySideEffects(Condition selCond) {
        System.out.println("\nSelect a drug to view the side effects.");
        String selection = input.next();
        selection = selection.toLowerCase();

        Drug selDrug = selCond.findDrug(selection);
        if (selDrug == null) {
            System.out.println("Drug not found.");
        } else {
            if (selDrug.displaySideEffects() == null) {
                System.out.println("No side effects listed.");
            } else {
                System.out.println(selDrug.displaySideEffects());
            }
            addSideEffect(selDrug);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command and allows user to add a side effect(s) to the given drug
    private void addSideEffect(Drug selDrug) {
        System.out.println("\nWould you like to add a side effect to this drug?");
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("\ty -> yes");
            System.out.println("\tn -> no");
            String selection = input.next();
            selection = selection.toLowerCase();

            if (selection.equals("n")) {
                System.out.println("Returning to main menu");
                keepGoing = false;
            } else if (selection.equals("y")) {
                System.out.println("Enter the side effect you would like to add:");
                String sideEffect = input.next();
                sideEffect = sideEffect.toLowerCase();
                selDrug.addSideEffect(sideEffect);
                System.out.println("New side effect list: " + selDrug.displaySideEffects());
                System.out.println("Would you like to add another side effect to this drug?");
            } else {
                System.out.println("Selection not valid.");
                keepGoing = false;
            }
        }
    }


//    // EFFECTS: prints out the number of patients taking the given drug
//    //          if the given drug is not found, prints out not found
//    private void displayNumTimesPrescribed(Drug drug) {
//        if (drug != null) {
//            int num = 0;
//            for (Patient patient : prescribingTool.getPatients()) {
//                if (patient.getDrugs().contains(drug)) {
//                    num++;
//                }
//            }
//            System.out.println("The number of patient(s) currently taking " + drug.getName() + " is " + num);
//        } else {
//            System.out.println("Drug not found.");
//        }
//    }

    // EFFECTS: prints out the number of patients taking the given drug
    //          if the given drug is not found, prints out not found
    private void displayNumTimesPrescribed(Drug drug) {
        if (drug != null) {
            int num = 0;
            String drugName = drug.getName();
            for (Patient patient : prescribingTool.getPatients()) {
                if (containsDrug(patient.getDrugs(), drugName)) {
                    num++;
                }
            }
            System.out.println("The number of patient(s) currently taking " + drug.getName() + " is " + num);
        } else {
            System.out.println("Drug not found.");
        }
    }

    private boolean containsDrug(List<Drug> drugs, String name) {
        for (Drug drug : drugs) {
            if (drug.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: if "add" command is given, asks user to input a condition name,
    //          adds the condition to the condition list and returns the new condition
    //          otherwise, returns the condition found based on given string
    private Condition selectCondition(String selection) {
        if (selection.equals("add")) {
            System.out.println("Enter the condition name.");
            String condName = input.next();
            condName = condName.toLowerCase();
            Condition newCond = new Condition(condName);
//            conditions.add(newCond);
            prescribingTool.addCondition(newCond);
            return newCond;
        } else {
            return findCondition(selection);
        }
    }

    // EFFECTS: prints out the names of the conditions in the condition list
    private void displayConditions() {
        for (Condition condition : prescribingTool.getConditions()) {
            System.out.println(condition.getName());
        }
    }

    // EFFECTS: prints out the names of the drugs
    //          used for treatment for the given condition
    private void displayDrugs(Condition condition) {
        for (Drug drug : condition.getDrugs()) {
            System.out.println(drug.getName());
        }
    }

    // EFFECTS: returns a condition in condition list that matches given name
    //          returns null if not found
    private Condition findCondition(String name) {
        for (Condition condition : prescribingTool.getConditions()) {
            if (condition.getName().equals(name)) {
                return condition;
            }
        }
        return null;
    }

    // EFFECTS: saves the Prescribing Tool to file
    private void savePrescribingTool() {
        try {
            jsonWriter.open();
            jsonWriter.write(prescribingTool);
            jsonWriter.close();
            System.out.println("Saved all updates to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Prescribing Tool from file
    private void loadPrescribingTool() {
        try {
            prescribingTool = jsonReader.read();
            System.out.println("Loaded " + prescribingTool.getName() + " from" + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
