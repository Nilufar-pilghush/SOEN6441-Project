package test.java.com.warzone.services.impl;

        import main.java.warzone.entities.GameSession;
        import main.java.warzone.exceptions.WarzoneValidationException;
        import main.java.warzone.services.impl.OrderExecutorServiceImpl;
        import org.junit.jupiter.api.AfterEach;
        import org.junit.jupiter.api.BeforeEach;

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
public class OrderExecutorServiceImplTest {

    /**
     * Class to be tested
     */
    private OrderExecutorServiceImpl orderExecutorService;

    /**
     * Current game world instance
     */
    private GameSession d_GameSession;

    /**
     * Constructor to initialize execute orders service
     */
    public OrderExecutorServiceImplTest() {
        orderExecutorService = new OrderExecutorServiceImpl();
    }

    /**
     * Setup game world before every test case
     *
     * @throws WarzoneValidationException If game world creation is not possible
     */
    @BeforeEach
    public void setUp() throws WarzoneValidationException {
        d_GameSession = GameSession.getInstance();
//        d_GameSession.clearPreviousWorld();
//        d_GameSession.createContinent("Asia", String.valueOf(5));
//        d_GameSession.createCountry("Iran", "Asia");
//        d_GameSession.createCountry("Canada", "Asia");
//        d_GameSession.createNeighbors("Iran", "Canada");
//        d_GameSession.createPlayer("Niloufar");
//        d_GameSession.getCountriesInSession().get("Iran").setOwner("Niloufar");
        d_GameSession.getContinentsInSession().get("Canada").setOwner("Niloufar");
        d_GameSession.getPlayers().get("Niloufar").addDeployOrder("Canada", 2);
    }

    /**
     * Test case to validate execute orders for a given player
     */
//    @Test
//    public void whenHandlePhase_ExpectOrdersExecutedTest() {
////        Assertions.assertEquals(GamePhase.MAIN_GAME_LOOP, orderExecutorService.handleGamePhase(
////                GamePhase.EXECUTE_ORDERS));
//    }

    /**
     * Method to clean up the created world
     */
    @AfterEach
    public void clean() {
        d_GameSession.deletePreviousSession();
    }
}
