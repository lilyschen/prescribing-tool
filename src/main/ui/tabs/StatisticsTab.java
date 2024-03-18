package ui.tabs;

import model.Condition;
import model.Drug;
import model.Patient;
import ui.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// referenced SmartHome application from CPSC210
public class StatisticsTab extends Tab {
    private static final String REPORT_GEN_MESSAGE = "Latest report: Today at ";

    private JScrollPane reportPane;
    private JTextArea reportText;
    private JLabel reportMessage;
    private String currTime;
    private SimpleDateFormat timeFormat;
    private Date currDate;

    //REQUIRES: GUI controller that holds this tab
    //EFFECTS: creates statistics tab with button and drug usage status functionality
    public StatisticsTab(GUI controller) {
        super(controller);

        currDate = Calendar.getInstance().getTime();
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        updateCurrTime();

        placeReportButton();

        JPanel reportBlock = new JPanel(new GridLayout(2, 1));
        reportBlock.setSize(GUI.WIDTH - (GUI.WIDTH / 5),
                GUI.HEIGHT - (GUI.HEIGHT / 5));
        reportMessage = new JLabel("");
        reportPane = new JScrollPane(new JTextArea(6, 40));
        reportText = new JTextArea("", 6, 40);
        reportText.setVisible(true);

        reportBlock.add(reportMessage);
        reportBlock.add(reportPane);

        add(reportBlock);
    }

    //MODIFIES: this
    //EFFECTS: adds a generate report button that prints drug usage status when clicked
    private void placeReportButton() {
        JButton b1 = new JButton("Drug Usage Report");
        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.setSize(WIDTH, HEIGHT / 6);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
//                getController().getSmartHome().update();
                if (buttonPressed.equals("Drug Usage Report")) {
                    updateCurrTime();
                    String message = REPORT_GEN_MESSAGE + currTime;
                    reportMessage.setText(message);
                    reportText.setText(checkStat());
                    reportPane.setViewportView(reportText);
                }
            }
        });

        this.add(buttonRow);
    }

    //MODIFIES: this
    //EFFECTS: updates current time in format HH:mm:ss
    private void updateCurrTime() {
        currDate = Calendar.getInstance().getTime();
        currTime = timeFormat.format(currDate);
    }

    // EFFECTS: processes user command to select a drug under a condition and
    //          returns a String message of how many people are currently taking the drug
    private String checkStat() {
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
                "Select a drug",
                JOptionPane.PLAIN_MESSAGE);
        String chosen = (String) drugOptions.getSelectedItem();
        Drug selDrug = selCond.findDrug(chosen);

        int num = 0;
        String drugName = selDrug.getName();
        for (Patient patient : getController().getPrescribingTool().getPatients()) {
            if (containsDrug(patient.getDrugs(), drugName)) {
                num++;
            }
        }
        return "The number of patient(s) currently taking " + selDrug.getName() + " is " + num;
    }

    // EFFECTS: returns true if the given name matches a drug name in the drugs list,
    //          false otherwise
    private boolean containsDrug(List<Drug> drugs, String name) {
        for (Drug drug : drugs) {
            if (drug.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

}
