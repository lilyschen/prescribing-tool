package persistence;

import model.Condition;
import model.Drug;
import model.Patient;
import model.PrescribingTool;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// referenced JsonSerializationDemo example for phase 2
public class JsonReaderTest extends JsonTest {

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
            assertEquals(2, patients.size());

            Patient patient0 = patients.get(0);
            Patient patient1 = patients.get(1);
            Drug patient0Drug0 = patient0.getDrugs().get(0);
            Drug patient0Drug1 = patient0.getDrugs().get(1);
            Drug patient1Drug0 = patient1.getDrugs().get(0);
            Drug patient1Drug1 = patient1.getDrugs().get(1);

            Condition condition0 = conditions.get(0);
            Condition condition1 = conditions.get(1);
            Drug condition0Drug0 = condition0.getDrugs().get(0);
            Drug condition0Drug1 = condition0.getDrugs().get(1);
            Drug condition1Drug0 = condition1.getDrugs().get(0);
            Drug condition1Drug1 = condition1.getDrugs().get(1);

            checkPatient(patient0, "emily", 2);
            checkPatient(patient1, "alex", 2);

            checkCondition(condition0, "acne", 2);
            checkCondition(condition1, "headache", 2);

            checkDrug(patient0Drug0, "tretinoin", 2);
            assertEquals("dry skin", patient0Drug0.getSideEffects().get(0));
            assertEquals("sun sensitivity", patient0Drug0.getSideEffects().get(1));

            checkDrug(patient0Drug1, "acetaminophen", 1);
            assertEquals("liver damage", patient0Drug1.getSideEffects().get(0));

            checkDrug(patient1Drug0, "benzoyl peroxide", 2);
            assertEquals("irritation", patient1Drug0.getSideEffects().get(0));
            assertEquals("sun sensitivity", patient1Drug0.getSideEffects().get(1));

            checkDrug(patient1Drug1, "ibuprofen",3);
            assertEquals("upset stomach", patient1Drug1.getSideEffects().get(0));
            assertEquals("stomach bleeding", patient1Drug1.getSideEffects().get(1));
            assertEquals("dizziness", patient1Drug1.getSideEffects().get(2));

            checkDrug(condition0Drug0, "tretinoin", 2);
            assertEquals("dry skin", condition0Drug0.getSideEffects().get(0));
            assertEquals("sun sensitivity", condition0Drug0.getSideEffects().get(1));

            checkDrug(condition0Drug1, "benzoyl peroxide", 2);
            assertEquals("irritation", condition0Drug1.getSideEffects().get(0));
            assertEquals("sun sensitivity", condition0Drug1.getSideEffects().get(1));

            checkDrug(condition1Drug0, "acetaminophen", 1);
            assertEquals("liver damage", condition1Drug0.getSideEffects().get(0));

            checkDrug(condition1Drug1, "ibuprofen",3);
            assertEquals("upset stomach", condition1Drug1.getSideEffects().get(0));
            assertEquals("stomach bleeding", condition1Drug1.getSideEffects().get(1));
            assertEquals("dizziness", condition1Drug1.getSideEffects().get(2));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
