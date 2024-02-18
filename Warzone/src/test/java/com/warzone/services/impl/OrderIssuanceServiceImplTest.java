package test.java.com.warzone.services.impl;

        import main.java.warzone.entities.GamePhase;
        import main.java.warzone.entities.GameSession;
        import main.java.warzone.exceptions.WarzoneValidationException;
        import main.java.warzone.services.impl.OrderIssuanceServiceImpl;
        import org.junit.jupiter.api.AfterEach;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;

/**
 * JUnit test cases for the game issue order service phase.
 * Test case to verify the user commands execution for issue orders.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */
public class OrderIssuanceServiceImplTest {

    /**
     * Class to be tested
     */
    private OrderIssuanceServiceImpl orderIssuanceService;

    /**
     * Current game world instance
     */
    private GameSession d_GameSession;

    /**
     * Constructor to initialize issue orders service
     */
    public OrderIssuanceServiceImplTest(){
        orderIssuanceService = new OrderIssuanceServiceImpl();
        d_GameSession = GameSession.getInstance();
    }

    /**
     * Setup game world before every test case
     *
     * @throws WarzoneValidationException If game world creation is not possible
     */
    @BeforeEach
    public void setUp() throws WarzoneValidationException {
//        d_GameSession.deletePreviousSession();
//        d_GameSession.createContinent("Asia", String.valueOf(5));
//        d_GameSession.createCountry("India", "Asia");
//        d_GameSession.createCountry("Sri", "Asia");
//        d_GameSession.createNeighbors("India", "Sri");
    }

    /**
     * Test case to verify the user commands execution for issue orders.
     */
    @Test
    public void whenHandleOrderIssuePhase_ExpectPhaseHandledTest(){
//        Assertions.assertDoesNotThrow(()-> orderIssuanceService.handleGamePhase(GamePhase.ISSUE_ORDERS));
    }

    /**
     * Method to clean up the created world
     */
    @AfterEach
    public void clean() {
        GameSession.getInstance().deletePreviousSession();
    }
}
