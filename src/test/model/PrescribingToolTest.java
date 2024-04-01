package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        condition1 = new Condition("conditionA");
        condition2 = new Condition("conditionB");
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

    @Test
    void testGetConditionsUnmodifiable() {
        prescribingTool.addCondition(condition1);
        prescribingTool.addCondition(condition2);
        List<Condition> test = prescribingTool.getConditionsUnmodifiable();
        assertEquals(2, test.size());
        assertEquals(condition1, test.get(0));
        assertEquals(condition2, test.get(1));
        try {
            test.remove(condition1);
            fail("UnsupportedOperationException not thrown when expected");
        } catch (UnsupportedOperationException e) {
            // success
        }
        assertEquals(2, test.size());
    }

    @Test
    void testGetAlphabeticalOrderConditionNameList() {
        prescribingTool.addCondition(condition1);
        prescribingTool.addCondition(condition2);
        List<String> test = prescribingTool.getAlphabeticalOrderConditionNameList();
        assertEquals(2, test.size());
        assertEquals("conditionA", test.get(0));
        assertEquals("conditionB", test.get(1));
    }

    @Test
    void testNumOfPtOnDrug() {
        prescribingTool.addPatient(patient1);
        prescribingTool.addPatient(patient2);
        assertEquals(0, prescribingTool.numOfPtOnDrug(drug1));

        patient1.addDrug(drug1);
        assertEquals(1, prescribingTool.numOfPtOnDrug(drug1));

        patient2.addDrug(drug1);
        assertEquals(2, prescribingTool.numOfPtOnDrug(drug1));
    }

    @Test
    void testSaveAndLoad() {
        prescribingTool.addCondition(condition1);
        prescribingTool.addCondition(condition2);
        prescribingTool.addPatient(patient1);
        prescribingTool.addPatient(patient2);
        prescribingTool.save();
        PrescribingTool newPt = new PrescribingTool("new prescribing tool");
        newPt.load();
        assertEquals("test pt", newPt.getName());
        assertEquals(2, newPt.numConditions());
        assertEquals("conditionA", newPt.getConditions().get(0).getName());
        assertEquals("conditionB", newPt.getConditions().get(1).getName());
        assertEquals(2, newPt.numPatients());
        assertEquals("patient1", newPt.getPatients().get(0).getName());
        assertEquals("patient2", newPt.getPatients().get(1).getName());
    }
}
