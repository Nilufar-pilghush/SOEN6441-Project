package test.java.warzone.services.impl;

import main.java.warzone.entities.GamePhase;
import main.java.warzone.entities.GameSession;
import main.java.warzone.exceptions.WarzoneValidationException;
import main.java.warzone.services.impl.OrderIssuanceServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the {@link OrderIssuanceServiceImpl} for handling the issue order phase in the game.
 * It verifies that orders can be issued to players without throwing exceptions, ensuring the game phase
 * transitions smoothly. This class sets up a minimal game environment and assesses the service's
 * capability to process user commands for issuing orders.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class OrderIssuanceServiceImplTest {
    private OrderIssuanceServiceImpl d_orderIssuanceService;
    private GameSession d_gameSession;

    /**
     * Initializes the OrderIssuanceServiceImpl and a fresh game session instance for testing.
     */
    public OrderIssuanceServiceImplTest() {
        d_orderIssuanceService = new OrderIssuanceServiceImpl();
        d_gameSession = GameSession.getInstance();
    }

    /**
     * Prepares the game environment before each test by resetting the game session and setting up
     * a basic game world structure with continents, countries, and neighbors.
     *
     * @throws WarzoneValidationException If setup fails due to invalid game world configuration.
     */
    @BeforeEach
    public void setUp() throws WarzoneValidationException {
        d_gameSession.clearPreviousSession();
        d_gameSession.createContinent("Asia", "5");
        d_gameSession.createCountry("Iran", "Asia");
        d_gameSession.createCountry("Turkey", "Asia");
        d_gameSession.makeNeighbors("Iran", "Turkey");
    }

    /**
     * Verifies that the ISSUE_ORDERS phase can be handled without any exceptions being thrown,
     * ensuring the order issuance process is functioning as expected.
     */
    @Test
    public void testOrderIssueHandling() {
        Assertions.assertDoesNotThrow(() -> d_orderIssuanceService.handleGamePhase(GamePhase.ISSUE_ORDERS),
                "Order issuance phase should be handled without throwing exceptions.");
    }

    /**
     * Cleans up the game session by clearing any existing data after each test,
     * ensuring a clean state for subsequent tests.
     */
    @AfterEach
    public void cleanUp() {
        d_gameSession.clearPreviousSession();
    }
}
