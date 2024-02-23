package persistence;

import org.json.JSONObject;

// Note: modeled the JsonSerializationDemo example from Phase 2
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

