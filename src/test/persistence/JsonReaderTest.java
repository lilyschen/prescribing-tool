package persistence;

import model.Condition;
import model.Patient;
import model.PrescribingTool;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PrescribingTool pt = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPrescribingTool.json");
        try {
            PrescribingTool pt = reader.read();
            assertEquals("My Prescribing App", pt.getName());
            assertEquals(0, pt.numConditions());
            assertEquals(0, pt.numPatients());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPrescribingTool.json");
        try {
            PrescribingTool pt = reader.read();
            assertEquals("My Prescribing App", pt.getName());
            List<Condition> conditions = pt.getConditions();
            List<Patient> patients = pt.getPatients();
            assertEquals(2, conditions.size());
            assertEquals(1, patients.size());
            assertEquals("emily", patients.get(0).getName());
            assertEquals("acne", conditions.get(0).getName());
            assertEquals("cold sore", conditions.get(1).getName());
            assertEquals(1, patients.get(0).getDrugs().size());
            assertEquals("tretinoin", patients.get(0).getDrugs().get(0).getName());
            assertEquals(1, conditions.get(0).getDrugs().size());
            assertEquals("tretinoin", conditions.get(0).getDrugs().get(0).getName());
            assertEquals("valacyclovir", conditions.get(1).getDrugs().get(0).getName());
            assertEquals("dry skin", patients.get(0).getDrugs().get(0).getSideEffects().get(0));
            assertEquals("sun sensitivity", patients.get(0).getDrugs().get(0).getSideEffects().get(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
