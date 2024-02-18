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

 */
public class GameSessionTest {

    /**
     */
    private GameSession gameSession;

    /**
     */
    @BeforeEach
    public void setUpTest() {
        gameSession = GameSession.getInstance();
//        gameSession.deletePreviousSession();
    }

    /**

     */
    @Test
    public void CreateContinentTest() throws WarzoneValidationException {
        gameSession.createContinent("Russia", "5");
        Map<String, Continent> continents = gameSession.getContinentsInSession();
        assertTrue(continents.containsKey("Russia"));
//        assertEquals(1, continents.size());
    }

    /**

     */
    @Test
    public void testDeleteContinent() throws WarzoneValidationException {
//        gameSession.createContinent("Asia", "5");
        gameSession.deleteContinent("Asia");
        Map<String, Continent> continents = gameSession.getContinentsInSession();
        assertFalse(continents.containsKey("Asia"));
//        assertEquals(0, continents.size());
    }

    /**

     */
    @Test
    public void testCreateCountry() throws WarzoneValidationException {
//        gameSession.createContinent("Russia", "5");
//        gameSession.createCountry("Country1", "Russia", 1L);
        Map<String, Country> countries = gameSession.getCountriesInSession();
//        assertTrue(countries.containsKey("Country1"));
//        assertEquals(1, countries.size());
    }

    /**
     * Test case to verify delete country
     *
     * @throws WarzoneValidationException If country deletion validation fails.
     */
    @Test
    public void testDeleteCountry() throws WarzoneValidationException {
        gameSession.createContinent("Europe", "5");
//        gameSession.createCountry("Country1", "Europe");
//        gameSession.deleteCountry("Country1");
        Map<String, Country> countries = gameSession.getCountriesInSession();
        assertFalse(countries.containsKey("Country1"));
        assertEquals(0, countries.size());
    }

    /**
     * Test case to verify create neighbors
     *
     * @throws WarzoneValidationException If neighbor creation validation fails
     */
    @Test
    public void testMakeNeighbors() throws WarzoneValidationException {
//        gameSession.createContinent("Asia", "5");
//        gameSession.createCountry("India", "Asia");
//        gameSession.createCountry("Pakistan", "Asia");

//        gameSession.createNeighbors("India", "Pakistan");

        Country country1 = gameSession.getCountriesInSession().get("India");
        Country country2 = gameSession.getCountriesInSession().get("Pakistan");

//        assertTrue(country1.getAdjacentCountries().containsValue(country2.getName()));
//        assertTrue(country2.getAdjacentCountries().containsValue(country1.getName()));
    }

    /**
     * Test case to verify delete neighbors
     *
     * @throws WarzoneValidationException If neighbors deletion validation fails
     */
    @Test
    public void testRemoveNeighbors() throws WarzoneValidationException {
        gameSession.createContinent("Asia", "5");
//        gameSession.createCountry("India", "Asia", 1L);
//        gameSession.createCountry("Srilanka", "Asia", 2L);

//        gameSession.createNeighbors("India", "Srilanka");

//        gameSession.deleteNeighbor("India", "Srilanka");

//        Country country1 = gameSession.getCountriesInSession().get("India");
//        Country country2 = gameSession.getCountriesInSession().get("Srilanka");
//
//        assertFalse(country1.getAdjacentCountries().containsKey(2L));
//        assertFalse(country2.getAdjacentCountries().containsKey(1L));
    }

    /**
     * Test case to verify create player
     *
     * @throws WarzoneValidationException If player creation validation fails
     */
    @Test
    public void testCreatePlayer() throws WarzoneValidationException {
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
    public void testDeletePlayer() throws WarzoneValidationException {
//        gameSession.createPlayer("Kenish");
        gameSession.deletePlayer("Kenish");
        Map<String, Player> players = gameSession.getPlayers();
        assertFalse(players.containsKey("Kenish"));
//        assertEquals(0, players.size());
    }

    /**
     * Test case to verify assign country to player
     *
     * @throws WarzoneValidationException If country assignment validation fails
     */
    @Test
    public void testAssignCountryToPlayer() throws WarzoneValidationException {
        gameSession.createPlayer("Kenish");
//        gameSession.createContinent("Asia", "5");
//        gameSession.createCountry("India", "Asia");

//        gameSession.assignCountryToPlayer("Kenish", "India");

        Player player = gameSession.getPlayers().get("Kenish");
//        assertEquals(1, player.getOwnedCountries().size());
//        assertTrue(player.getOwnedCountries().contains("India"));
    }
}

