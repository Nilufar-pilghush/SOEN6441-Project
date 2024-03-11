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
     * Tests for validating the functionality of the {@link GamePhase} enumeration in the context of retrieving the correct
     * {@link GamePhaseService} implementation for each game phase. Each test verifies the association between a game phase
     * and its corresponding service implementation, ensuring the game phase management system operates as intended.
     *
     * @author Niloufar Pilgush
     * @author Nasrin Maarefi
     * @author Jerome Kithinji
     * @author Ali sayed Salehi
     * @author Fatemeh Chaji
     * @version 2.0.0
     */
    public class GamePhaseTest {
        /**
         * Validates that the Map Editor phase returns an instance of {@link MapEditorServiceImpl}.
         * This test ensures that the game phase logic correctly initializes map editing functionalities.
         */

        @Test
        public void testGetWarzoneSegmentSceneEditor() {
            GamePhase phase = GamePhase.MAP_EDITOR;
            GamePhaseService service = phase.getWarzonePhase(phase);
            assertTrue(service instanceof MapEditorServiceImpl);
        }

        /**
         * Validates that the Startup phase returns an instance of {@link StartupPhaseServiceImpl}.
         * This test ensures that the game phase logic correctly initializes startup functionalities.
         */

        @Test
        public void testGetWarzoneSegmentStartUp() {
            GamePhase phase = GamePhase.START_UP;
            GamePhaseService service = phase.getWarzonePhase(phase);
            assertTrue(service instanceof StartupPhaseServiceImpl);
        }

        /**
         * Validates that the Reinforcement phase returns an instance of {@link ReinforcementServiceImpl}.
         * This test ensures that the game phase logic correctly initializes reinforcement functionalities.
         */

        @Test
        public void testGetWarzoneSegmentReinforcement() {
            GamePhase phase = GamePhase.REINFORCEMENT;
            GamePhaseService service = phase.getWarzonePhase(phase);
            assertTrue(service instanceof ReinforcementServiceImpl);
        }

        /**
         * Validates that the Issue Orders phase returns an instance of {@link OrderIssuanceServiceImpl}.
         * This test ensures that the game phase logic correctly initializes order issuance functionalities.
         */

        @Test
        public void testGetWarzoneSegmentIssueOrders() {
            GamePhase phase = GamePhase.ISSUE_ORDERS;
            GamePhaseService service = phase.getWarzonePhase(phase);
            assertTrue(service instanceof OrderIssuanceServiceImpl);
        }

        /**
         * Validates that the Exit phase does not return a service instance.
         * This test ensures that the game phase logic correctly handles the exit condition with no associated service.
         */

        @Test
        public void testGetWarzoneSegmentExit() {
            GamePhase phase = GamePhase.EXIT;
            GamePhaseService service = phase.getWarzonePhase(phase);
            assertNull(service);
        }
    }
