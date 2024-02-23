package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a PrescribingTool having a collection of conditions and patients
// Note: modeled the JsonSerializationDemo example from Phase 2
public class PrescribingTool implements Writable {
    private String name;
    private List<Condition> conditions;  // a list of conditions available to prescribe from
    private List<Patient> patients;      // a list of patients

    // EFFECTS: constructs workroom with a name and empty list of thingies
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
    // EFFECTS: adds thingy to this workroom
    public void addCondition(Condition condition) {
        conditions.add(condition);
    }

    // MODIFIES: this
    // EFFECTS: adds thingy to this workroom
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    // EFFECTS: returns number of thingies in this workroom
    public int numConditions() {
        return conditions.size();
    }

    // EFFECTS: returns number of thingies in this workroom
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

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray conditionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Condition c : conditions) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray patientsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Patient p : patients) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
}

