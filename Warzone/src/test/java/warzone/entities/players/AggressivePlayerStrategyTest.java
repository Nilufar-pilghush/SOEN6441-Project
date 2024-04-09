package test.java.warzone.entities.players;

import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.Country;
import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.orders.Order;
import main.java.warzone.entities.players.AggressivePlayerStrategy;
import main.java.warzone.exceptions.WarzoneValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
/**
 *
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */
public class AggressivePlayerStrategyTest {
    /**
     * Current game world instance
     */
    private GameSession d_GameSession;

    /**
     * Setup game world before every test case
     *
     * @throws WarzoneValidationException If game world creation is not possible
     */
    @BeforeEach
    public void setUp() throws WarzoneValidationException {
        d_GameSession = GameSession.getInstance();
        d_GameSession.clearPreviousSession();
        d_GameSession.createContinent("Asia", String.valueOf(5));
        d_GameSession.createCountry("Iran", "Asia");
        d_GameSession.createCountry("Turkey", "Asia");
        d_GameSession.makeNeighbors("Iran", "Turkey");
        d_GameSession.createPlayer("Player1");
        d_GameSession.getPlayers().get("Player1").setPlayerStrategy(new AggressivePlayerStrategy());
        d_GameSession.getPlayers().get("Player1").addOwnedCountry("Iran");
        d_GameSession.getPlayers().get("Player1").addOwnedCountry("Turkey");
        d_GameSession.getCountriesInSession().get("Iran").setOwner("Player1");
        d_GameSession.getCountriesInSession().get("Turkey").setOwner("Player1");
        d_GameSession.getCountriesInSession().get("Turkey").setNumberOfArmies(10);
    }

    /**
     * Test case for issuePlayerOrder method.
     */
    @Test
    void whenTestIssuePlayerOrder_ExpectOrder() {
        AggressivePlayerStrategy l_AggressiveStrategy = new AggressivePlayerStrategy();
        assertDoesNotThrow(() -> l_AggressiveStrategy.issuePlayerOrder(d_GameSession.getPlayers().get("Player1"), d_GameSession));
    }

    /**
     * Test case for getStrategyNameString method.
     */
    @Test
    void whenTestGetStrategyName_ExpectString() {
        AggressivePlayerStrategy l_AggressiveStrategy = new AggressivePlayerStrategy();
        Assertions.assertEquals(WarzoneConstants.AGGRESSIVE, l_AggressiveStrategy.getStrategyNameString());
    }

    /**
     * Test case for getOwnedCountriesSortedByStrongestToWeakest method.
     */
    @Test
    void whenTestGetOwnedCountriesSortedByStrongestToWeakest_ExpectSorted() {

        AggressivePlayerStrategy l_AggressiveStrategy = new AggressivePlayerStrategy();

        assertDoesNotThrow(() -> {
            List<Country> l_SortedCountries = l_AggressiveStrategy.getOwnedCountriesFromStrongestToWeakest(d_GameSession.getPlayers().get("Player1"), d_GameSession);

            // You may want to check the sorted order of countries
            for (int i = 0; i < l_SortedCountries.size() - 1; i++) {
                Assertions.assertTrue(l_SortedCountries.get(i).getNumberOfArmies() >= l_SortedCountries.get(i + 1).getNumberOfArmies());
            }
        });
    }

}
