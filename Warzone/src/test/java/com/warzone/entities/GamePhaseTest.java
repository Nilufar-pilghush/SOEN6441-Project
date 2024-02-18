package test.java.com.warzone.entities;
import main.java.warzone.entities.GamePhase;
import main.java.warzone.services.GamePhaseService;
import main.java.warzone.services.impl.*;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class
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
     */
    @Test
    public void GetWarzonePhaseMapEditorTest() {
        GamePhase phase = GamePhase.MAP_EDITOR;
        GamePhaseService service = phase.getWarzonePhase(phase);
        assertTrue(service instanceof MapEditorServiceImpl);
    }

    /**
     */
    @Test
    public void GetWarzonePhaseStartUpTest() {
        GamePhase segment = GamePhase.START_UP;
        GamePhaseService service = segment.getWarzonePhase(segment);
        assertTrue(service instanceof StartupPhaseServiceImpl);
    }

    /**
     */
    @Test
    public void GetWarzoneSegmentReinforcementTest() {
        GamePhase phase = GamePhase.REINFORCEMENT;
        GamePhaseService service = phase.getWarzonePhase(phase);
        assertTrue(service instanceof ReinforcementServiceImpl);
    }

    /**
     */
    @Test
    public void GetWarzoneSegmentIssueOrdersTest() {
        GamePhase phase = GamePhase.ISSUE_ORDERS;
        GamePhaseService service = phase.getWarzonePhase(phase);
        assertTrue(service instanceof OrderIssuanceServiceImpl);
    }

    /**
     */
    @Test
    public void testGetWarzoneSegmentExit() {
        GamePhase phase = GamePhase.EXIT;
        GamePhaseService service = phase.getWarzonePhase(phase);
        assertNull(service);
    }
}
