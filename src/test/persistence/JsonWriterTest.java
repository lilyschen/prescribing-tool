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

public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            PrescribingTool pt = new PrescribingTool("My Prescribing App");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            PrescribingTool pt = new PrescribingTool("My Prescribing App");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPrescribingTool.json");
            writer.open();
            writer.write(pt);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPrescribingTool.json");
            pt = reader.read();
            assertEquals("My Prescribing App", pt.getName());
            assertEquals(0, pt.numConditions());
            assertEquals(0, pt.numPatients());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            PrescribingTool pt = new PrescribingTool("My Prescribing App");
            Condition acne = new Condition("acne");
            Condition headache = new Condition("headache");
            Patient emily = new Patient("emily");
            Patient alex = new Patient("alex");
            Drug tretinoin = new Drug("tretinoin");
            Drug acetaminophen = new Drug("acetaminophen");
            acne.addDrug(tretinoin);
            headache.addDrug(acetaminophen);
            tretinoin.addSideEffect("dry skin");
            emily.addDrug(tretinoin);
            alex.addDrug(acetaminophen);
            pt.addCondition(acne);
            pt.addCondition(headache);
            pt.addPatient(emily);
            pt.addPatient(alex);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPrescribingTool.json");
            writer.open();
            writer.write(pt);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPrescribingTool.json");
            pt = reader.read();
            assertEquals("My Prescribing App", pt.getName());
            List<Condition> conditions = pt.getConditions();
            List<Patient> patients = pt.getPatients();
            assertEquals(2, conditions.size());
            assertEquals(2, patients.size());
            assertEquals("emily", patients.get(0).getName());
            assertEquals("alex", patients.get(1).getName());
            assertEquals("acne", conditions.get(0).getName());
            assertEquals("headache", conditions.get(1).getName());
            assertEquals(1, patients.get(0).getDrugs().size());
            assertEquals("tretinoin", patients.get(0).getDrugs().get(0).getName());
            assertEquals("acetaminophen", patients.get(1).getDrugs().get(0).getName());
            assertEquals(1, conditions.get(0).getDrugs().size());
            assertEquals("tretinoin", conditions.get(0).getDrugs().get(0).getName());
            assertEquals("dry skin", patients.get(0).getDrugs().get(0).getSideEffects().get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
