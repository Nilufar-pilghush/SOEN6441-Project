package test.java.warzone.entities.orders.commands;

import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.orders.Order;
import main.java.warzone.entities.orders.commands.AirliftOrderCommand;
import main.java.warzone.exceptions.WarzoneValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of {@link AirliftOrderCommand} in the context of a game session.
 * Ensures that airlift orders can be executed properly according to the game rules,
 * including transferring armies between countries owned by the same or different players.
 */
public class AirliftOrderCommandTest {
    private Order d_order;
    private GameSession d_gameSession;

    @BeforeEach
    public void setUp() throws WarzoneValidationException {
        d_gameSession = GameSession.getInstance();
        d_gameSession.clearPreviousSession();
        initializeGameSession();
    }

    private void initializeGameSession() throws WarzoneValidationException {
        d_gameSession.createContinent("Asia", "10");
        d_gameSession.createCountry("Iran", "Asia");
        d_gameSession.createCountry("Turkey", "Asia");
        d_gameSession.createPlayer("Player1");
        d_gameSession.createPlayer("Player2");
        d_gameSession.getCountriesInSession().get("Iran").setOwner("Player1");
        d_gameSession.getCountriesInSession().get("Turkey").setOwner("Player2");
        d_order = new AirliftOrderCommand("Player1", "Iran", "Turkey", 10);
    }

    /**
     * Tests that executing an AirliftOrderCommand does not throw exceptions
     * under valid conditions, including correct ownership and valid target countries.
     */
    @Test
    public void testAirliftOrderExecution() {
        Assertions.assertDoesNotThrow(() -> d_order.execute(d_gameSession),
                "Executing the airlift order should not throw exceptions.");
    }
}
