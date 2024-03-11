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
     * Unit tests for {@link GameLoopServiceImpl} focusing on the execution of user commands during the main game loop phase.
     * This class ensures that the game loop service can correctly handle various phases and commands, reflecting changes
     * within a {@link GameSession}.
     *
     * @author Niloufar Pilgush
     * @author Nasrin Maarefi
     * @author Jerome Kithinji
     * @author Ali sayed Salehi
     * @author Fatemeh Chaji
     * @version 2.0.0
     */
    public class GameLoopServiceImplTest {

        private GameLoopServiceImpl d_gameLoopService;
        private GameSession d_gameSession;

        /**
         * Initializes the GameLoopServiceImpl and {@link GameSession} before each test case to ensure
         * a fresh environment for testing the game loop's handling of user commands.
         */
        @BeforeEach
        public void setUp() throws WarzoneValidationException {
            d_gameLoopService = new GameLoopServiceImpl();
        }

        /**
         * Verifies that {@link GameLoopServiceImpl#handleGamePhase(GamePhase)} correctly processes user commands
         * during the MAIN_GAME_LOOP phase without throwing any exceptions. It simulates user input for showing the map
         * and exiting the game to test the method's resilience and correct behavior.
         */
        @Test
        public void testMainGameLoopPhaseHandling() {
            String inputCommands = "showmap\nexit";
            InputStream systemInBackup = System.in; // Backup System.in to restore it later
            System.setIn(new ByteArrayInputStream(inputCommands.getBytes()));

            Assertions.assertDoesNotThrow(() -> d_gameLoopService.handleGamePhase(GamePhase.MAIN_GAME_LOOP),
                    "GameLoopServiceImpl should handle MAIN_GAME_LOOP phase without throwing exceptions.");

            System.setIn(systemInBackup); // Restore System.in
        }
        /**
         * Cleans up the game session after each test case by clearing the previously created session.
         */
        @AfterEach
        public void cleanUp() {
            d_gameSession.clearPreviousSession();
        }
    }
