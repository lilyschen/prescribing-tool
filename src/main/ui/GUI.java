package ui;

import model.PrescribingTool;
import ui.tabs.DatabaseTab;
import ui.tabs.HomeTab;
import ui.tabs.PatientTab;
import ui.tabs.StatisticsTab;

import javax.swing.*;

// represents Prescribing Application GUI
public class GUI extends JFrame {
    public static final int HOME_TAB_INDEX = 0;
    public static final int PATIENT_TAB_INDEX = 1;
    public static final int DATABASE_TAB_INDEX = 2;
    public static final int STATISTICS_TAB_INDEX = 3;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    private JTabbedPane sidebar;
    private PrescribingTool prescribingTool;

    private MyWindowAdapter myWindowAdapter = new MyWindowAdapter();

    public static void main(String[] args) {
        new GUI();
    }

    //MODIFIES: this
    //EFFECTS: creates GUI, loads Prescribing Tool, displays sidebar and tabs
    private GUI() {
        super("Prescribing Tool");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(myWindowAdapter);

        prescribingTool = new PrescribingTool("My Prescribing Tool");

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

    // EFFECTS: returns myWindowAdapter
    public MyWindowAdapter getMyWindowAdapter() {
        return myWindowAdapter;
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

    // MODIFIES: this
    // EFFECTS: saves the Prescribing Tool to file and returns message of success or failure to save
    public String savePrescribingTool() {
        return prescribingTool.save();
    }

    // MODIFIES: this
    // EFFECTS: loads Prescribing Tool from file and returns message of success or failure to load
    public String loadPrescribingTool() {
        return prescribingTool.load();
    }
}
