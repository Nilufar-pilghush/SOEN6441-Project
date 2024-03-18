package test.java.warzone.services.impl;

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
         * Initializes the map editor service before each test.
         */
        public MapEditorServiceImplTest() {
            d_mapEditorService = new MapEditorServiceImpl();
        }

        /**
         * Sets up a new game session for each test to ensure a clean environment.
         */
        @BeforeEach
        public void setUp() {
            d_gameSession = GameSession.getInstance();
            d_gameSession.clearPreviousSession();
        }

        /**
         * Tests that adding a continent correctly updates the game session.
         */
        @Test
        public void testAddContinent() {
            String input = "editcontinent -add Asia 6\nexit";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
            Assertions.assertTrue(d_gameSession.getContinentsInSession().containsKey("Asia"));
        }

        /**
         * Tests that removing a continent correctly updates the game session.
         */
        @Test
        public void testRemoveContinent() {
            String input = "editcontinent -add Asia 6\neditcontinent -remove Asia\nexit";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
            Assertions.assertFalse(d_gameSession.getContinentsInSession().containsKey("Asia"));
        }

        /**
         * Tests that adding a country correctly updates the game session.
         */
        @Test
        public void testAddCountry() {
            String input = "editcontinent -add Asia 6\neditcountry -add Iran Asia\nexit";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
            Assertions.assertTrue(d_gameSession.getCountriesInSession().containsKey("Iran"));
        }

        /**
         * Tests that removing a country correctly updates the game session.
         */
        @Test
        public void testRemoveCountry() {
            String input = "editcontinent -add Asia 6\neditcountry -add India Asia\neditcountry -remove India\nexit";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
            Assertions.assertFalse(d_gameSession.getCountriesInSession().containsKey("India"));
        }

        /**
         * Tests that adding a neighbor relationship between countries is correctly updated in the game session.
         */
        @Test
        public void testAddNeighbor() {
            String input = "editcontinent -add Asia 6\neditcountry -add Iran Asia\neditcountry -add Turkey Asia\neditneighbor -add Iran Turkey\nexit";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
            Assertions.assertTrue(d_gameSession.getCountriesInSession().get("Iran").isAdjacentTo("Turkey"));
        }

        /**
         * Tests that removing a neighbor relationship between countries is correctly updated in the game session.
         */
        @Test
        public void testRemoveNeighbor() {
            String input = "editcontinent -add Asia 6\neditcountry -add Iran Asia\neditcountry -add Turkey Asia\neditneighbor -remove Iran Turkey\nexit";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
            Assertions.assertFalse(d_gameSession.getCountriesInSession().get("Iran").isAdjacentTo("Turkey"));
        }

        /**
         * Tests the functionality of the show map command within the map editor service.
         */
        @Test
        public void testShowMap() {
            String input = "editcontinent -add Asia 6\neditcountry -add Iran Asia\neditcountry -add Turkey Asia\neditneighbor -add Iran Turkey\nshowmap\nexit";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            Assertions.assertEquals(GamePhase.START_UP, d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR));
        }

        /**
         * Tests the functionality of the list maps command within the map editor service.
         */
        @Test
        public void testListMaps() {
            String input = "listmaps\nexit";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            Assertions.assertEquals(GamePhase.START_UP, d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR));
        }

        /**
         * Tests the functionality of the help command within the map editor service.
         */
        @Test
        public void testHelpCommand() {
            String input = "help\nexit";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            Assertions.assertEquals(GamePhase.START_UP, d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR));
        }

        /**
         * Tests the handling of invalid commands within the map editor service.
         */
        @Test
        public void testInvalidCommand() {
            String input = "invalid cmd\nexit";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            Assertions.assertEquals(GamePhase.START_UP, d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR));
        }

        /**
         * Tests editing a map with a specific command within the map editor service.
         */
        @Test
        public void testEditMap() {
            String input = "editmap artic.map\neditcountry -add ABC Canada\nexit";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
            Assertions.assertFalse(d_gameSession.getCountriesInSession().containsKey("ABC"));
        }

        /**
         * Tests saving a map with a specific command within the map editor service.
         */
        @Test
        public void testSaveMap() {
            String input = "editmap artic.map\neditcountry -add ABC Canada\nsavemap CanUpdateTest\nexit";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            d_mapEditorService.handleGamePhase(GamePhase.MAP_EDITOR);
            Assertions.assertFalse(d_gameSession.getCountriesInSession().containsKey("ABC"));
        }
    }
