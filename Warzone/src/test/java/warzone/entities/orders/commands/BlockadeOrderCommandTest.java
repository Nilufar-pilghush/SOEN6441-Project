package test.java.warzone.entities.orders.commands;

import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.orders.Order;
import main.java.warzone.entities.orders.commands.BlockadeOrderCommand;
import main.java.warzone.exceptions.WarzoneValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link BlockadeOrderCommand} functionality within the game.
 * This suite ensures that the blockade order can be executed properly under various game states,
 * confirming that it adheres to the rules and constraints defined for the game.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class BlockadeOrderCommandTest {
    private Order d_order;
    private GameSession d_gameSession;

    /**
     * Sets up the game environment for blockade order testing.
     * Initializes game session, creates continents, countries, sets country ownership, and prepares a blockade order.
     *
     * @throws WarzoneValidationException if there's an issue creating the game setup.
     */
    @BeforeEach
    public void setUp() throws WarzoneValidationException {
        d_gameSession = GameSession.getInstance();
        d_gameSession.clearPreviousSession();
        d_gameSession.createContinent("Asia", "10");
        d_gameSession.createCountry("Iran", "Asia");
        d_gameSession.createCountry("Turkey", "Asia");
        d_gameSession.getCountriesInSession().get("Iran").setOwner("Player1");
        d_gameSession.getCountriesInSession().get("Turkey").setOwner("Player2");
        d_gameSession.makeNeighbors("Iran", "Turkey");
        d_order = new BlockadeOrderCommand("Player1", "Turkey");
    }

    /**
     * Tests the execution of a blockade order, verifying that it can be performed without throwing exceptions.
     * This includes modifying the game state according to the order and ensuring that the action respects game rules.
     *
     * @throws WarzoneValidationException if the execution of the blockade order fails to meet game validation.
     */
    @Test
    public void testBlockadeOrderExecution() throws WarzoneValidationException {
        d_gameSession.createPlayer("Player2"); // Ensure all necessary players exist for the test scenario.
        Assertions.assertDoesNotThrow(() -> d_order.execute(d_gameSession), "Blockade order should execute without exceptions.");
    }
}
