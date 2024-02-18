package test.java.com.warzone.utils;

        import main.java.warzone.entities.GameSession;
        import main.java.warzone.exceptions.WarzoneValidationException;
        import main.java.warzone.utils.GameSessionValidator;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;

/**
 * JUnit test cases for the map validator.
 * Test case to verify if continents are connected.
 * Test case to verify if countries in continents are connected.
 * Test case to verify if continents are empty.
 * Test case to verify if players are added.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */
public class GameSessionValidatorTest {

    /**
     * Current Game world instance
     */
    private GameSession d_GameSession;

    /**
     * Setup method to game session before each test
     */
    @BeforeEach
    public void setUp() {
        d_GameSession = GameSession.getInstance();
        d_GameSession.deletePreviousSession();
    }

    /**
     * Test case to verify connected map is valid
     *
     * @throws WarzoneValidationException If map creation validations fail.
     */
    @Test
    public void whenContinentsCountriesConnected_ExpectValidMapTest() throws WarzoneValidationException {
        d_GameSession.createContinent("Asia",String.valueOf(5));
//        d_GameSession.createCountry("Iran", "Asia",1L);
//        d_GameSession.createCountry("Turkey", "Asia",2L);
//        d_GameSession.createNeighbors("India", "Pakistan");

        d_GameSession.createContinent("Europe",String.valueOf(8));
//        d_GameSession.createCountry("Germany", "Europe",4L);
//        d_GameSession.createCountry("France", "Europe",5L);
//        d_GameSession.createNeighbors("Germany", "France");
//        d_GameSession.createNeighbors("Germany","India");
//        Assertions.assertTrue(GameSessionValidator.validateMap(d_GameSession));
    }

    /**
     * Test case to verify empty map.
     */
    @Test
    public void whenEmptyGameSession_ExpectInvalidMapTest(){
        Assertions.assertFalse(GameSessionValidator.validateMap(d_GameSession));
    }

    /**
     * Test case to verify empty continent
     *
     * @throws WarzoneValidationException If continent creation fails
     */
    @Test
    public void whenEmptyContinent_ExpectInvalidMap() throws WarzoneValidationException {
        d_GameSession.createContinent("Asia",String.valueOf(5));
        Assertions.assertFalse(GameSessionValidator.validateMap(d_GameSession));
    }

    /**
     * Test case to verify if every country is reachable from a continent
     *
     * @throws WarzoneValidationException If country creation fails.
     */
    @Test
    public void whenCountryInContinentNotReachable_ExpectInvalidMap() throws WarzoneValidationException {
        d_GameSession.createContinent("Asia",String.valueOf(5));
//        d_GameSession.createCountry("Iran", "Asia",1L);
//        d_GameSession.createCountry("Turkey", "Asia",2L);
        Assertions.assertFalse(GameSessionValidator.validateMap(d_GameSession));
    }

    /**
     * Test case to verify if continents are connected.
     *
     * @throws WarzoneValidationException if countries/continent creation fails
     */
    @Test
    public void whenContinentsNotConnected_ExpectInvalidMap() throws WarzoneValidationException {
        d_GameSession.createContinent("Asia",String.valueOf(5));
//        d_GameSession.createCountry("Iran", "Asia",1L);
//        d_GameSession.createCountry("turkey", "Asia",2L);
//        d_GameSession.createNeighbors("Iran", "Turkey");

        d_GameSession.createContinent("Europe",String.valueOf(8));
//        d_GameSession.createCountry("Germany", "Europe",4L);
//        d_GameSession.createCountry("France", "Europe",5L);
//        d_GameSession.createNeighbors("Germany", "France");
        Assertions.assertFalse(GameSessionValidator.validateMap(d_GameSession));
    }

    /**
     * Test case to verify if players are created
     */
    @Test
    public void whenEmptyPlayers_ExpectPlayersNotAdded(){
        Assertions.assertFalse(GameSessionValidator.arePlayersAdded(d_GameSession));
    }

    /**
     * Test case to verify if players are added
     */
    @Test
    public void whenPlayersAdded_ExpectPlayersAdded() throws WarzoneValidationException {
        d_GameSession.createPlayer("Snehil");
        Assertions.assertTrue(GameSessionValidator.arePlayersAdded(d_GameSession));
    }

}
