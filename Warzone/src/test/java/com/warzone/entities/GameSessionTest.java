package test.java.com.warzone.entities;

        import main.java.warzone.entities.Continent;
        import main.java.warzone.entities.GameSession;
        import main.java.warzone.exceptions.WarzoneValidationException;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;

        import java.util.Map;
        import static org.junit.jupiter.api.Assertions.*;
    /**
     * Validates the functionality of the {@link GameSession} class, ensuring the correct management of game entities
     * such as continents, countries, and players. This includes testing the creation and deletion of these entities,
     * as well as managing relationships between them, like setting neighbors and assigning countries to players.
     * Each test method is designed to verify a specific functionality within the game session context,
     * highlighting the session's ability to maintain the game's integrity and rules.
     *
     * @author Niloufar Pilgush
     * @author Nasrin Maarefi
     * @author Jerome Kithinji
     * @author Ali sayed Salehi
     * @author Fatemeh Chaji
     * @version 2.0.0
     */
    public class GameSessionTest {
        private GameSession d_gameSession;

        /**
         * Prepares the test environment before each test, ensuring a clean state.
         * This setup includes acquiring the singleton instance of {@link GameSession} and clearing any previous session data.
         */
        @BeforeEach
        public void setUp() {
            d_gameSession = GameSession.getInstance();
            d_gameSession.clearPreviousSession();
        }
        /**
         * Tests the creation of a continent, verifying its presence in the session.
         * @throws WarzoneValidationException if the creation process fails validation checks
         */
        @Test
        public void testCreateContinent() throws WarzoneValidationException {
            d_gameSession.createContinent("Asia", "5");
            Map<String, Continent> continents = d_gameSession.getContinentsInSession();
            assertTrue(continents.containsKey("Asia"), "Continent Asia should be present in the session.");
        }

        /**
         * Tests the deletion of a continent, ensuring it is removed from the session.
         * @throws WarzoneValidationException if the deletion process fails validation checks
         */
        @Test
        public void testDeleteContinent() throws WarzoneValidationException {
            d_gameSession.createContinent("Asia", "5");
            d_gameSession.deleteContinent("Asia");
            Map<String, Continent> continents = d_gameSession.getContinentsInSession();
            assertFalse(continents.containsKey("Asia"), "Continent Asia should be removed from the session.");
        }

        /**
         * Tests the creation of a country within a continent, verifying its addition to the session.
         * @throws WarzoneValidationException if the country creation process fails validation checks
         */

        @Test
        public void testCreateCountry() throws WarzoneValidationException {
            d_gameSession.createContinent("Asia", "5");
            d_gameSession.createCountry("Country1", "Asia", 1L);
            assertTrue(d_gameSession.getCountriesInSession().containsKey("Country1"), "Country1 should be present in the session.");
        }

        /**
         * Tests the deletion of a country, ensuring it is removed from the session.
         * @throws WarzoneValidationException if the country deletion process fails validation checks
         */
        @Test
        public void testDeleteCountry() throws WarzoneValidationException {
            d_gameSession.createContinent("Europe", "6");
            d_gameSession.createCountry("Country2", "Europe");
            d_gameSession.deleteCountry("Country2");
            assertFalse(d_gameSession.getCountriesInSession().containsKey("Country2"), "Country2 should be removed from the session.");
        }

        /**
         * Tests creating neighbor relationships between countries, verifying bidirectional adjacency.
         * @throws WarzoneValidationException if the neighbor creation process fails validation checks
         */
        @Test
        public void testMakeNeighbors() throws WarzoneValidationException {
            d_gameSession.createContinent("Asia", "5");
            d_gameSession.createCountry("Iran", "Asia");
            d_gameSession.createCountry("Turkey", "Asia");
            d_gameSession.makeNeighbors("Iran", "Turkey");

            assertTrue(d_gameSession.getCountriesInSession().get("Iran").getAdjacentCountries().containsValue("Turkey"),
                    "Iran should be neighbors with Turkey.");
            assertTrue(d_gameSession.getCountriesInSession().get("Turkey").getAdjacentCountries().containsValue("Iran"),
                    "Turkey should be neighbors with Iran.");
        }

        /**
         * Tests the creation of a player, verifying their addition to the game session.
         * @throws WarzoneValidationException if the player creation process fails validation checks
         */
        @Test
        public void testCreatePlayer() throws WarzoneValidationException {
            d_gameSession.createPlayer("Player1");
            assertTrue(d_gameSession.getPlayers().containsKey("Player1"), "Player1 should be present in the session.");
        }

        /**
         * Tests the deletion of a player, ensuring their removal from the game session.
         * @throws WarzoneValidationException if the player deletion process fails validation checks
         */
        @Test
        public void testDeletePlayer() throws WarzoneValidationException {
            d_gameSession.createPlayer("Player1");
            d_gameSession.deletePlayer("Player1");
            assertFalse(d_gameSession.getPlayers().containsKey("Player1"), "Player1 should be removed from the session.");
        }
        /**
         * Tests assigning a country to a player, verifying the player's ownership of the country.
         * @throws WarzoneValidationException if the country assignment process fails validation checks
         */
        @Test
        public void testAssignCountryToPlayer() throws WarzoneValidationException {
            d_gameSession.createPlayer("Player1");
            d_gameSession.createContinent("Asia", "5");
            d_gameSession.createCountry("Iran", "Asia");
            d_gameSession.assignCountryToPlayer("Player1", "Iran");

            assertTrue(d_gameSession.getPlayers().get("Player1").getOwnedCountries().contains("Iran"),
                    "Player1 should own Iran.");
        }

    }

