/*
 *  Licensed to GraphHopper GmbH under one or more contributor
 *  license agreements. See the NOTICE file distributed with this work for
 *  additional information regarding copyright ownership.
 *
 *  GraphHopper GmbH licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except in
 *  compliance with the License. You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.graphhopper.util;

import com.carrotsearch.hppc.IntArrayList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Here are written tests for the methods of the class {@link ArrayUtil}.
 * <p>
 * They are part of a task given in the course IFT3913, UDEM.
 *
 * @author Mohamed Terbaoui
 */
class NewArrayUtilTest {
    /**
     * Tests that {@code subList} correctly returns a sublist from a normal range of indices.
     * <p>
     * Example: from the list [1, 2, 3, 4, 5], a sublist from index 1 to 4
     * should return [2, 3, 4].
     * </p>
     */
    @Test
    public void testSubListNormal() {
        IntArrayList original = IntArrayList.from(1, 2, 3, 4, 5);

        IntArrayList sub = ArrayUtil.subList(original, 1, 4);

        // Expected: [2, 3, 4]
        assertEquals(IntArrayList.from(2, 3, 4), sub);
    }

        /**
     * Tests that {@code subList} returns the full list when the range covers all elements.
     * <p>
     * Example: from the list [1, 2, 3], a sublist from index 0 to size() should return [1, 2, 3].
     * </p>
     */
    @Test
    public void testSubListFull() {
        IntArrayList original = IntArrayList.from(1, 2, 3);
        IntArrayList sub = ArrayUtil.subList(original, 0, original.size());

        // Expected: [1, 2, 3]
        assertEquals(IntArrayList.from(1, 2, 3), sub);
    }

        /**
     * Tests that {@code subList} returns an empty list when fromIndex equals toIndex.
     * <p>
     * Example: from the list [1, 2, 3], a sublist from index 2 to 2 should return an empty list.
     * </p>
     */
    @Test
    public void testSubListEmpty() {
        IntArrayList original = IntArrayList.from(1, 2, 3);
        IntArrayList sub = ArrayUtil.subList(original, 2, 2);

        // Expected: empty list
        assertEquals(IntArrayList.from(), sub);
    }
}
