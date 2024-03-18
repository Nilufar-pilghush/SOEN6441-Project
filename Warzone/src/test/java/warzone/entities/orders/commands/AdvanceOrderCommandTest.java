package test.java.warzone.entities.orders.commands;

import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.orders.Order;
import main.java.warzone.entities.orders.commands.AdvanceOrderCommand;
import main.java.warzone.exceptions.WarzoneValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the {@link AdvanceOrderCommand} in a {@link GameSession}, ensuring it executes correctly
 * within game rules. It checks the command's behavior in a simulated game setup by creating a game
 * environment, issuing orders, and verifying their execution without errors. This class aims to
 * validate the correct application of advance orders, simulating a scenario close to actual gameplay.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class AdvanceOrderCommandTest {
    private Order d_order;
    private GameSession d_gameSession;

    /**
     * Initializes a new game session and an advance order before each test.
     */
    @BeforeEach
    public void setUp() throws WarzoneValidationException {
        d_gameSession = GameSession.getInstance();
        d_gameSession.clearPreviousSession();
        d_order = new AdvanceOrderCommand("Player1", "Iran", "Canada", 10);
        testSetupGameEnvironment();
    }
    /**
     * Configures the game environment required for testing the advance order execution.
     * This involves creating continents, countries, setting country ownership, and defining
     * country relationships such as neighbors. This setup aims to create a controlled game
     * scenario to accurately test the advance order functionality.
     *
     * @throws WarzoneValidationException If there is an issue with creating the game environment,
     * such as invalid continent or country names, or issues with setting neighbors.
     */
    private void testSetupGameEnvironment() throws WarzoneValidationException {
        d_gameSession.createContinent("Asia", "10");
        d_gameSession.createCountry("Iran", "Asia");
        d_gameSession.createCountry("Turkey", "Asia");
        d_gameSession.makeNeighbors("Iran", "Turkey");
        d_gameSession.createPlayer("Player1");
        d_gameSession.getCountriesInSession().get("Iran").setOwner("Player1");
        d_gameSession.getCountriesInSession().get("Turkey").setOwner("Player1");
        d_order = new AdvanceOrderCommand("Player1", "Iran", "Turkey", 10);
    }

    /**
     * Verifies that an advance order can be executed without throwing exceptions, ensuring it
     * adheres to game rules. The test simulates adding players, countries, and their relationships,
     * then executes an order, checking for successful execution.
     *
     * @throws WarzoneValidationException if the order execution violates game rules.
     */
    @Test
    public void testAdvanceOrderExecution() {
        Assertions.assertDoesNotThrow(() -> d_order.execute(d_gameSession),
                "Advance order should execute without throwing exceptions under valid conditions.");
    }
}
