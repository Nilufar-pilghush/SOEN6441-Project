package test.java.com.warzone.services.impl;
        import main.java.warzone.entities.GamePhase;
        import main.java.warzone.services.impl.StartupPhaseServiceImpl;
        import org.junit.jupiter.api.AfterEach;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.Test;

        import java.io.ByteArrayInputStream;
        import java.io.InputStream;

/**
 * Tests the functionality of {@link StartupPhaseServiceImpl} during the game's start-up phase.
 * It verifies that various commands such as loading maps, adding players, listing maps, displaying help,
 * and assigning countries can be executed without errors, ensuring the game can transition through the
 * START_UP phase as expected.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class StartupPhaseServiceImplTest {

    private StartupPhaseServiceImpl d_startupPhaseService;

    public void testStartupPhaseServiceImpl() {
        d_startupPhaseService = new StartupPhaseServiceImpl();
    }

    /**
     * Verifies adding a game player during the start-up phase processes without throwing exceptions.
     */
    @Test
    public void testAddGamePlayer() {
        String input1 = "loadmap artic\ngameplayer -add Snehil\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> d_startupPhaseService.handleGamePhase(GamePhase.START_UP));
    }
    /**
     * Verifies listing maps during the start-up phase processes without throwing exceptions.
     */
    @Test
    public void testListMaps() {
        String input1 = "listmaps\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> d_startupPhaseService.handleGamePhase(GamePhase.START_UP));
    }

    /**
     * Verifies that the help command during the start-up phase processes without throwing exceptions.
     */
    @Test
    public void testHelpCommand() {
        String input1 = "help\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> d_startupPhaseService.handleGamePhase(GamePhase.START_UP));
    }
    /**
     * Verifies assigning countries to players during the start-up phase processes without throwing exceptions.
     */
    @Test
    public void testAssignCountries() {
        String input1 = "loadmap artic\ngameplayer -add Snehil\nassigncountries\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> d_startupPhaseService.handleGamePhase(GamePhase.START_UP));
    }
    /**
     * Cleans up after tests by resetting system inputs and potentially clearing any game session data.
     */
    @AfterEach
    public void cleanUp() {
        System.setIn(System.in); // Reset system input to its original source
        // GameSession.getInstance().clearPreviousSession();
    }
    /**
     * Method to clean up the created world
     */
    @AfterEach
    public void clean() {
//        GameSession.getInstance().deletePreviousSession();
    }
}
