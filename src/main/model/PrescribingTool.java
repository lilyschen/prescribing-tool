package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.Writable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a PrescribingTool having a collection of conditions and patients
// Note: modeled the JsonSerializationDemo example from Phase 2
public class PrescribingTool implements Writable {
    private String name;
    private List<Condition> conditions;  // a list of conditions available to prescribe from
    private List<Patient> patients;      // a list of patients
    private static final String JSON_STORE = "./data/prescribingtool.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs prescribing tool with a name,
    //          an empty list of conditions and an empty list of patients
    public PrescribingTool(String name) {
        this.name = name;
        this.conditions = new ArrayList<>();
        this.patients = new ArrayList<>();
        this.jsonWriter = new JsonWriter(JSON_STORE);
        this.jsonReader = new JsonReader(JSON_STORE);
    }

    public String getName() {
        return name;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    // EFFECTS: returns an unmodifiable list of conditions for viewing
    public List<Condition> getConditionsUnmodifiable() {
        EventLog.getInstance().logEvent(new Event("Viewed Conditions in the database"));
        return Collections.unmodifiableList(conditions);
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

    // EFFECTS: returns the number of patients currently taking the given drug
    public int numOfPtOnDrug(Drug selDrug) {
        int num = 0;
        String drugName = selDrug.getName();
        for (Patient patient : patients) {
            if (containsDrug(patient.getDrugs(), drugName)) {
                num++;
            }
        }
        EventLog.getInstance().logEvent(new Event("Viewed Drug Usage Report for " + drugName));
        return num;
    }

    // EFFECTS: returns true if the given name matches a drug name in the drugs list,
    //          false otherwise
    private boolean containsDrug(List<Drug> drugs, String name) {
        for (Drug drug : drugs) {
            if (drug.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns a list of names of conditions in alphabetical order
    public List<String> getAlphabeticalOrderConditionNameList() {
        List<String> conditionNames = new ArrayList<>();
        for (Condition condition : conditions) {
            conditionNames.add(condition.getName());
        }
        Collections.sort(conditionNames);
        EventLog.getInstance().logEvent(new Event("Viewed Conditions in Alphabetical Order"));
        return conditionNames;
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

    // MODIFIES: this
    // EFFECTS: saves the Prescribing Tool to file and returns message of success or failure to save
    public String save() {
        try {
            jsonWriter.open();
            jsonWriter.write(this);
            jsonWriter.close();
            EventLog.getInstance().logEvent(new Event("Updates saved to database"));
            return "Your updates have been saved successfully!";
        } catch (FileNotFoundException e) {
            EventLog.getInstance().logEvent(new Event("Updates failed to save to database"));
            return "Unable to write to file: " + JSON_STORE;
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Prescribing Tool from file and returns message of success or failure to load
    public String load() {
        try {
            PrescribingTool loadedPt = jsonReader.read();
            setLoadedPrescribingTool(loadedPt);
            EventLog.getInstance().clear();
            EventLog.getInstance().logEvent(new Event("Loaded data from database"));
            return "Previous data has been loaded successfully!";
        } catch (IOException e) {
            EventLog.getInstance().logEvent(new Event("Data failed to load from database"));
            return "Unable to read from file: " + JSON_STORE;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets fields of this prescribing tool to the fields of the given loaded prescribing tool
    private void setLoadedPrescribingTool(PrescribingTool loadedPt) {
        this.name = loadedPt.name;
        this.conditions = loadedPt.conditions;
        this.patients = loadedPt.getPatients();
    }
}

