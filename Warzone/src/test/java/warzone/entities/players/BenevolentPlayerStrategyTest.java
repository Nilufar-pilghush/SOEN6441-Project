package test.java.warzone.entities.players;

import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.players.BenevolentPlayerStrategy;
import main.java.warzone.exceptions.WarzoneValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**

 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */
public class BenevolentPlayerStrategyTest {

    /**
     * Current game world
     */
    private GameSession d_GameSession;

    /**
     * Method to set up tests
     *
     * @throws WarzoneValidationException
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
        d_GameSession.getPlayers().get("Player3").setPlayerStrategy(new BenevolentPlayerStrategy());
        d_GameSession.getPlayers().get("Player3").addOwnedCountry("Iran");
        d_GameSession.getPlayers().get("Player3").addOwnedCountry("Turkey");
        d_GameSession.getCountriesInSession().get("Iran").setOwner("Player3");
        d_GameSession.getCountriesInSession().get("Turkey").setOwner("Player3");
        d_GameSession.getCountriesInSession().get("Turkey").setNumberOfArmies(10);
    }

    /**
     * Method to test issue player order
     */
    @Test
    void whenTestIssuePlayerOrder_ExpectOrder() {
        BenevolentPlayerStrategy l_BenevolentStrategy = new BenevolentPlayerStrategy();
        assertDoesNotThrow(() -> l_BenevolentStrategy.issuePlayerOrder(d_GameSession.getPlayers().get("Player3"), d_GameSession));
    }

    /**
     * Method to test get strategy name
     */
    @Test
    void whenTestGetStrategyName_ExpectString() {
        BenevolentPlayerStrategy l_BenevolentStrategy = new BenevolentPlayerStrategy();
        assertEquals(WarzoneConstants.BENEVOLENT, l_BenevolentStrategy.getStrategyNameString());
    }
}
