package test.java.warzone.entities.players;

import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.players.CheaterPlayerStrategy;
import main.java.warzone.exceptions.WarzoneValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *Tests the functionalities associated with the {@code CheaterPlayerStrategy} class in the Warzone game framework,
 * ensuring that the cheater strategy is implemented correctly according to the game's rules and requirements.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */
public class CheaterPlayerStrategyTest {

    /**
     * Game session
     */
    private GameSession d_GameSession;

    /**
     * Method to set up tests
     *
     * @throws WarzoneValidationException if game session creation fails
     */
    @BeforeEach
    public void setUp() throws WarzoneValidationException {
        d_GameSession = GameSession.getInstance();
        d_GameSession.clearPreviousSession();
        d_GameSession.createContinent("Asia", String.valueOf(5));
        d_GameSession.createCountry("Iran", "Asia");
        d_GameSession.createCountry("Turkey", "Asia");
        d_GameSession.makeNeighbors("Iran", "Turkey");
        d_GameSession.createPlayer("player1");
        d_GameSession.getPlayers().get("player1").setPlayerStrategy(new CheaterPlayerStrategy());
        d_GameSession.getPlayers().get("player1").addOwnedCountry("Iran");
        d_GameSession.getPlayers().get("player1").addOwnedCountry("Turkey");
        d_GameSession.getCountriesInSession().get("Iran").setOwner("player1");
        d_GameSession.getCountriesInSession().get("Turkey").setNumberOfArmies(5);
    }

    /**
     * Method to test issue player order
     */
    @Test
    void testIssuePlayerOrderExecutesAsExpected() {
        CheaterPlayerStrategy l_CheaterStrategy = new CheaterPlayerStrategy();
        assertDoesNotThrow(() -> l_CheaterStrategy.issuePlayerOrder(d_GameSession.getPlayers().get("player1"), d_GameSession));
        assertEquals("player1", d_GameSession.getCountriesInSession().get("Iran").getOwner());
        assertEquals("player1", d_GameSession.getCountriesInSession().get("Turkey").getOwner());
        assertEquals(1, d_GameSession.getCountriesInSession().get("Turkey").getNumberOfArmies());
    }

    /**
     * Method to test get strategy name
     */
    @Test
    void testGetStrategyNameReturnsExpectedString() {
        CheaterPlayerStrategy l_CheaterStrategy = new CheaterPlayerStrategy();
        assertEquals(WarzoneConstants.CHEATER, l_CheaterStrategy.getStrategyNameString());
    }
}
