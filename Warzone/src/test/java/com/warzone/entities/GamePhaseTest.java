package test.java.com.warzone.entities;
import main.java.warzone.entities.GamePhase;
import main.java.warzone.services.GamePhaseService;
import main.java.warzone.services.impl.MapEditorServiceImpl;
import main.java.warzone.services.impl.OrderIssuanceServiceImpl;
import main.java.warzone.services.impl.ReinforcementServiceImpl;
import main.java.warzone.services.impl.StartupPhaseServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

    /**
     * JUnit test cases for the game segment class.
     * Test case to verify get scene editor segment.
     * Test case to verify get startup segment.
     * Test case to verify get reinforcement segment.
     * Test case to verify get issue orders segment.
     * Test case to verify get exit segment.
     *
     * @author Niloufar Pilgush
     * @author Nasrin Maarefi
     * @author Jerome Kithinji
     * @author Ali sayed Salehi
     * @author Fatemeh Chaji
     * @version 1.0.0
     */
    public class GamePhaseTest {

        /**
         * Test case to verify scene editor segment.
         */
        @Test
        public void getWarzoneSegmentSceneEditorTest() {
            GamePhase phase = GamePhase.MAP_EDITOR;
            GamePhaseService service = phase.getWarzonePhase(phase);
            assertTrue(service instanceof MapEditorServiceImpl);
        }

        /**
         * Test case to verify startup segment.
         */
        @Test
        public void getWarzoneSegmentStartUpTest() {
            GamePhase phase = GamePhase.START_UP;
            GamePhaseService service = phase.getWarzonePhase(phase);
            assertTrue(service instanceof StartupPhaseServiceImpl);
        }

        /**
         * Test case to verify get reinforcement segment
         */
        @Test
        public void getWarzoneSegmentReinforcementTest() {
            GamePhase phase = GamePhase.REINFORCEMENT;
            GamePhaseService service = phase.getWarzonePhase(phase);
            assertTrue(service instanceof ReinforcementServiceImpl);
        }

        /**
         * Test case to verify get issue order segment
         */
        @Test
        public void getWarzoneSegmentIssueOrdersTest() {
            GamePhase phase = GamePhase.ISSUE_ORDERS;
            GamePhaseService service = phase.getWarzonePhase(phase);
            assertTrue(service instanceof OrderIssuanceServiceImpl);
        }

        /**
         * Test case to verify exit segment.
         */
        @Test
        public void getWarzoneSegmentExitTest() {
            GamePhase phase = GamePhase.EXIT;
            GamePhaseService service = phase.getWarzonePhase(phase);
            assertNull(service);
        }
    }
