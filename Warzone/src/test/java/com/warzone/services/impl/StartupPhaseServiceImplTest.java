package test.java.com.warzone.services.impl;
        import main.java.warzone.entities.GamePhase;
        import main.java.warzone.entities.GameSession;
        import main.java.warzone.services.impl.StartupPhaseServiceImpl;
        import org.junit.jupiter.api.AfterEach;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.Test;

        import java.io.ByteArrayInputStream;
        import java.io.InputStream;

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
public class StartupPhaseServiceImplTest {

    /**
     * Class to be tested
     */
    private StartupPhaseServiceImpl startupPhaseService;

    /**
     * Constructor to initialize game startup phase service
     */
    public StartupPhaseServiceImplTest(){
        startupPhaseService = new StartupPhaseServiceImpl();
    }

    /**
     * Test case to verify add gameplayer
     */
    @Test
    public void whenHandlePhaseGamePlayer_ExpectGamePlayerHandledTest() {
        String input1 = "loadmap artic\ngameplayer -add Snehil\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> startupPhaseService.handleGamePhase(GamePhase.START_UP));
    }

    /**
     * Test case to verify list maps
     */
    @Test
    public void whenHandleListMap_ExpectListMapsTest() {
        String input1 = "listmaps\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> startupPhaseService.handleGamePhase(GamePhase.START_UP));
    }

    /**
     * Test case to verify help
     */
    @Test
    public void whenHandleHelp_ExpectHelp() {
        String input1 = "help\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> startupPhaseService.handleGamePhase(GamePhase.START_UP));
    }

    /**
     * Test case to verify assign countries
     */
    @Test
    public void whenHandleAssignCountries_ExpectAssignCountriesTest() {
        String input1 = "loadmap artic\ngameplayer -add Snehil\nassigncountries\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> startupPhaseService.handleGamePhase(GamePhase.START_UP));
    }

    /**
     * Method to clean up the created world
     */
    @AfterEach
    public void clean() {
//        GameSession.getInstance().deletePreviousSession();
    }
}
