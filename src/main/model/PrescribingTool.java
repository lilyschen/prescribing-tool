package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a PrescribingTool having a collection of conditions and patients
// Note: modeled the JsonSerializationDemo example from Phase 2
public class PrescribingTool implements Writable {
    private String name;
    private List<Condition> conditions;  // a list of conditions available to prescribe from
    private List<Patient> patients;      // a list of patients

    // EFFECTS: constructs prescribing tool with a name,
    //          an empty list of conditions and an empty list of patients
    public PrescribingTool(String name) {
        this.name = name;
        this.conditions = new ArrayList<>();
        this.patients = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    // MODIFIES: this
    // EFFECTS: adds condition to this prescribing tool
    public void addCondition(Condition condition) {
        conditions.add(condition);
        EventLog.getInstance().logEvent(new Event("Added condition: "
                + condition.getName() + " to " + this.name + " database"));
    }

    // MODIFIES: this
    // EFFECTS: adds patient to this prescribing tool
    public void addPatient(Patient patient) {
        patients.add(patient);
        EventLog.getInstance().logEvent(new Event("Added patient: "
                + patient.getName() + " to " + this.name + " database"));
    }

    // EFFECTS: returns number of conditions in this prescribing tool
    public int numConditions() {
        return conditions.size();
    }

    // EFFECTS: returns number of patients in this prescribing tool
    public int numPatients() {
        return patients.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("conditions", conditionsToJson());
        json.put("patients", patientsToJson());
        return json;
    }

    // EFFECTS: returns conditions in this prescribing tool as a JSON array
    private JSONArray conditionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Condition c : conditions) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns patients in this prescribing tool as a JSON array
    private JSONArray patientsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Patient p : patients) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
}

