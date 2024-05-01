package ui;

import model.Event;
import model.EventLog;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Represents WindowAdapter class that prints event log when window closes
public class MyWindowAdapter extends WindowAdapter {

    // EFFECTS: prints event log to console when window closes
    @Override
    public void windowClosing(WindowEvent e) {
        printEventLog();
    }

    // EFFECTS: prints event log to console
    public void printEventLog() {
        EventLog log = EventLog.getInstance();
        for (Event next : log) {
            System.out.println(next.toString() + "\n");
        }
    }
}
