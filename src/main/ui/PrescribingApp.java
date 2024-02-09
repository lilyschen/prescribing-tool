package ui;

import model.Condition;
import model.Drug;
import model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrescribingApp {
    private List<Condition> conditions;
    private List<Patient> patients;
    private Scanner input;

    private Condition acne;      //test conditions & medications
    private Condition headache;
    private Drug tretinoin;
    private Drug adapalene;
    private Drug acetaminophen;
    private Drug ibuprofen;
    private Patient emily;
    private Patient molly;

    public PrescribingApp() {
        runApp();
    }

    private void runApp() {
        boolean keepRunning = true;
        String command;

        init();

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

    private void processCommand(String command) {
        if (command.equals("p")) {
            searchPatient();
        } else if (command.equals("d")) {
            openDatabase();
        } else {
            System.out.println("Selection not valid.");
        }
    }

    private void init() {
        conditions = new ArrayList<>();
        patients = new ArrayList<>();
        input = new Scanner(System.in);
        input.useDelimiter("\n");

        acne = new Condition("acne");
        headache = new Condition("headache");
        tretinoin = new Drug("tretinoin");
        adapalene = new Drug("adapalene");
        acetaminophen = new Drug("acetaminophen");
        ibuprofen = new Drug("ibuprofen");

        conditions.add(acne);
        conditions.add(headache);

        acne.addDrug(tretinoin);
        acne.addDrug(adapalene);
        headache.addDrug(acetaminophen);
        headache.addDrug(ibuprofen);

        molly = new Patient("molly");
        emily = new Patient("emily");
        molly.addDrug(tretinoin);
        emily.addDrug(acetaminophen);
        patients.add(emily);
        patients.add(molly);
    }

    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tp -> find or create patient");
        System.out.println("\td -> view database");
        System.out.println("\tq -> quit App");
    }

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
            Drug selDrug = findDrug(selCond, drugName);

            if (selDrug != null) {
                patient.addDrug(selDrug);
                System.out.println(selDrug.getName() + " has been added to patient's drug list.");
            } else {
                System.out.println("Selection not valid.");
            }

        } else {
            System.out.println("Condition not found.");
        }
    }

    private Drug findDrug(Condition condition, String drugName) {
        for (Drug drug : condition.getDrugs()) {
            if (drug.getName().equals(drugName)) {
                return drug;
            }
        }
        return null;
    }

    private void displayPatientsDrugs(Patient patient) {
        for (Drug drug : patient.getDrugs()) {
            System.out.println(drug.getName());
        }
    }

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


    private Patient selectPatient(String selection) {
        if (selection.equals("add")) {
            System.out.println("Enter the patient name.");
            String patName = input.next();
            patName = patName.toLowerCase();
            Patient newPat = new Patient(patName);
            patients.add(newPat);
            return newPat;
        } else {
            return findPatient(selection);
        }
    }

    private Patient findPatient(String name) {
        for (Patient patient : patients) {
            if (patient.getName().equals(name)) {
                return patient;
            }
        }
        return null;
    }

    private void openDatabase() {
        displayConditions();
        System.out.println("\nSelect a condition by entering its name or enter 'add' to add a new condition");
        String selection = input.next();
        selection = selection.toLowerCase();
        Condition selCond = selectCondition(selection);

        if (selCond != null) {
            displayDrugs(selCond);
            modifyDrugs(selCond);
            checkStat(selCond);
        } else {
            System.out.println("Condition not found.");
        }
    }

    private void modifyDrugs(Condition selCond) {
        System.out.println("\nWould you like to add a drug to the condition?");
        System.out.println("\ty -> yes");
        System.out.println("\tn -> no");
        String selection = input.next();
        selection = selection.toLowerCase();

        if (selection.equals("y")) {
            System.out.println("Enter the drug name.");
            selection = input.next();
            selection = selection.toLowerCase();
            selCond.addDrug(new Drug(selection));
        } else if (selection.equals("n")) {
            System.out.println("");
        } else {
            System.out.println("Selection not valid.");
        }
    }

    private void checkStat(Condition selCond) {
        System.out.println("\nSelect a drug to see how many people are taking it or type 'no' to return to main page.");
        String selection = input.next();
        selection = selection.toLowerCase();

        if (selection.equals("no")) {
            System.out.println("Returning to main page.");
        } else {
            Drug selDrug = findDrug(selCond, selection);
            displayNumTimesPrescribed(selDrug);
        }
    }

    private void displayNumTimesPrescribed(Drug drug) {
        if (drug != null) {
            int num = 0;
            for (Patient patient : patients) {
                if (patient.getDrugs().contains(drug)) {
                    num++;
                }
            }
            System.out.println("The number of patient(s) currently taking " + drug.getName() + " is " + num);
        } else {
            System.out.println("Drug not found.");
        }
    }

    private Condition selectCondition(String selection) {
        if (selection.equals("add")) {
            System.out.println("Enter the condition name.");
            String condName = input.next();
            condName = condName.toLowerCase();
            Condition newCond = new Condition(condName);
            conditions.add(newCond);
            return newCond;
        } else {
            return findCondition(selection);
        }
    }

    private void displayConditions() {
        for (Condition condition : conditions) {
            System.out.println(condition.getName());
        }
    }

    private void displayDrugs(Condition condition) {
        for (Drug drug : condition.getDrugs()) {
            System.out.println(drug.getName());
        }
    }

    private Condition findCondition(String name) {
        for (Condition condition : conditions) {
            if (condition.getName().equals(name)) {
                return condition;
            }
        }
        return null;
    }
}
