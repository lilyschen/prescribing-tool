package persistence;

import model.Condition;
import model.Drug;
import model.Patient;
import model.PrescribingTool;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
// Note: modeled the JsonSerializationDemo example from Phase 2
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PrescribingTool read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePrescribingTool(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private PrescribingTool parsePrescribingTool(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        PrescribingTool prescribingTool = new PrescribingTool(name);
        addConditions(prescribingTool, jsonObject);
        addPatients(prescribingTool, jsonObject);
        return prescribingTool;
    }

    // MODIFIES: pt
    // EFFECTS: parses conditions from JSON object and adds them to PrescribingTool
    private void addConditions(PrescribingTool pt, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("conditions");
        for (Object json : jsonArray) {
            JSONObject nextCondition = (JSONObject) json;
            addCondition(pt, nextCondition);
        }
    }

    // MODIFIES: pt
    // EFFECTS: parses condition from JSON object and adds it to workroom
    private void addCondition(PrescribingTool pt, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        JSONArray drugsArray = jsonObject.getJSONArray("drugs");
        Condition condition = new Condition(name);
        addDrugsToCondition(condition, drugsArray);
        pt.addCondition(condition);
    }

    private void addDrugsToCondition(Condition condition, JSONArray drugsArray) {
        for (Object json : drugsArray) {
            JSONObject nextDrug = (JSONObject) json;
            String name = nextDrug.getString("name");
            JSONArray sideEffectsArray = nextDrug.getJSONArray("side effects");
            Drug drug = new Drug(name);
            addSideEffectsToDrug(drug, sideEffectsArray);
            condition.addDrug(drug);
        }
    }

    // MODIFIES: pt
    // EFFECTS: parses conditions from JSON object and adds them to PrescribingTool
    private void addPatients(PrescribingTool pt, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("patients");
        for (Object json : jsonArray) {
            JSONObject nextPatient = (JSONObject) json;
            addPatient(pt, nextPatient);
        }
    }

    // MODIFIES: pt
    // EFFECTS: parses condition from JSON object and adds it to workroom
    private void addPatient(PrescribingTool pt, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        JSONArray drugsArray = jsonObject.getJSONArray("drugs");
        Patient patient = new Patient(name);
        addDrugsToPatient(patient, drugsArray);
        pt.addPatient(patient);
    }

    private void addDrugsToPatient(Patient patient, JSONArray drugsArray) {
        for (Object json : drugsArray) {
            JSONObject nextDrug = (JSONObject) json;
            String name = nextDrug.getString("name");
            JSONArray sideEffectsArray = nextDrug.getJSONArray("side effects");
            Drug drug = new Drug(name);
            addSideEffectsToDrug(drug, sideEffectsArray);
            patient.addDrug(drug);
        }
    }

    private void addSideEffectsToDrug(Drug drug, JSONArray sideEffectsArray) {
        for (Object json : sideEffectsArray) {
            String se = (String) json;
            drug.addSideEffect(se);
        }
    }
}

