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
 * JUnit test cases for the scene editor class.
 * Test case to verify the correct insertion and removal of continent.
 * Test case to verify the correct insertion and removal of country.
 * Test case to verify the correct creation and deletion of neighbors.
 * Test case to verify the show map command.
 * Test case to verify the correct behavior of edit map and save map commands .
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */
public class MapEditorServiceImplTest {

    /**
     * Class to be tested instance
     */
    private MapEditorServiceImpl d_MapEditorService;

    /**
     * Current game world
     */
    private GameSession d_GameSession;

    /**
     * Constructor to initialize scene editor
     */
    public MapEditorServiceImplTest(){
        d_MapEditorService = new MapEditorServiceImpl();
    }

    /**
     */
    @BeforeEach
    public void setUp() {
        d_GameSession = GameSession.getInstance();
        d_GameSession.clearPreviousSession();
    }

    /**
     * Test case to verify continent insertion.
     */
    @Test
    public void whenSceneEditorAddContinent_AddContinentTest() {
        String input1 = "editcontinent -add Asia 6\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        d_MapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
        Assertions.assertTrue(d_GameSession.getContinentsInSession().containsKey("Asia"));
    }

    /**
     * Test case to verify continent removal.
     */
    @Test
    public void whenMapEditorRemoveContinent_removeContinentTest() {
        String input1 = "editcontinent -remove Asia\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        d_MapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
        Assertions.assertFalse(d_GameSession.getContinentsInSession().containsKey("Asia"));
    }

    /**
     * Test case to verify country insertion.
     */
    @Test
    public void whenMapEditorAddCountry_AddCountryTest() {
        String input1 = "editcontinent -add Asia 6\neditcountry -add Iran Asia\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        d_MapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
        Assertions.assertTrue(d_GameSession.getCountriesInSession().containsKey("Iran"));
    }

    /**
     * Test case to verify country removal.
     */
    @Test
    public void whenSceneEditorAddCountry_RemoveCountry() {
        String input1 = "editcontinent -add Asia 6\neditcountry -remove Iran\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        d_MapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
        Assertions.assertFalse(d_GameSession.getCountriesInSession().containsKey("Iran"));
    }

    /**
     * Test case to verify neighbors creation.
     */
    @Test
    public void whenMapEditorAddNeighbor_AddNeighborTest() {
        String input1 = "editcontinent -add Asia 6\neditcountry -add Iran Asia\n" +
                "editcountry -add Turkey Asia\neditneighbor -add Iran Turkey\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        d_MapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
        Assertions.assertTrue(d_GameSession.getCountriesInSession().get("Iran").isAdjacentTo("Turkey"));
    }

    /**
     * Test case to verify neighbors deletion.
     */
    @Test
    public void whenMapEditorRemoveNeighbor_RemoveNeighborTest() {
        String input1 = "editcontinent -add Asia 6\neditcountry -add Iran Asia\n" +
                "editcountry -add Turkey Asia\neditneighbor -remove Iran Turkey\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        d_MapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
        Assertions.assertFalse(d_GameSession.getCountriesInSession().get("Iran").isAdjacentTo("Turkey"));
    }

    /**
     * Test case to verify show map command.
     */
    @Test
    public void whenSceneEditorShowMap_ShowMap() {
        String input1 = "editcontinent -add Asia 6\neditcountry -add Iran Asia\n" +
                "editcountry -add Turkey Asia\neditneighbor -remove Iran Turkey\nshowmap\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        Assertions.assertEquals(d_MapEditorService.handleGamePhase(GamePhase.MAP_EDITOR), GamePhase.START_UP);
    }

    /**
     * Test case to verify list maps command.
     */
    @Test
    public void whenMapEditorListMaps_ListMapsTest() {
        String input1 = "listmaps\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        Assertions.assertEquals(d_MapEditorService.handleGamePhase(GamePhase.MAP_EDITOR), GamePhase.START_UP);
    }

    /**
     * Test case to verify help command.
     */
    @Test
    public void whenMapEditorHelp_HelpTest() {
        String input1 = "help\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        Assertions.assertEquals(d_MapEditorService.handleGamePhase(GamePhase.MAP_EDITOR), GamePhase.START_UP);
    }

    /**
     * Test case for invalid user command.
     */
    @Test
    public void whenMapEditorInvalidCommand_InvalidCommandTest() {
        String input1 = "invalid cmd\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        Assertions.assertEquals(d_MapEditorService.handleGamePhase(GamePhase.MAP_EDITOR), GamePhase.START_UP);
    }

    /**
     * Test case to verify edit map command.
     */
    @Test
    public void whenMapEditorEditMap_EditMapTest() {
        String input1 = "editmap artic.map\neditcountry -add ABC Canada\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        d_MapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
//        Assertions.assertTrue(d_GameSession.getCountriesInSession().containsKey("ABC"));
    }

    /**
     * Test case to verify save map command.
     */
    @Test
    public void whenMapEditorSaveMap_SaveMap() {
        String input1 = "editmap artic.map\neditcountry -add ABC Canada\nsavemap CanUpdateTest\nexit";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        d_MapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
        Assertions.assertFalse(d_GameSession.getCountriesInSession().containsKey("ABC"));
    }
}
