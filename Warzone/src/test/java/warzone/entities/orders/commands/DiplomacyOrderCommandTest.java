package test.java.warzone.entities.orders.commands;

import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.orders.Order;
import main.java.warzone.entities.orders.commands.DiplomacyOrderCommand;
import main.java.warzone.exceptions.WarzoneValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link DiplomacyOrderCommand} class to verify the functionality of diplomacy orders within the game.
 * Ensures that diplomacy orders can be executed correctly, affecting the game state as expected without throwing exceptions.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class DiplomacyOrderCommandTest {
    private Order d_order;
    private GameSession d_gameSession;

    /**
     * Prepares the testing environment by initializing a new game session and
     * setting up the required game world elements for the diplomacy order test.
     * This includes clearing any pre-existing data to ensure a clean state for each test.
     *
     * @throws WarzoneValidationException If there is a problem initializing the game session.
     */
    @BeforeEach
    public void setUp() throws WarzoneValidationException {
        d_gameSession = GameSession.getInstance();
        d_gameSession.clearPreviousSession();
        initializeGameWorld();
    }

    /**
     * Configures the game world with essential components, such as continents,
     * countries, and players. This setup is crucial for testing the diplomacy order
     * as it ensures the presence of two distinct players and countries, mirroring
     * a real game scenario where diplomacy might be invoked.
     *
     * @throws WarzoneValidationException If there is an issue setting up the game world components.
     */
    private void initializeGameWorld() throws WarzoneValidationException {
        d_gameSession.createContinent("Asia", "10");
        d_gameSession.createCountry("Iran", "Asia");
        d_gameSession.createCountry("Turkey", "Asia");
        d_gameSession.createPlayer("Player1");
        d_gameSession.createPlayer("Player2");
        d_gameSession.getCountriesInSession().get("Iran").setOwner("Player1");
        d_gameSession.getCountriesInSession().get("Turkey").setOwner("Player2");
        d_gameSession.makeNeighbors("Iran", "Turkey");
        d_order = new DiplomacyOrderCommand("Player1", "Player2");
    }

    /**
     * Tests the execution of the diplomacy order, ensuring it can be carried out successfully
     * according to the rules of the game. This test specifically checks that the order does not
     * result in exceptions, indicating a smooth integration within the game's logic and
     * adherence to its rules regarding diplomatic actions.
     *
     * @throws WarzoneValidationException If the execution of the diplomacy order contravenes game rules.
     */
    @Test
    public void testDiplomacyOrderExecution() throws WarzoneValidationException {
        Assertions.assertDoesNotThrow(() -> d_order.execute(d_gameSession),
                "Diplomacy order should execute without exceptions.");
    }
}