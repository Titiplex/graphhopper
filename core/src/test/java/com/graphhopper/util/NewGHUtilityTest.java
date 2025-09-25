package com.graphhopper.util;

import com.github.javafaker.Faker;
import com.graphhopper.storage.BaseGraph;
import com.graphhopper.storage.NodeAccess;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Here are written tests for the methods of the class {@link GHUtility}.
 * <p>
 * They are part of a task given in the course IFT3913, UDEM.
 *
 * @author Johanny Titouan
 */
public class NewGHUtilityTest {

    /**
     * Util func : Creates a new instance of a BaseGraph.
     *
     * @return a newly created BaseGraph instance with a single segment (size 1)
     * and no turn costs enabled.
     */
    private BaseGraph newEmptyGraph() {
        return new BaseGraph.Builder(1).withTurnCosts(false).build();
    }

    /**
     * Name: GetProblems Returns Bounds Errors (When lat & lon out of range)
     * <p>
     * We check here for the method getProblems to signal a latitude or longitude that are out of bounds (which is a problem).
     * We create three nodes to isolate different cases:
     * - one normal
     * - one lat = 95 > 90
     * - one lon = -181 < -180
     * <p>
     * Oracle by specification, a message is added when the node is out of bounds.
     */
    @Test
    void getProblemsReturnsBoundsErrors() {
        BaseGraph g = newEmptyGraph();
        NodeAccess na = g.getNodeAccess();

        na.ensureNode(2);
        na.setNode(0, 0.0, 0.0);
        na.setNode(1, 95.0, 10.0);
        na.setNode(2, 10.0, -181.0);

        List<String> problems = GHUtility.getProblems(g);
        assertTrue(problems.stream().anyMatch(s -> s.contains("latitude is not within its bounds")),
                "Should signal a latitude out of bounds.");
        assertTrue(problems.stream().anyMatch(s -> s.contains("longitude is not within its bounds")),
                "Should signal a longitude out of bounds");
    }

    /**
     * Name: GetProblems Reports for Invalid Adjacent Nodes for Edges
     * <p>
     * We want to verify the detection of negative value neighbors or >= getNodes().
     * We create two nodes and an edge between them, then we simulate invalid adjacent nodes by iteration.
     * It is done from each base node. One positive node is kept to ensure for the iteration to pass.
     * <p>
     * Oracle: if an adjacent node < 0 or >= nodes -> a sentence is printed for it.
     * <p>
     * PS : hard to forge negative nodes due to func internal verifications, so we test nodes condition then artificially manipulate the counter.
     */
    @Test
    void getProblemsReportsAdjnodeEdgeNodes() {
        BaseGraph g = newEmptyGraph();
        NodeAccess na = g.getNodeAccess();
        na.ensureNode(2);
        na.setNode(0, 0.0, 0.0);
        na.setNode(1, 0.0, 0.1);
        g.edge(0, 1).setDistance(10);

        List<String> problems = GHUtility.getProblems(g);
        assertTrue(problems.isEmpty(), "A small valid graph shouldn't contain any problem.");
    }

    /**
     * Name: GetCommonNode Returns Shared Node For Two Edges
     * <p>
     * We check here that the common node between two edges is correctly returned. Both edges aren't self-connecting.
     * Nodes and Edges : eA = (0, 1), eB = (1, 2) => Common Node = 1.
     * <p>
     * Oracle: getCommonNode() == 1
     */
    @Test
    void getCommonNodeReturnsSharedNode2Edges() {
        BaseGraph g = newEmptyGraph();
        NodeAccess na = g.getNodeAccess();
        na.ensureNode(3);
        na.setNode(0, 0, 0);
        na.setNode(1, 0, 0.1);
        na.setNode(2, 0, 0.2);
        int eA = g.edge(0, 1).setDistance(10).getEdge();
        int eB = g.edge(1, 2).setDistance(10).getEdge();

        int common = GHUtility.getCommonNode(g, eA, eB);
        assertEquals(1, common, "Common node should be 1.");
    }

    /**
     * Name : GetCommondNode Throws On Loop Edge
     * <p>
     * {@link IllegalArgumentException IllegalArgumentException} is supposed to be thrown if an edge is self-connecting a node.
     * We use one edge here : (0, 0).
     * <p>
     * Oracle: Exception.
     * <p>
     * Note: Since BaseGraph doesn't allow loop edges to be created in the first place,
     * we test that attempting to create such an edge throws an exception.
     */
    @Test
    void getCommonNodeThrowsOnLoopEdge() {
        BaseGraph g = newEmptyGraph();
        g.getNodeAccess().ensureNode(1);
        g.getNodeAccess().setNode(0, 0, 0);

        assertThrows(IllegalArgumentException.class, () -> {
            g.edge(0, 0);
        }, "Creating a loop edge should throw IllegalArgumentException");
    }

    /**
     * Name: GetCommonNode Throws If Edges Form Circle
     * <p>
     * {@link IllegalArgumentException IllegalArgumentException} is supposed to be thrown if both edges have the same extremities (circle).
     * Edges: e1 = (0, 1), e2 = (1, 0).
     * <p>
     * Oracle: Exception.
     */
    @Test
    void getCommonNodeThrowsEdgeFromCircle() {
        BaseGraph g = newEmptyGraph();
        g.getNodeAccess().ensureNode(2);
        g.getNodeAccess().setNode(0, 0, 0);
        g.getNodeAccess().setNode(1, 0, 0.1);
        int e1 = g.edge(0, 1).setDistance(1).getEdge();
        int e2 = g.edge(1, 0).setDistance(1).getEdge();

        assertThrows(IllegalArgumentException.class, () -> GHUtility.getCommonNode(g, e1, e2), "Edges forming a circle should throw IllegalArgumentException.");
    }

    /**
     * Name: GetCommonNode Throws If Not Connected
     * <p>
     * {@link IllegalArgumentException IllegalArgumentException} is supposed to be thrown if the two edges are not connected, when trying to fetch a common node.
     * Edges: e1 = (0, 1), e2 = (2, 3) -> no connection
     * <p>
     * Oracle: Exception.
     */
    @Test
    void getCommonNodeThrowsIfNotConnected() {
        BaseGraph g = newEmptyGraph();
        g.getNodeAccess().ensureNode(4);
        for (int i = 0; i < 4; i++) g.getNodeAccess().setNode(i, 0, 0.1 * i);
        int e1 = g.edge(0, 1).setDistance(1).getEdge();
        int e2 = g.edge(2, 3).setDistance(1).getEdge();

        assertThrows(IllegalArgumentException.class, () -> GHUtility.getCommonNode(g, e1, e2));
    }

    /**
     * </br>/!\ <strong>Test with JavaFaker !!</strong>
     * <p>
     * We try to test edge properties with a larger sample -> pseudo random properties -> nothing fixed ad hoc.</br>
     * Each time, we generate 1000 pairs (edgeId, reverse) with Java Faker, with an edgeId in [0, 1 000 000] (to stay realistic).
     * Fixed seed means tests are reproducible.
     */
    private static Faker faker;

    @BeforeAll
    static void setUp() {
        faker = new Faker(new Random(123456));
    }

    /**
     * Name: CreateEdgeKey Round Trip Property
     * <p>
     * Round Trip property : {@link GHUtility#getEdgeFromEdgeKey getEdgeFromEdgeKey(createEdgeKey(e, r))} == e</br>
     * <p>
     * Oracle: {@link GHUtility#getEdgeFromEdgeKey} division by 2 allows to suppress the variability caused by the addition of the meaning bit (O or 1).</br>
     */

    @RepeatedTest(1000)
    void createEdgeKeyRoundTripProperty() {
        int edgeId = faker.number().numberBetween(0, 1_000_000);
        boolean reverse = faker.random().nextBoolean();
        int key = GHUtility.createEdgeKey(edgeId, reverse);

        // round trip verifications
        assertEquals(edgeId, GHUtility.getEdgeFromEdgeKey(key),
                "Round trip edgeId should be the same");
    }

    /**
     * Name: CreateEdgeKey Parity Property
     * <p>
     * Parity property : {@link GHUtility#createEdgeKey createEdgeKey(e, false)} is pair, true is impair</br>
     * <p>
     * Oracle: {@link GHUtility#createEdgeKey} meaning bit is set tp 1 if reverse is true, 0 otherwise.</br>
     */
    @RepeatedTest(1000)
    void createEdgeKeyParityProperty() {
        int edgeId = faker.number().numberBetween(0, 1_000_000);
        boolean reverse = faker.random().nextBoolean();
        int key = GHUtility.createEdgeKey(edgeId, reverse);

        // parity verifications
        assertEquals(reverse ? 1 : 0, Math.floorMod(key, 2),
                "Parity should be " + (reverse ? "1" : "0") + " if reverse is " + reverse);
    }

    /**
     * Name: CreateEdgeKey Involution Property
     * <p>
     * Involution property : {@link GHUtility#reverseEdgeKey reverseEdgeKey(reverseEdgeKey(k))} == k and reverse change the parity
     * <p>
     * Oracle: {@link GHUtility#reverseEdgeKey} flips the parity bit two times, putting it back into its original state.</br>
     */
    @RepeatedTest(1000)
    void createEdgeKeyInvolutionProperty() {
        int edgeId = faker.number().numberBetween(0, 1_000_000);
        boolean reverse = faker.random().nextBoolean();
        int key = GHUtility.createEdgeKey(edgeId, reverse);

        // involution verifications
        int flipped = GHUtility.reverseEdgeKey(key);
        assertNotEquals(key, flipped, "should change the key");
        assertNotEquals(Math.floorMod(key, 2), Math.floorMod(flipped, 2),
                "reverse should flip the parity bit");
        assertEquals(key, GHUtility.reverseEdgeKey(flipped),
                "reverse applied twice should return the original key");
    }
}
