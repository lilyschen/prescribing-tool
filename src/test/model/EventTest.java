package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

// referenced Alarm System: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
public class EventTest {
    private Event e;
    private Date d;

    @BeforeEach
    public void runBefore() {
        e = new Event("Added drug: tretinoin to acne");
        d = Calendar.getInstance().getTime();
    }

    @Test
    public void testEvent() {
        assertEquals("Added drug: tretinoin to acne", e.getDescription());
        assertEquals(d.getDay(), e.getDate().getDay());
        assertEquals(d.getMonth(), e.getDate().getMonth());
        assertEquals(d.getYear(), e.getDate().getYear());
        assertEquals(d.getHours(), e.getDate().getHours());
        assertEquals(d.getMinutes(), e.getDate().getMinutes());
        assertEquals(d.getSeconds(), e.getDate().getSeconds());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Added drug: tretinoin to acne", e.toString());
    }

    @Test
    public void testHashCode() {
        assertEquals(13 * d.hashCode() + e.getDescription().hashCode(), e.hashCode());
    }

    @Test
    public void testEquals() {
        assertFalse(e.equals(null));
        assertFalse(e.equals(e.getDescription()));
        assertTrue(e.equals(e));

        Event e2 = new Event("Added acetaminophen to headache");
        assertFalse(e.equals(e2));
    }
}
