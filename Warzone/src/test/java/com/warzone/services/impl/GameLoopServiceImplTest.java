package test.java.com.warzone.services.impl;

        import main.java.warzone.entities.GamePhase;
        import main.java.warzone.entities.GameSession;
        import main.java.warzone.exceptions.WarzoneValidationException;
        import main.java.warzone.services.impl.GameLoopServiceImpl;
        import org.junit.jupiter.api.AfterEach;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;

        import java.io.ByteArrayInputStream;
        import java.io.InputStream;

/**
 * JUnit test cases for the main game loop phase.
 * Test case to verify the user commands execution for main game loop.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */
public class GameLoopServiceImplTest {

    /**
     * Class to be tested
     */
    private GameLoopServiceImpl gameLoopService;

    /**
     * Current game world instance
     */
    private GameSession d_GameSession;

    /**
     * Constructor to initialize issue orders service
     */
    public GameLoopServiceImplTest(){
        gameLoopService = new GameLoopServiceImpl();
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
        d_GameSession.createContinent("Asia", String.valueOf(1));
        d_GameSession.createCountry("Iran", "Asia");
        d_GameSession.createCountry("Turkey", "Asia");
        d_GameSession.makeNeighbors("Iran", "Turkey");
    }

    /**
     * Test case to verify the user commands execution for issue orders.
     */
    @Test
    public void whenHandleMainGameLoopPhase_ExpectPhaseHandledTest(){
        String input1 = "showmap\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(()-> gameLoopService.handleGamePhase(GamePhase.MAIN_GAME_LOOP));
    }

    /**
     * Method to clean up the created world
     */
    @AfterEach
    public void clean() {
        GameSession.getInstance().clearPreviousSession();
    }
}
