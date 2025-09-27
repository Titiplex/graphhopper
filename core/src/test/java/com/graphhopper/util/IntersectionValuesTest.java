package com.graphhopper.util;

import com.graphhopper.util.details.IntersectionValues;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@link IntersectionValues#createList(Map)}.
 *
 * <p>This test verifies that a valid intersection map containing
 * bearings, entries, and in/out indices is correctly converted
 * into a list of {@link IntersectionValues}. It checks that the
 * fields bearing, entry, in, and out are populated as expected
 * for each element.</p>
 */
public class IntersectionValuesTest {

    @Test
    public void testCreateListValid() {
        Map<String, Object> map = new HashMap<>();
        map.put("bearings", Arrays.asList(90, 180, 270));
        map.put("entries", Arrays.asList(true, false, true));
        map.put("in", 1);
        map.put("out", 2);

        List<IntersectionValues> list = IntersectionValues.createList(map);

        assertEquals(3, list.size());

        assertEquals(90, list.get(0).bearing);
        assertTrue(list.get(0).entry);
        assertFalse(list.get(0).in);
        assertFalse(list.get(0).out);

        assertEquals(180, list.get(1).bearing);
        assertFalse(list.get(1).entry);
        assertTrue(list.get(1).in);
        assertFalse(list.get(1).out);

        assertEquals(270, list.get(2).bearing);
        assertTrue(list.get(2).entry);
        assertFalse(list.get(2).in);
        assertTrue(list.get(2).out);
    }
}