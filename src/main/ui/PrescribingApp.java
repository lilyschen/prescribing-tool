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
    private Drug clindamycin;
    private Drug acetaminophen;
    private Drug ibuprofen;
    private Drug naproxen;

    public PrescribingApp() {
        runApp();
    }

    private void runApp() {
        boolean keepRunning = true;
        String command = null;

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
            // searchPatient();
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
        clindamycin = new Drug("clindamycin");
        acetaminophen = new Drug("acetaminophen");
        ibuprofen = new Drug("ibuprofen");
        naproxen = new Drug("naproxen");

        conditions.add(acne);
        conditions.add(headache);

        acne.addDrug(tretinoin);
        acne.addDrug(adapalene);
        acne.addDrug(clindamycin);

        headache.addDrug(acetaminophen);
        headache.addDrug(ibuprofen);
        headache.addDrug(naproxen);


    }

    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tp -> find or create patient");
        System.out.println("\td -> view database");
        System.out.println("\tq -> quit App");
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
            System.out.println("Returning to main page.");
        } else {
            System.out.println("Selection not valid.");
        }
    }

    private Condition selectCondition(String selection) {
        if (selection.equals("add")) {
            return null;//stub adds a new condition - selCond
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
