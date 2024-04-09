package test.java.warzone.entities.orders.commands;

import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.orders.Order;
import main.java.warzone.entities.orders.commands.BombOrderCommand;
import main.java.warzone.exceptions.WarzoneValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link BombOrderCommand} functionality within a game session.
 * Ensures that bomb orders can be executed properly under various game states,
 * adhering to game rules and constraints.
 */
public class BombOrderCommandTest {
    private Order d_order;
    private GameSession d_gameSession;

    /**
     * Prepares the game environment before each test by resetting the game session
     * and setting up necessary game entities such as continents, countries, and players.
     *
     * @throws WarzoneValidationException if setup fails due to game rule violations.
     */
    @BeforeEach
    public void setUp() throws WarzoneValidationException {
        d_gameSession = GameSession.getInstance();
        d_gameSession.clearPreviousSession();
//        initializeGameWorld();
    }

    /**
     * Prepares a basic game world with necessary entities for executing a bomb order.
     * This includes creating continents, countries, players, and assigning countries.
     *
     * @throws WarzoneValidationException if initializing the game world fails.
     */
//    private void initializeGameWorld() throws WarzoneValidationException {
//        d_gameSession.createContinent("Asia", "10");
//        d_gameSession.createCountry("Iran", "Asia");
//        d_gameSession.createCountry("Turkey", "Asia");
//        d_gameSession.createPlayer("Player1");
//        d_gameSession.createPlayer("Player2");
//        d_gameSession.getCountriesInSession().get("Iran").setOwner("Player1");
//        d_gameSession.getCountriesInSession().get("Turkey").setOwner("Player2");
//        d_order = new BombOrderCommand("Player1", "Turkey");
//    }

    /**
     * Verifies that executing a bomb order does not result in exceptions under valid conditions,
     * indicating it behaves as expected within the game's rules.
     */
    @Test
    public void testBombOrderExecution() {
        Assertions.assertDoesNotThrow(() -> d_order.execute(d_gameSession), "Bomb order should execute without exceptions.");
    }
}
