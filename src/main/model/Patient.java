package model;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String name;
    private List<Drug> drugs;

    public Patient(String name) {
        this.name = name;
        this.drugs = new ArrayList<>();
    }

    public void addDrug(Drug drug) {
        drugs.add(drug);
    }

    public void removeDrug(Drug drug) {
        drugs.remove(drug);
    }

    public String getName() {
        return name;
    }

    public List<Drug> getDrugs() {
        return drugs;
    }
}
