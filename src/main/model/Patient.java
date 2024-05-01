package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a patient with their name and
// a list of drugs they are currently taking
public class Patient implements Writable {
    private String name;        // name of the patient
    private List<Drug> drugs;   // a list of prescribed drugs

    // REQUIRES: patient name has a non-zero length
    // EFFECTS: constructs the patient with given name and an empty drug list
    public Patient(String name) {
        this.name = name;
        this.drugs = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given drug to the drug list with no duplicates and logs Event
    //          if given drug is already in list, does nothing
    public void addDrug(Drug drug) {
        if (!drugs.contains(drug)) {
            drugs.add(drug);
            EventLog.getInstance().logEvent(new Event("Added drug: "
                    + drug.getName() + " to " + this.name + "'s drug list"));
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the given drug from the drug list if it's in the list and logs Event
    //          If the given drug was not in the list, does nothing
    public void removeDrug(Drug drug) {
        drugs.remove(drug);
        EventLog.getInstance().logEvent(new Event("Removed drug: "
                + drug.getName() + " from " + this.name + "'s drug list"));
    }

    // EFFECTS: if the given name is the name of a drug in
    //          the drug list, returns the drug
    //          Otherwise, returns null
    public Drug findDrugInPatientList(String drugName) {
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

    // EFFECTS: returns a string of given patient's drugs and logs Event
    public String displayDrugList() {
        StringBuilder drugNames = new StringBuilder("Medication List:");
        for (Drug drug : drugs) {
            drugNames.append("\n" + drug.getName());
        }
        EventLog.getInstance().logEvent(new Event("Viewed " + this.name + "'s drug list"));
        return drugNames.toString();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("drugs", drugsToJson());
        return json;
    }

    // EFFECTS: returns drugs in this patient as a JSON array
    private JSONArray drugsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Drug d : drugs) {
            jsonArray.put(d.toJson());
        }
        return jsonArray;
    }
}
