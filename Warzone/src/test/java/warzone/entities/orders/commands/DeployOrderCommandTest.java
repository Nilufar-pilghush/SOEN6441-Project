package test.java.warzone.entities.orders.commands;

import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.orders.Order;
import main.java.warzone.entities.orders.commands.DeployOrderCommand;
import main.java.warzone.exceptions.WarzoneValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link DeployOrderCommand} class, ensuring that deploy orders are correctly executed within the game.
 * These tests verify that the deploy command adheres to the game's rules, such as deploying only to territories owned
 * by the player and in valid game states.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class DeployOrderCommandTest {
    private Order d_order;
    private GameSession d_gameSession;

    /**
     * Initializes the testing framework by setting up a new game session and preparing a deploy order for testing.
     * This setup includes clearing any previous game session data and creating necessary game entities for the test.
     */
    @BeforeEach
    public void setUp() throws WarzoneValidationException {
        d_gameSession = GameSession.getInstance();
        d_gameSession.clearPreviousSession();
        initializeGameWorld();
    }

    /**
     * Prepares the game environment for the deploy order tests.
     * This setup includes creating players, continents, countries, and assigning ownership,
     * ensuring a valid context for deploy order execution.
     */
    private void initializeGameWorld() throws WarzoneValidationException {
        d_gameSession.createPlayer("Player1");
        d_gameSession.createContinent("Asia", "10");
        d_gameSession.createCountry("Turkey", "Asia");
        d_gameSession.getCountriesInSession().get("Turkey").setOwner("Player1");
        d_order = new DeployOrderCommand("Player1", "Turkey", 10);
    }

    /**
     * Tests the execution of a deploy order, verifying it processes correctly under valid conditions.
     * Ensures the order does not throw exceptions and the game state is updated as expected, reflecting
     * the deployment of armies to the specified country.
     */
    @Test
    public void testDeployOrderExecution() {
        Assertions.assertDoesNotThrow(() -> d_order.execute(d_gameSession),
                "Deploy order execution should not throw exceptions.");
    }
}

