package ui.tabs;

import ui.GUI;

import javax.swing.*;
import java.awt.*;

// referenced SmartHome application from CPSC210
// represents Home Tab/Panel
public class HomeTab extends Tab {

    private static final String INIT_GREETING = "Would you like to load previous data, save new updates, or quit?";
    private JLabel greeting;
    private JLabel imageLabel;
    private ImageIcon image;

    //REQUIRES: GUI controller that holds this tab
    //EFFECTS: constructs a home tab for console with buttons and a greeting
    public HomeTab(GUI controller) {
        super(controller);
        setLayout(new GridLayout(3, 1));

        // reference: https://pixabay.com/vectors/vitamins-tablets-pills-26622/
        image = loadImage("vitaminsIcon.png");
        imageLabel = new JLabel();
        imageLabel.setIcon(image);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        add(imageLabel);

        placeGreeting();
        placeHomeButtons();

    }

    //EFFECTS: creates greeting at top of console
    private void placeGreeting() {
        greeting = new JLabel(INIT_GREETING, JLabel.CENTER);
        greeting.setSize(WIDTH, HEIGHT / 3);
        this.add(greeting);
    }

    //MODIFIES: this
    //EFFECTS: creates Load, Save and Quit buttons that change greeting message when clicked
    private void placeHomeButtons() {
        JButton b1 = new JButton("Load");
        JButton b2 = new JButton("Save");
        JButton b3 = new JButton("Quit");

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.add(b2);
        buttonRow.add(b3);
        buttonRow.setSize(WIDTH, HEIGHT / 6);

        b1.addActionListener(e -> {
            String message = getController().loadPrescribingTool();
            greeting.setText(message);
        });

        b2.addActionListener(e -> {
            String message = getController().savePrescribingTool();
            greeting.setText(message);
        });

        b3.addActionListener(e -> {
            System.exit(0);
        });

        this.add(buttonRow);
    }

}
