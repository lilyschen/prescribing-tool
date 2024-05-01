package persistence;

import model.Condition;
import model.Drug;
import model.Patient;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkDrug(Drug drug, String drugName, int sideEffectsSize) {
        assertEquals(drugName, drug.getName());
        assertEquals(sideEffectsSize, drug.getSideEffects().size());
    }

    protected void checkPatient(Patient patient, String patientName, int drugListSize) {
        assertEquals(patientName, patient.getName());
        assertEquals(drugListSize, patient.getDrugs().size());
    }

    protected void checkCondition(Condition condition, String conditionName, int drugListSize) {
        assertEquals(conditionName, condition.getName());
        assertEquals(drugListSize, condition.getDrugs().size());
    }
}
