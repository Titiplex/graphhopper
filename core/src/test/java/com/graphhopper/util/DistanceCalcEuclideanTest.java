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

import com.graphhopper.util.shapes.GHPoint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DistanceCalcEuclideanTest {

    @Test
    public void testCrossingPointToEdge() {
        DistanceCalcEuclidean distanceCalc = new DistanceCalcEuclidean();
        GHPoint point = distanceCalc.calcCrossingPointToEdge(0, 10, 0, 0, 10, 10);
        assertEquals(5, point.getLat(), 0);
        assertEquals(5, point.getLon(), 0);
    }

    @Test
    public void testCalcNormalizedEdgeDistance() {
        DistanceCalcEuclidean distanceCalc = new DistanceCalcEuclidean();
        double distance = distanceCalc.calcNormalizedEdgeDistance(0, 10, 0, 0, 10, 10);
        assertEquals(50, distance, 0);
    }

    @Test
    public void testCalcNormalizedEdgeDistance3dStartEndSame() {
        DistanceCalcEuclidean distanceCalc = new DistanceCalcEuclidean();
        double distance = distanceCalc.calcNormalizedEdgeDistance3D(0, 3, 4, 0, 0, 0, 0, 0, 0);
        assertEquals(25, distance, 0);
    }

    @Test
    public void testValidEdgeDistance() {
        DistanceCalcEuclidean distanceCalc = new DistanceCalcEuclidean();
        boolean validEdgeDistance = distanceCalc.validEdgeDistance(5, 15, 0, 0, 10, 10);
        assertEquals(false, validEdgeDistance);
        validEdgeDistance = distanceCalc.validEdgeDistance(15, 5, 0, 0, 10, 10);
        assertEquals(false, validEdgeDistance);
    }

    @Test
    public void testDistance3dEuclidean() {
        DistanceCalcEuclidean distCalc = new DistanceCalcEuclidean();
        assertEquals(1, distCalc.calcDist3D(
                0, 0, 0,
                0, 0, 1
        ), 1e-6);
        assertEquals(10, distCalc.calcDist3D(
                0, 0, 0,
                0, 0, 10
        ), 1e-6);
    }

    /**
     * Unit test for the {@link DistanceCalcEuclidean#calcDist(double, double, double, double)} method.
     * <p>
     * This test verifies that the method correctly calculates the Euclidean distance between two points
     * in 2D space. It uses the points (0,0) and (4,3), for which the expected distance is 5, according
     * to the Pythagorean theorem: sqrt((4-0)^2 + (3-0)^2) = 5.
     * <p>
     * The objective of this test is to ensure that {@code calcDist} computes distances accurately
     * for basic coordinates and handles standard 2D points in real number space.
     */
    @Test
    public void testDistanceEuclidean(){
        DistanceCalcEuclidean distCalc = new DistanceCalcEuclidean();

        double fromY = 0.0;
        double fromX = 0.0;
        double toY = 3.0;
        double toX = 4.0;
    
        double calculatedDistance = distCalc.calcDist(fromY, fromX, toY, toX);
    
        // Expected distance = sqrt((3-0)^2 + (4-0)^2) = 5
        assertEquals(5.0, calculatedDistance, 1e-6);
    }

    /**
     * Unit test for the {@link #intermediatePoint(double, double, double, double, double)} method.
     * <p>
     * This test verifies that the method correctly computes a point along the straight line connecting
     * two geographic coordinates (latitude and longitude) at a given fraction {@code f}.
     * </p>
     * <p>
     * Specifically, it checks:
     * <ul>
     *   <li>When {@code f = 0.0}, the returned point matches the start point.</li>
     *   <li>When {@code f = 1.0}, the returned point matches the end point.</li>
     *   <li>When {@code f = 0.5}, the returned point matches the midpoint between start and end points.</li>
     * </ul>
     * </p>
     * <p>
     * The test ensures that linear interpolation is performed correctly for coordinates in 2D
     * geographic space, which is essential for GraphHopper when calculating points along edges
     * or routes.
     * </p>
     */
    @Test
    public void testIntermediatePoint() {
        DistanceCalcEuclidean distCalc = new DistanceCalcEuclidean(); // or the class implementing it
    
        double lat1 = 10.0;
        double lon1 = 20.0;
        double lat2 = 14.0;
        double lon2 = 28.0;
    
        // f = 0  start point
        GHPoint start = distCalc.intermediatePoint(0.0, lat1, lon1, lat2, lon2);
        assertEquals(lat1, start.getLat(), 1e-6);
        assertEquals(lon1, start.getLon(), 1e-6);
    
        // f = 1  end point
        GHPoint end = distCalc.intermediatePoint(1.0, lat1, lon1, lat2, lon2);
        assertEquals(lat2, end.getLat(), 1e-6);
        assertEquals(lon2, end.getLon(), 1e-6);
    
        // f = 0.5  midpoint
        GHPoint mid = distCalc.intermediatePoint(0.5, lat1, lon1, lat2, lon2);
        assertEquals((lat1 + lat2) / 2, mid.getLat(), 1e-6);
        assertEquals((lon1 + lon2) / 2, mid.getLon(), 1e-6);
    }
}
