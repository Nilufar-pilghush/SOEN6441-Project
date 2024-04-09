package test.java.warzone.entities.players;

import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.players.RandomPlayerStrategy;
import main.java.warzone.exceptions.WarzoneValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the implementation of the {@code RandomPlayerStrategy} in the Warzone game, ensuring that the strategy behaves as expected
 * in a game session. The {@code RandomPlayerStrategy} is designed to simulate a player making decisions randomly, which can be
 * challenging to predict but essential for testing the game's resilience and fairness.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */
public class RandomPlayerStrategyTest {
    /**
     * Current game session
     */
    private GameSession d_GameSession;

    /**
     * Method to set up tests
     *
     * @throws WarzoneValidationException if session creation fails
     */
    @BeforeEach
    public void setUp() throws WarzoneValidationException {
        d_GameSession = GameSession.getInstance();
        d_GameSession.clearPreviousSession();
        d_GameSession.createContinent("Asia", String.valueOf(5));
        d_GameSession.createCountry("Iran", "Asia");
        d_GameSession.createCountry("Turkey", "Asia");
        d_GameSession.makeNeighbors("Iran", "Turkey");
        d_GameSession.createPlayer("Player3");
        d_GameSession.getPlayers().get("Player3").setPlayerStrategy(new RandomPlayerStrategy());
        d_GameSession.getPlayers().get("Player3").addOwnedCountry("Iran");
        d_GameSession.getPlayers().get("Player3").addOwnedCountry("Turkey");
        d_GameSession.getCountriesInSession().get("Iran").setOwner("Player3");
        d_GameSession.getCountriesInSession().get("Turkey").setOwner("AnotherPlayer");
        d_GameSession.getCountriesInSession().get("Turkey").setNumberOfArmies(5);
    }

    /**
     * Method to test issue player order
     */
    @Test
    void testIssuePlayerOrderDoesNotThrowException() {
        RandomPlayerStrategy l_RandomStrategy = new RandomPlayerStrategy();
        assertDoesNotThrow(() -> l_RandomStrategy.issuePlayerOrder(d_GameSession.getPlayers().get("Player3"), d_GameSession));
    }

    /**
     * Method to test get strategy name
     */
    @Test
    void testGetStrategyNameReturnsCorrectString() {
        RandomPlayerStrategy l_RandomStrategy = new RandomPlayerStrategy();
        assertEquals(WarzoneConstants.RANDOM, l_RandomStrategy.getStrategyNameString());
    }

}
