package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {
    private Patient patient;

    @BeforeEach
    void runBefore() {
        patient = new Patient("test patient");
    }

    @Test
    void testConstructor() {
        assertEquals("test patient", patient.getName());
        assertEquals(0, patient.getDrugs().size());
    }

    @Test
    void testAddDrugOnce() {
        Drug drug1 = new Drug("drug1");
        patient.addDrug(drug1);
        assertEquals(1, patient.getDrugs().size());
        assertEquals(drug1, patient.getDrugs().get(0));
    }

    @Test
    void testAddDrugMultipleTimes() {
        Drug drug1 = new Drug("drug1");
        Drug drug2 = new Drug("drug2");
        Drug drug3 = new Drug("drug3");
        patient.addDrug(drug1);
        patient.addDrug(drug2);
        patient.addDrug(drug3);
        assertEquals(3, patient.getDrugs().size());
        assertEquals(drug1, patient.getDrugs().get(0));
        assertEquals(drug2, patient.getDrugs().get(1));
        assertEquals(drug3, patient.getDrugs().get(2));
    }

    @Test
    void testAddDrugDuplicate() {
        Drug drug1 = new Drug("drug1");
        patient.addDrug(drug1);
        assertEquals(1, patient.getDrugs().size());

        patient.addDrug(drug1);
        assertEquals(1, patient.getDrugs().size());
    }

    @Test
    void testRemoveDrugOnce() {
        Drug drug1 = new Drug("drug1");
        patient.addDrug(drug1);
        assertEquals(1, patient.getDrugs().size());

        patient.removeDrug(drug1);
        assertEquals(0, patient.getDrugs().size());
    }

    @Test
    void testRemoveDrugNotThere() {
        Drug drug1 = new Drug("drug1");
        Drug drug2 = new Drug("drug2");
        patient.addDrug(drug1);
        assertEquals(1, patient.getDrugs().size());

        patient.removeDrug(drug2);
        assertEquals(1, patient.getDrugs().size());
    }

    @Test
    void testRemoveDrugMultipleTimes() {
        Drug drug1 = new Drug("drug1");
        Drug drug2 = new Drug("drug2");
        Drug drug3 = new Drug("drug3");
        patient.addDrug(drug1);
        patient.addDrug(drug2);
        patient.addDrug(drug3);
        assertEquals(3, patient.getDrugs().size());

        patient.removeDrug(drug1);
        assertEquals(2, patient.getDrugs().size());
        assertEquals(drug2, patient.getDrugs().get(0));
        assertEquals(drug3, patient.getDrugs().get(1));

        patient.removeDrug(drug2);
        assertEquals(1, patient.getDrugs().size());
        assertEquals(drug3, patient.getDrugs().get(0));

        patient.removeDrug(drug3);
        assertEquals(0, patient.getDrugs().size());

    }

    @Test
    void testFindDrugInPatientList() {
        Drug drug1 = new Drug("drug1");
        Drug drug2 = new Drug("drug2");
        Drug drug3 = new Drug("drug3");
        patient.addDrug(drug1);
        patient.addDrug(drug2);
        patient.addDrug(drug3);
        assertEquals(null, patient.findDrugInPatientList("drug0"));
        assertEquals(drug1, patient.findDrugInPatientList("drug1"));
        assertEquals(drug2, patient.findDrugInPatientList("drug2"));
        assertEquals(drug3, patient.findDrugInPatientList("drug3"));
    }

    @Test
    void testToJson() {
        Drug drug1 = new Drug("drug1");
        drug1.addSideEffect("side effect");
        patient.addDrug(drug1);

        String expectedJson =
                "{\"drugs\":[{\"name\":\"drug1\",\"side effects\":[\"side effect\"]}],\"name\":\"test patient\"}";
        String actualJson = patient.toJson().toString();

        assertEquals(expectedJson, actualJson);
    }
}
