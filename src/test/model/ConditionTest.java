package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConditionTest {
    private Condition condition;

    @BeforeEach
    void runBefore() {
        condition = new Condition("test condition");
    }

    @Test
    void testConstructor() {
        assertEquals("test condition", condition.getName());
        assertEquals(0, condition.getDrugs().size());
    }

    @Test
    void testAddDrugOnce() {
        Drug drug1 = new Drug("drug1");
        condition.addDrug(drug1);
        assertEquals(1, condition.getDrugs().size());
        assertEquals(drug1, condition.getDrugs().get(0));
    }

    @Test
    void testAddDrugMultipleTimes() {
        Drug drug1 = new Drug("drug1");
        Drug drug2 = new Drug("drug2");
        Drug drug3 = new Drug("drug3");
        condition.addDrug(drug1);
        condition.addDrug(drug2);
        condition.addDrug(drug3);
        assertEquals(3, condition.getDrugs().size());
        assertEquals(drug1, condition.getDrugs().get(0));
        assertEquals(drug2, condition.getDrugs().get(1));
        assertEquals(drug3, condition.getDrugs().get(2));
    }

    @Test
    void testAddDrugDuplicate() {
        Drug drug1 = new Drug("drug1");
        condition.addDrug(drug1);
        assertEquals(1, condition.getDrugs().size());

        condition.addDrug(drug1);
        assertEquals(1, condition.getDrugs().size());
    }

    @Test
    void testFindDrug() {
        Drug drug1 = new Drug("drug1");
        Drug drug2 = new Drug("drug2");
        Drug drug3 = new Drug("drug3");
        condition.addDrug(drug1);
        condition.addDrug(drug2);
        condition.addDrug(drug3);
        assertEquals(null, condition.findDrug("drug0"));
        assertEquals(drug1, condition.findDrug("drug1"));
        assertEquals(drug2, condition.findDrug("drug2"));
        assertEquals(drug3, condition.findDrug("drug3"));
    }
}