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
    }

}
