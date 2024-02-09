package model;

// Represents a drug with a name
public class Drug {
    private String name;  // name of the drug

    // EFFECTS: constructs the drug with given name
    public Drug(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
