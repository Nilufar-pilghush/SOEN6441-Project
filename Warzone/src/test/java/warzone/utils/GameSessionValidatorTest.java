package test.java.warzone.utils;

        import main.java.warzone.entities.GameSession;
        import main.java.warzone.exceptions.WarzoneValidationException;
        import main.java.warzone.utils.GameSessionValidator;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;

/**
 * Provides JUnit test cases for the {@link GameSessionValidator} class to validate game session configurations.
 * Tests cover validation of map connectivity, continent and country configurations, and player additions,
 * ensuring the game logic is correctly enforced under various scenarios.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class GameSessionValidatorTest {
    private GameSession d_gameSession;

    /**
     * Setup method to game session before each test
     */
    @BeforeEach
    public void setUp() {
        d_gameSession = GameSession.getInstance();
        d_gameSession.clearPreviousSession();
    }

    /**
     * Tests that a valid map configuration is correctly identified by the validator.
     * This includes creating continents and countries, and ensuring they are connected.
     *
     * @throws WarzoneValidationException if there's an error in creating continents or countries.
     */
    @Test
    public void testValidMapConnection() throws WarzoneValidationException {
        d_gameSession.createContinent("Asia",String.valueOf(5));
        d_gameSession.createCountry("Iran", "Asia",1L);
        d_gameSession.createCountry("Turkey", "Asia",2L);
        d_gameSession.makeNeighbors("Iran", "Turkey");

        d_gameSession.createContinent("Europe",String.valueOf(8));
        d_gameSession.createCountry("Germany", "Europe",4L);
        d_gameSession.createCountry("France", "Europe",5L);
        d_gameSession.makeNeighbors("Germany", "France");
        Assertions.assertFalse(GameSessionValidator.validateMap(d_gameSession));
    }

    /**
     * Tests the validator's response to an empty game session to ensure it recognizes an invalid map due to lack of content.
     */
    @Test
    public void testEmptyMapInvalid() {
        Assertions.assertFalse(GameSessionValidator.validateMap(d_gameSession));
    }

    /**
     * Tests the validator's response to a continent without countries to ensure it recognizes such a continent as invalid.
     *
     * @throws WarzoneValidationException if there's an error in creating the continent.
     */
    @Test
    public void testEmptyContinentInvalid() throws WarzoneValidationException {
        d_gameSession.createContinent("Asia",String.valueOf(5));
        Assertions.assertFalse(GameSessionValidator.validateMap(d_gameSession));
    }

    /**
     * Test case to verify if every country is reachable from a continent
     *
     * @throws WarzoneValidationException If country creation fails.
     */
    @Test
    public void testUnreachableCountriesInContinentInvalid() throws WarzoneValidationException {
        d_gameSession.createContinent("Asia",String.valueOf(5));
        d_gameSession.createCountry("Iran", "Asia",1L);
        d_gameSession.createCountry("Turkey", "Asia",2L);
        Assertions.assertFalse(GameSessionValidator.validateMap(d_gameSession));
    }

    /**
     * Test case to verify if continents are connected.
     *
     * @throws WarzoneValidationException if countries/continent creation fails
     */
    @Test
    public void testDisconnectedContinentsInvalid() throws WarzoneValidationException {
        d_gameSession.createContinent("Asia",String.valueOf(5));
        d_gameSession.createCountry("Iran", "Asia",1L);
        d_gameSession.createCountry("Turkey", "Asia",2L);
        d_gameSession.makeNeighbors("Iran", "Turkey");

        d_gameSession.createContinent("Europe",String.valueOf(8));
        d_gameSession.createCountry("Germany", "Europe",4L);
        d_gameSession.createCountry("France", "Europe",5L);
        d_gameSession.makeNeighbors("Germany", "France");
        Assertions.assertFalse(GameSessionValidator.validateMap(d_gameSession));
    }

    /**
     * Test case to verify if players are created
     */
    @Test
    public void testNoPlayersAdded(){
        Assertions.assertFalse(GameSessionValidator.arePlayersAdded(d_gameSession));
    }

    /**
     * Test case to verify if players are added
     */
    @Test
    public void testPlayersAddedIsValid() throws WarzoneValidationException {
        d_gameSession.createPlayer("Player1");
        Assertions.assertTrue(GameSessionValidator.arePlayersAdded(d_gameSession));
    }

}
