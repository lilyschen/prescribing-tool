package model;

import java.util.ArrayList;
import java.util.List;

public class Condition {
    private String name;
    private List<Drug> drugs;

    public Condition(String name) {
        this.name = name;
        this.drugs = new ArrayList<>();
    }

    public void addDrug(Drug drug) {
        drugs.add(drug);
    }

    public String getName() {
        return name;
    }

    public List<Drug> getDrugs() {
        return drugs;
    }
}
