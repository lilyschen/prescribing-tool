package model;

import java.util.ArrayList;
import java.util.List;

// Represents a condition with the condition name
// and a list of drugs used for treatment of this condition
public class Condition {
    private String name;         // name of the condition
    private List<Drug> drugs;    // a list of drugs used for treatment

    // EFFECTS: constructs the condition with given name and an empty drug list
    public Condition(String name) {
        this.name = name;
        this.drugs = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given drug to the drug list with no duplicates
    //          if given drug is already in list, do nothing
    public void addDrug(Drug drug) {
        if (!drugs.contains(drug)) {
            drugs.add(drug);
        }
    }

    public String getName() {
        return name;
    }

    public List<Drug> getDrugs() {
        return drugs;
    }
}
