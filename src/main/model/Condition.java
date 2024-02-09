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
    //          if given drug is already in list, does nothing
    public void addDrug(Drug drug) {
        if (!drugs.contains(drug)) {
            drugs.add(drug);
        }
    }

    // EFFECTS: if the given name is the name of a drug in
    //          the drug list, returns the drug
    //          Otherwise, returns null
    public Drug findDrug(String drugName) {
        for (Drug drug : drugs) {
            if (drug.getName().equals(drugName)) {
                return drug;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public List<Drug> getDrugs() {
        return drugs;
    }
}
