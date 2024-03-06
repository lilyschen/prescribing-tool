package ui;

import java.io.FileNotFoundException;

// Note: modeled the JsonSerializationDemo example from Phase 2
public class Main {
    public static void main(String[] args) {
        try {
            new PrescribingApp();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot run application: file not found");
        }
    }
}
