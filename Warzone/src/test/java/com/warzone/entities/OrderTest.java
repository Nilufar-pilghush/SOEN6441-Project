package test.java.com.warzone.entities;

        import main.java.warzone.entities.GameSession;
        import main.java.warzone.entities.Order;
        import main.java.warzone.exceptions.WarzoneValidationException;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;

/**

 */
public class OrderTest {

    /**
     * Class to be tested
     */
    private Order d_Order;

    /**
     * Current game world instance
     */
    private GameSession d_GameSession;

    /**
     * Method to set up each test case
     */
    @BeforeEach
    public void setUp() {
        d_GameSession = GameSession.getInstance();
//        d_GameSession.deletePreviousSession();
        d_Order = new Order("Niloufar", "Iran", "Canada", 10);
    }

    /**
     * Test case to validate player name
     */
    @Test
    public void whenGetPlayerName_ExpectPlayerNameTest() {
        Assertions.assertEquals("Niloufar", d_Order.getPlayerName());
    }

    /**
     * Test case to set player name
     */
    @Test
    public void whenSetPlayerName_ExpectPlayerNameSetTest() {
        d_Order.setPlayerName("Nasrin");
        Assertions.assertEquals("Nasrin", d_Order.getPlayerName());
    }

    /**
     * Test case to get target country name
     */
    @Test
    public void whenGetTargetCountry_ExpectTargetCountryNameTest() {
        Assertions.assertEquals("Canada", d_Order.getTargetCountry());
    }

    /**
     * Test case to set target country name
     */
    @Test
    public void whenSetTargetCountry_ExpectTargetCountryNameSetTest() {
        d_Order.setTargetCountry("Turkey");
        Assertions.assertEquals("Turkey", d_Order.getTargetCountry());
    }

    /**
     * Test case to get source country name
     */
    @Test
    public void whenGetSourceCountry_ExpectSourceCountryNameTest() {
        Assertions.assertEquals("Iran", d_Order.getSourceCountry());
    }

    /**
     * Test case to set source country name
     */
    @Test
    public void whenSetSourceCountry_ExpectSourceCountryNameSetTest() {
        d_Order.setSourceCountry("China");
        Assertions.assertEquals("China", d_Order.getSourceCountry());
    }

    /**
     * Test case to get number of armies
     */
    @Test
    public void whenGetNumberOfArmies_ExpectArmiesTest() {
        Assertions.assertEquals(10, d_Order.getNumberOfArmies());
    }

    /**
     * Test case to set number of armies
     */
    @Test
    public void whenSetNumberOfArmies_ExpectArmiesSetTest() {
        d_Order.setNumberOfArmies(15);
        Assertions.assertEquals(15, d_Order.getNumberOfArmies());
    }

    /**
     * Test case to execute orders
     *
     * @throws main.java.warzone.exceptions.WarzoneValidationException If execution validations are not met
     */
    @Test
    public void whenExecuteOrder_ExpectOrderExecuted() throws WarzoneValidationException {
//        d_GameSession.createPlayer("Nasrin");
        d_Order.setPlayerName("Nasrin");
//        d_GameSession.createContinent("Asia", String.valueOf(10));
//        d_GameSession.createCountry("Iran", "Asia", 10);
//        d_GameSession.createCountry("Canada", "NorthAmerica", 13);
//        d_GameSession.getCountriesInSession().get("Iran").setOwner("Niloufar");
//        d_GameSession.getCountriesInSession().get("Canada").setOwner("Nasrin");
//        d_GameSession.createNeighbors("Iran", "Sri Lanka");
//        Assertions.assertDoesNotThrow(() -> d_Order.execute(d_GameSession));
    }

    /**
     * Test case to execute orders
     *
     * @throws WarzoneValidationException If execution validations are not met
     */
    @Test
    public void whenExecuteDeployOrder_ExpectOrderExecuted() throws WarzoneValidationException {
        d_Order.setSourceCountry(null);
        d_Order.setPlayerName("Nasrin");
        d_GameSession.createPlayer("Nasrin");
        d_GameSession.createContinent("Asia", String.valueOf(10));
//        d_GameSession.createCountry("Iran", "Asia",14);
//        d_GameSession.createCountry("Canada", "NorthAmerica",13);
//        d_GameSession.getCountriesInSession().get("Iran").setOwner("Niloufar");
//        d_GameSession.getCountriesInSession().get("Canada").setOwner("Nasrin");
//        d_GameSession.createNeighbors("India", "Sri Lanka");
//        Assertions.assertDoesNotThrow(() -> d_Order.execute(d_GameSession));
    }
}

