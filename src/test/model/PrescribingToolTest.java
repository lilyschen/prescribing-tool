package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrescribingToolTest {
    private PrescribingTool prescribingTool;
    private Condition condition1;
    private Condition condition2;
    private Patient patient1;
    private Patient patient2;
    private Drug drug1;
    private Drug drug2;

    @BeforeEach
    void runBefore() {
        prescribingTool = new PrescribingTool("test pt");
        condition1 = new Condition("condition1");
        condition2 = new Condition("condition2");
        patient1 = new Patient("patient1");
        patient2 = new Patient("patient2");
        drug1 = new Drug("drug1");
        drug2 = new Drug("drug2");
    }

    @Test
    void testConstructor() {
        assertEquals("test pt", prescribingTool.getName());
        assertEquals(0, prescribingTool.getConditions().size());
        assertEquals(0, prescribingTool.getPatients().size());
    }

    @Test
    void testAddConditionOnce() {
        prescribingTool.addCondition(condition1);
        assertEquals(1, prescribingTool.numConditions());
        assertEquals(condition1, prescribingTool.getConditions().get(0));
    }

    @Test
    void testAddConditionTwice() {
        prescribingTool.addCondition(condition1);
        prescribingTool.addCondition(condition2);
        assertEquals(2, prescribingTool.numConditions());
        assertEquals(condition1, prescribingTool.getConditions().get(0));
        assertEquals(condition2, prescribingTool.getConditions().get(1));
    }

    @Test
    void testAddPatientOnce() {
        prescribingTool.addPatient(patient1);
        assertEquals(1, prescribingTool.numPatients());
        assertEquals(patient1, prescribingTool.getPatients().get(0));
    }

    @Test
    void testAddPatientTwice() {
        prescribingTool.addPatient(patient1);
        prescribingTool.addPatient(patient2);
        assertEquals(2, prescribingTool.numPatients());
        assertEquals(patient1, prescribingTool.getPatients().get(0));
        assertEquals(patient2, prescribingTool.getPatients().get(1));
    }
}
