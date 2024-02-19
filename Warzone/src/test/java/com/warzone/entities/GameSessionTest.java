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
     * JUnit test cases for the Game-world class.
     * Test case to verify continent creation.
     * Test case to verify continent deletion.
     * Test case to verify country creation.
     * Test case to verify country deletion.
     * Test case to verify make neighbors.
     * Test case to verify neighbors deletion.
     * Test case to verify create player.
     * Test case to verify delete player.
     * Test case to assign country to player.
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
         * Class to be tested
         */
        private GameSession gameSession;

        /**
         * Method to set up game world
         */
        @BeforeEach
        public void setUp() {
            gameSession = gameSession.getInstance();
            gameSession.clearPreviousSession();
            gameSession.clearPreviousSession();
        }

        /**
         * Test case to verify create continent
         *
         * @throws WarzoneValidationException If continent creation validation fails
         */
        @Test
        public void createContinentTest() throws WarzoneValidationException {
            gameSession.createContinent("Russia", "5");
            Map<String, Continent> continents = gameSession.getContinentsInSession();
            assertTrue(continents.containsKey("Russia"));
            assertEquals(1, continents.size());
        }

        /**
         * Test case to verify delete continent
         *
         * @throws WarzoneValidationException If continent deletion validation fails
         */
        @Test
        public void deleteContinentTest() throws WarzoneValidationException {
            gameSession.createContinent("Asia", "5");
            gameSession.deleteContinent("Asia");
            Map<String, Continent> continents = gameSession.getContinentsInSession();
            assertFalse(continents.containsKey("Asia"));
            assertEquals(0, continents.size());
        }

        /**
         * Test case to verify create country
         *
         * @throws WarzoneValidationException If country creation validation fails
         */
        @Test
        public void createCountryTest() throws WarzoneValidationException {
            gameSession.createContinent("Russia", "5");
            gameSession.createCountry("Country1", "Russia", 1L);
            Map<String, Country> countries = gameSession.getCountriesInSession();
            assertTrue(countries.containsKey("Country1"));
            assertEquals(1, countries.size());
        }

        /**
         * Test case to verify delete country
         *
         * @throws WarzoneValidationException If country deletion validation fails.
         */
        @Test
        public void deleteCountryTest() throws WarzoneValidationException {
            gameSession.createContinent("Europe", "5");
            gameSession.createCountry("Country1", "Europe");
            gameSession.deleteCountry("Country1");
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
        public void makeNeighborsTest() throws WarzoneValidationException {
            gameSession.createContinent("Asia", "5");
            gameSession.createCountry("Iran", "Asia");
            gameSession.createCountry("Turkey", "Asia");

            gameSession.makeNeighbors("Iran", "Turkey");

            Country country1 = gameSession.getCountriesInSession().get("Iran");
            Country country2 = gameSession.getCountriesInSession().get("Turkey");

            assertTrue(country1.getAdjacentCountries().containsValue(country2.getName()));
            assertTrue(country2.getAdjacentCountries().containsValue(country1.getName()));
        }

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
            assertEquals(0, players.size());
        }

        /**
         * Test case to verify assign country to player
         *
         * @throws WarzoneValidationException If country assignment validation fails
         */
        @Test
        public void assignCountryToPlayerTest() throws WarzoneValidationException {
            gameSession.createPlayer("Niloufar");
            gameSession.createContinent("Asia", "5");
            gameSession.createCountry("Iran", "Asia");

            gameSession.assignCountryToPlayer("Niloufar", "Iran");

            Player player = gameSession.getPlayers().get("Niloufar");
            assertEquals(1, player.getOwnedCountries().size());
            assertTrue(player.getOwnedCountries().contains("Iran"));
        }
    }

