package ui;

import model.Event;
import model.EventLog;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Represents WindowAdapter class that prints event log when window closes
// References below:
// learned about window listeners from: https://docs.oracle.com/javase/tutorial/uiswing/events/windowlistener.html
// Window Class: https://docs.oracle.com/javase/8/docs/api/java/awt/Window.html
// Note to self: JFrame is a subclass of Window, so can call addWindowListener in GUI since GUi extends JFrame
// WindowListener Interface: https://docs.oracle.com/javase/8/docs/api/java/awt/event/WindowListener.html
// WindowAdapter Class: https://docs.oracle.com/javase/8/docs/api/java/awt/event/WindowAdapter.html
// Note to self: WindowAdapter implements WindowListener so don't need to implement all the methods in WindowListener
//               if using WindowAdapter Class
public class MyWindowAdapter extends WindowAdapter {

    // EFFECTS: prints event log to console when window closes
    @Override
    public void windowClosing(WindowEvent e) {
        printEventLog();
    }

    // EFFECTS: prints event log to console
    // reference: Alarm System - FilePrinter.printLog method https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    public void printEventLog() {
        EventLog log = EventLog.getInstance();
        for (Event next : log) {
            System.out.println(next.toString() + "\n");
        }
    }
}
