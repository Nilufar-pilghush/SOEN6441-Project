package test.java.warzone.services.impl;

        import main.java.warzone.entities.GamePhase;
        import main.java.warzone.entities.GameSession;
        import main.java.warzone.exceptions.WarzoneValidationException;
        import main.java.warzone.services.impl.ReinforcementServiceImpl;
        import org.junit.jupiter.api.AfterEach;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;

    /**
     * Tests the {@link ReinforcementServiceImpl} to ensure correct handling of the reinforcement phase in the game.
     * This includes setting up a game world, assigning continents and owners, and verifying the reinforcement
     * service processes reinforcement commands without throwing exceptions.
     *
     * @author Niloufar Pilgush
     * @author Nasrin Maarefi
     * @author Jerome Kithinji
     * @author Ali sayed Salehi
     * @author Fatemeh Chaji
     * @version 2.0.0
     */
    public class ReinforcementServiceImplTest {

        private ReinforcementServiceImpl d_reinforcementService;
        private GameSession d_gameSession;

        /**
         * Initializes the reinforcement service and game session for testing. Prepares the testing environment
         * by instantiating the {@link ReinforcementServiceImpl} and retrieving the singleton instance of {@link GameSession}.
         */
        public ReinforcementServiceImplTest() {
            d_reinforcementService = new ReinforcementServiceImpl();
            d_gameSession = GameSession.getInstance();
        }
        /**
         * Sets up a mock game world environment before each test. This setup includes clearing any existing
         * game session data and configuring a basic game state with continents, countries, neighbors, and a player.
         *
         * @throws WarzoneValidationException if an error occurs during game world setup.
         */
        @BeforeEach
        public void setUp() throws WarzoneValidationException {
            d_gameSession.clearPreviousSession();
            d_gameSession.createContinent("Asia", "5");
            d_gameSession.createCountry("Iran", "Asia");
            d_gameSession.createCountry("Turkey", "Asia");
            d_gameSession.makeNeighbors("Iran", "Turkey");
            d_gameSession.getContinentsInSession().get("Asia").setOwner("Player1");
            d_gameSession.createPlayer("Player1");
        }
        /**
         * Validates that the reinforcement phase can be processed without throwing exceptions,
         * ensuring that the game logic related to reinforcements is correctly implemented.
         */
        @Test
        public void testReinforcementPhaseHandling() {
            Assertions.assertDoesNotThrow(() -> d_reinforcementService.handleGamePhase(GamePhase.REINFORCEMENT),
                    "Reinforcement phase should be processed without exceptions.");
        }
        /**
         * Cleans up the game session by clearing any existing game state after each test, ensuring
         * a fresh start for subsequent tests.
         */
        @AfterEach
        public void cleanUp() {
            d_gameSession.clearPreviousSession();
        }
    }
