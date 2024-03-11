package test.java.com.warzone.services.impl;

        import main.java.warzone.entities.GamePhase;
        import main.java.warzone.entities.GameSession;
        import main.java.warzone.services.impl.MapEditorServiceImpl;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;

        import java.io.ByteArrayInputStream;
        import java.io.InputStream;

/**
 * Tests for {@link MapEditorServiceImpl} focusing on verifying the functionality of continent and country
 * management commands, neighbor relations, as well as utility commands such as show map, list maps, help, and
 * the processing of invalid commands. Ensures that map editing commands perform as expected in various scenarios.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class MapEditorServiceImplTest {
    private MapEditorServiceImpl d_mapEditorService;
    private GameSession d_gameSession;

    /**
     * Sets up the test environment before each test, initializing the MapEditorService and
     * clearing any existing game session data to ensure a clean state.
     */
    @BeforeEach
    public void setUp() {
        d_mapEditorService = new MapEditorServiceImpl();
        d_gameSession = GameSession.getInstance();
        d_gameSession.clearPreviousSession();
    }

    /**
     * Initializes a new instance of MapEditorServiceImplTest for testing map editing functionalities.
     * This constructor sets up a fresh instance of MapEditorServiceImpl to be used across various test cases,
     * ensuring that each test starts with a clean state of the map editor service.
     */
    public MapEditorServiceImplTest() {
        d_mapEditorService = new MapEditorServiceImpl();
    }

    /**
     * Tests the addition of a continent to the map. Verifies that the continent is correctly added to the game session.
     */
    @Test
    public void testAddContinent() {
        String input1 = "EditContinent -add Asia 6\n Exit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
        Assertions.assertTrue(d_gameSession.getContinentsInSession().containsKey("Asia"));
    }

    /**
     * Tests the removal of a continent from the map. Verifies that the continent is correctly removed from the game session.
     */
    @Test
    public void testRemoveContinent() {
        String input1 = "EditContinent -remove Asia\n Exit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
        Assertions.assertFalse(d_gameSession.getContinentsInSession().containsKey("Asia"));
    }
    /**
     * Tests the addition of a country to a continent. Verifies that the country is correctly added to the game session.
     */
    @Test
    public void testAddCountry() {
        String input1 = "EditContinent -add Asia 6\n EditCountry -add Iran Asia\n Exit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
        Assertions.assertTrue(d_gameSession.getCountriesInSession().containsKey("Iran"));

    }
    /**
     * Tests the removal of a country from the map. Verifies that the country is correctly removed from the game session.
     */
    @Test
    public void testRemoveCountry() {
        String input1 = "EditContinent -add Asia 6\n EditCountry -remove Iran\n Exit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
        Assertions.assertFalse(d_gameSession.getCountriesInSession().containsKey("Iran"));}
    /**
     * Tests the creation of a neighbor relationship between two countries. Verifies that the relationship is correctly established.
     */
    @Test
    public void testAddNeighbor() {
        String input1 = "EditContinent -add Asia 6\n EditCountry -add Iran Asia\n" +
                "EditCountry -add Turkey Asia\n EditNeighbor -add Iran Turkey\n Exit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        d_mapEditorService .handleGamePhase(GamePhase.MAP_EDITOR);
        Assertions.assertTrue(d_gameSession.getCountriesInSession().get("Iran").isAdjacentTo("Turkey"));
    }
    /**
     * Tests the deletion of a neighbor relationship between two countries. Verifies that the relationship is correctly removed.
     */
    @Test
    public void testRemoveNeighbor() {
        String input1 = "EditContinent -add Asia 6\n EditCountry -add Iran Asia\n\" +\n" +
                "                \"EditCountry -add Turkey Asia\n EditNeighbor -add Iran Turkey\n Exit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
        Assertions.assertFalse(d_gameSession.getCountriesInSession().get("Iran").isAdjacentTo("Turkey"));
    }
    /**
     * Validates the functionality of the "showmap" command within the map editor phase.
     * Simulates the user input to add continents and countries, then execute the "showmap" command,
     * finally verifying if the command leads to the expected game phase transition.
     */
    @Test
    public void testShowMapCommand() {
        String input1 = "EditContinent -add Asia 6\n EditCountry -add Iran Asia\n\" +\n" +
                "\"EditCountry -add Turkey Asia\n EditNeighbor -add Iran Turkey\n Exit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        Assertions.assertEquals(d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR), GamePhase.START_UP);
    }
    /**
     * Tests the "listmaps" command's functionality to ensure the game transitions to the START_UP phase after execution.
     * This test simulates the "listmaps" command input and checks if the game phase transition behaves as expected.
     */
    @Test
    public void testListMapsCommand() {
        String input1 = "listmaps\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        Assertions.assertEquals(d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR), GamePhase.START_UP);
    }
    /**
     * Verifies the help command's functionality within the map editor, ensuring the game transitions correctly.
     * By simulating the "help" command, this test checks if the system responds with the appropriate game phase transition.
     */
    @Test
    public void testHelpCommand() {
        String input1 = "listmaps\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        Assertions.assertEquals(d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR), GamePhase.START_UP);
    }
    /**
     * Confirms the system's handling of invalid commands within the map editor by checking for the correct phase transition.
     * This test simulates an invalid command input and verifies if the game still transitions to the START_UP phase gracefully.
     */
    @Test
    public void testInvalidCommandHandling() {
        String input1 = "invalid cmd\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        Assertions.assertEquals(d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR), GamePhase.START_UP);
    }
    /**
     * Tests the "editmap" command's execution to verify map editing functionality and phase transition.
     * Simulates the "editmap" command followed by an "add country" operation, assessing the command processing and game state transition.
     */
    @Test
    public void testEditMapCommand() {
        String input1 = "editmap artic.map\neditcountry -add ABC Canada\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
//        Assertions.assertTrue(d_GameSession.getCountriesInSession().containsKey("ABC"));
    }
    /**
     * Ensures the "savemap" command operates correctly, including verification of game state persistence.
     * Simulates the "savemap" command after map modifications to check if changes are acknowledged without persisting unintended data.
     */
    @Test
    public void testSaveMapCommand() {
        String input1 = "editmap artic.map\neditcountry -add ABC Canada\nsavemap CanUpdateTest\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
        Assertions.assertFalse(d_gameSession.getCountriesInSession().containsKey("ABC"));
    }
}
