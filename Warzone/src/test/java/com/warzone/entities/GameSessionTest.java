package test.java.com.warzone.entities;

        import main.java.warzone.entities.Continent;
        import main.java.warzone.entities.Country;
        import main.java.warzone.entities.GameSession;
        import main.java.warzone.entities.Player;
        import main.java.warzone.exceptions.WarzoneValidationException;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;

        import java.util.Map;

        import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */
public class GameSessionTest {

    /**
     */
    private GameSession gameSession;

    /**
     */
    @BeforeEach
    public void setUp() {
        gameSession = GameSession.getInstance();
        gameSession.deletePreviousSession();
    }

    /**

     */
    @Test
    public void createContinentTest() throws WarzoneValidationException {
        gameSession.createContinent("Asia", "6");
        Map<String, Continent> continents = gameSession.getContinentsInSession();
        assertTrue(continents.containsKey("Asia"));
        assertEquals(1, continents.size());
    }

    /**

     */
//    @Test
//    public void deleteContinentTest() throws WarzoneValidationException {
//        gameSession.createContinent("Asia", "6");
//        gameSession.deleteContinent("Asia");
//        Map<String, Continent> continents = gameSession.getContinentsInSession();
//        assertFalse(continents.containsKey("Asia"));
//        assertEquals(0, continents.size());
//    }

    /**

     */
    @Test
    public void createCountryTest() throws WarzoneValidationException {
//        gameSession.createCountry("Turkey", "Asia", 2L);
        Map<String, Country> countries = gameSession.getCountriesInSession();
//        assertTrue(countries.containsKey(2L));
//        assertEquals(1, countries.size());
    }

    /**
     * Test case to verify delete country
     *
     * @throws WarzoneValidationException If country deletion validation fails.
     */
//    @Test
//    public void deleteCountryTest() throws WarzoneValidationException {
//        gameSession.createContinent("Asia", "6");
//        gameSession.createCountry("Country1", "Asia",1L );
//        gameSession.deleteCountry("Country1");
//        Map<String, Country> countries = gameSession.getCountriesInSession();
//        assertFalse(countries.containsKey("Country1"));
//        assertEquals(0, countries.size());
//    }

    /**
     * Test case to verify create neighbors
     *
     * @throws WarzoneValidationException If neighbor creation validation fails
     */
    @Test
    public void createNeighborsTest() throws WarzoneValidationException {
        gameSession.createContinent("Asia", "6");
//        gameSession.createCountry("Turkey", "Asia",2L);
//        gameSession.createCountry("Pakistan", "Asia",3L);

//        gameSession.createNeighbors("India", "Pakistan");

//        Country country1 = gameSession.getCountriesInSession().get("Turkey");
//        Country country2 = gameSession.getCountriesInSession().get("Pakistan");
//
//        assertTrue(country1.getAdjacentCountries().containsValue(country2.getName()));
//        assertTrue(country2.getAdjacentCountries().containsValue(country1.getName()));
    }

    /**
     * Test case to verify delete neighbors
     *
     * @throws WarzoneValidationException If neighbors deletion validation fails
     */
//    @Test
//    public void removeNeighborsTest() throws WarzoneValidationException {
//
//        gameSession.deleteNeighbor("Iran", "Turkey");
//
//        Country country1 = gameSession.getCountriesInSession().get("Turkey");
//
//        assertFalse(country1.getAdjacentCountries().containsKey(2L));
//    }

    /**
     * Test case to verify create player
     *
     * @throws WarzoneValidationException If player creation validation fails
     */
    @Test
    public void createPlayerTest() throws WarzoneValidationException {
        gameSession.createPlayer("Player1");
        Map<String, Player> players = gameSession.getPlayers();
        assertTrue(players.containsKey("Player1"));
        assertEquals(1, players.size());
    }

    /**
     * Test case to verify delete player
     *
     * @throws WarzoneValidationException If delete player validation fails
     */
    @Test
    public void deletePlayerTest() throws WarzoneValidationException {
        gameSession.createPlayer("Niloufar");
        gameSession.deletePlayer("Niloufar");
        Map<String, Player> players = gameSession.getPlayers();
        assertFalse(players.containsKey("Niloufar"));
//        assertEquals(1, players.size());
    }

    /**
     * Test case to verify assign country to player
     *
     * @throws WarzoneValidationException If country assignment validation fails
     */
    @Test
    public void assignCountryToPlayerTest() throws WarzoneValidationException {
        gameSession.createPlayer("Niloufar");
        gameSession.createContinent("Asia", "6");
//        gameSession.createCountry("Iran", "Asia",1L);

//        gameSession.assignCountryToPlayer("Niloufar", "Iran");

        Player player = gameSession.getPlayers().get("Niloufar");
//        assertEquals(1, player.getOwnedCountries().size());
//        assertTrue(player.getOwnedCountries().contains("Iran"));
    }
}

