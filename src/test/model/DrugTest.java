package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DrugTest {
    private Drug drug;

    @BeforeEach
    void runBefore() {
        drug = new Drug("test drug");
    }

    @Test
    void testConstructor() {
        assertEquals("test drug", drug.getName());
        assertEquals(0, drug.getSideEffects().size());
    }

    @Test
    void testAddOneSideEffect() {
        drug.addSideEffect("diarrhea");
        assertEquals(1, drug.getSideEffects().size());
        assertEquals("diarrhea", drug.getSideEffects().get(0));
    }

    @Test
    void testAddMultipleSideEffects() {
        drug.addSideEffect("vomiting");
        drug.addSideEffect("diarrhea");
        drug.addSideEffect("sun sensitivity");
        assertEquals(3, drug.getSideEffects().size());
        assertEquals("vomiting", drug.getSideEffects().get(0));
        assertEquals("diarrhea", drug.getSideEffects().get(1));
        assertEquals("sun sensitivity", drug.getSideEffects().get(2));
    }

    @Test
    void testAddSideEffectDuplicate() {
        drug.addSideEffect("diarrhea");
        assertEquals(1, drug.getSideEffects().size());

        drug.addSideEffect("diarrhea");
        assertEquals(1, drug.getSideEffects().size());
    }

    @Test
    void testDisplaySideEffects() {
        assertEquals(null, drug.displaySideEffects());

        drug.addSideEffect("vomiting");
        assertEquals("vomiting", drug.displaySideEffects());

        drug.addSideEffect("diarrhea");
        assertEquals("vomiting, diarrhea", drug.displaySideEffects());

        drug.addSideEffect("sun sensitivity");
        assertEquals("vomiting, diarrhea, sun sensitivity", drug.displaySideEffects());
    }

    @Test
    void testToJson() {
        drug.addSideEffect("se1");
        drug.addSideEffect("se2");
        drug.addSideEffect("se3");

        String expectedJson =
                "{\"name\":\"test drug\",\"side effects\":[\"se1\",\"se2\",\"se3\"]}";
        String actualJson = drug.toJson().toString();

        assertEquals(expectedJson, actualJson);
    }

}
