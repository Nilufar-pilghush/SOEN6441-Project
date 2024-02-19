package test.java.com.warzone.services.impl;

        import main.java.warzone.entities.GamePhase;
        import main.java.warzone.entities.GameSession;
        import main.java.warzone.exceptions.WarzoneValidationException;
        import main.java.warzone.services.impl.ReinforcementServiceImpl;
        import org.junit.jupiter.api.AfterEach;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;

/**
 * JUnit test cases for the reinforcement phase.
 * Test case to verify the user commands execution for reinforcements.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */
public class ReinforcementServiceImplTest {

    /**
     * Class to be tested
     */
    private ReinforcementServiceImpl reinforcementService;

    /**
     * Current game world instance
     */
    private GameSession d_GameSession;

    /**
     * Constructor to initialize issue orders service
     */
    public ReinforcementServiceImplTest() {
        reinforcementService = new ReinforcementServiceImpl();
        d_GameSession = GameSession.getInstance();
    }

    /**
     * Setup game world before every test case
     *
     * @throws WarzoneValidationException If game world creation is not possible
     */
    @BeforeEach
    public void setUp() throws WarzoneValidationException {
        d_GameSession.clearPreviousSession();
        d_GameSession.createContinent("Asia", String.valueOf(5));
        d_GameSession.createCountry("Iran", "Asia");
        d_GameSession.createCountry("Turkey", "Asia");
        d_GameSession.makeNeighbors("Iran", "Turkey");
        d_GameSession.getContinentsInSession().get("Asia").setOwner("Niloufar");
        d_GameSession.createPlayer("Niloufar");
    }

    /**
     * Test case to verify the user commands execution for issue orders.
     */
    @Test
    public void whenHandleReinforcementPhase_ExpectPhaseHandledTest() {
        Assertions.assertDoesNotThrow(() -> reinforcementService.handleGamePhase(GamePhase.REINFORCEMENT));
    }

    /**
     * Method to clean up the created world
     */
    @AfterEach
    public void clean() {
        GameSession.getInstance().clearPreviousSession();
    }
}
