package ui;

import model.PrescribingTool;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tabs.DatabaseTab;
import ui.tabs.HomeTab;
import ui.tabs.PatientTab;
import ui.tabs.StatisticsTab;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// referenced SmartHome application from CPSC210: https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters
public class GUI extends JFrame {
    public static final int HOME_TAB_INDEX = 0;
    public static final int PATIENT_TAB_INDEX = 1;
    public static final int DATABASE_TAB_INDEX = 2;
    public static final int STATISTICS_TAB_INDEX = 3;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    private JTabbedPane sidebar;
    private PrescribingTool prescribingTool;

    private static final String JSON_STORE = "./data/prescribingtool.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public static void main(String[] args) {
        new GUI();

//        try {
//            new PrescribingApp();
//        } catch (FileNotFoundException e) {
//            System.out.println("Cannot run application: file not found");
//        }
    }

    //MODIFIES: this
    //EFFECTS: creates GUI, loads Prescribing Tool, displays sidebar and tabs
    private GUI() {
        super("Prescribing Tool");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        prescribingTool = new PrescribingTool("My Prescribing Tool");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.LEFT);

        loadTabs();
        add(sidebar);

        setVisible(true);
    }

    //EFFECTS: returns Prescribing Tool object controlled by this UI
    public PrescribingTool getPrescribingTool() {
        return prescribingTool;
    }

    //MODIFIES: this
    //EFFECTS: adds home tab, patient tab and database tab to this UI
    private void loadTabs() {
        JPanel homeTab = new HomeTab(this);
        JPanel patientTab = new PatientTab(this);
        JPanel databaseTab = new DatabaseTab(this);
        JPanel statisticsTab = new StatisticsTab(this);

        sidebar.add(homeTab, HOME_TAB_INDEX);
        sidebar.setTitleAt(HOME_TAB_INDEX, "Home");
        sidebar.add(patientTab, PATIENT_TAB_INDEX);
        sidebar.setTitleAt(PATIENT_TAB_INDEX, "Patient");
        sidebar.add(databaseTab, DATABASE_TAB_INDEX);
        sidebar.setTitleAt(DATABASE_TAB_INDEX, "Database");
        sidebar.add(statisticsTab,STATISTICS_TAB_INDEX);
        sidebar.setTitleAt(STATISTICS_TAB_INDEX, "Statistics");
    }

    //EFFECTS: returns sidebar of this UI
    public JTabbedPane getTabbedPane() {
        return sidebar;
    }

    // EFFECTS: saves the Prescribing Tool to file
    public String savePrescribingTool() {
        try {
            jsonWriter.open();
            jsonWriter.write(prescribingTool);
            jsonWriter.close();
            return "Previous data has been loaded successfully!";
        } catch (FileNotFoundException e) {
            return "Unable to write to file: " + JSON_STORE;
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Prescribing Tool from file
    public String loadPrescribingTool() {
        try {
            prescribingTool = jsonReader.read();
            return "Previous data has been loaded successfully!";
        } catch (IOException e) {
            return "Unable to read from file: " + JSON_STORE;
        }
    }

}
