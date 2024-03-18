package test.java.warzone.services.impl;

        import main.java.warzone.entities.GamePhase;
        import main.java.warzone.entities.GameSession;
        import main.java.warzone.exceptions.WarzoneValidationException;
        import main.java.warzone.services.impl.OrderExecutorServiceImpl;
        import org.junit.jupiter.api.AfterEach;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;

    /**
     * Tests the functionalities of {@link OrderExecutorServiceImpl} by simulating order execution within a game session.
     * It ensures that orders are correctly executed as part of the game phase transition from EXECUTE_ORDERS to MAIN_GAME_LOOP.
     * This class sets up a mock game environment, assigns countries and continents, and validates the execution of deploy orders.
     *
     * @author Niloufar Pilgush
     * @author Nasrin Maarefi
     * @author Jerome Kithinji
     * @author Ali sayed Salehi
     * @author Fatemeh Chaji
     * @version 2.0.0
     */
    public class OrderExecutorServiceImplTest {
        private OrderExecutorServiceImpl d_orderExecutorService;
        private GameSession d_gameSession;
        /**
         * Initializes the OrderExecutorService and GameSession for testing.
         */
        public OrderExecutorServiceImplTest() {
            d_orderExecutorService = new OrderExecutorServiceImpl();
        }

        /**
         * Sets up a game session with predefined continents, countries, neighbors, and player orders before each test.
         * This setup simulates a minimal game state necessary for testing order execution.
         *
         * @throws WarzoneValidationException if there's an issue creating the game world components.
         */
        @BeforeEach
        public void setUp() throws WarzoneValidationException {
            d_gameSession = GameSession.getInstance();
            d_gameSession.clearPreviousSession();
            d_gameSession.createContinent("Asia", "5");
            d_gameSession.createCountry("Iran", "Asia");
            d_gameSession.createCountry("Turkey", "Asia");
            d_gameSession.makeNeighbors("Iran", "Turkey");
            d_gameSession.createPlayer("Player1");
            d_gameSession.getCountriesInSession().get("Iran").setOwner("Player1");
            d_gameSession.getContinentsInSession().get("Asia").setOwner("Player1");
            d_gameSession.getPlayers().get("Player1").addDeployOrder("Turkey", 2);
        }
        /**
         * Validates that the order execution phase transitions the game to the MAIN_GAME_LOOP phase correctly.
         * Asserts the game phase after executing orders to ensure the phase transition occurs as expected.
         */
        @Test
        public void testOrderExecution() {
            Assertions.assertEquals(GamePhase.MAIN_GAME_LOOP,
                    d_orderExecutorService.handleGamePhase(GamePhase.EXECUTE_ORDERS),
                    "Game should transition to MAIN_GAME_LOOP after executing orders.");
        }

        /**
         * Cleans up the game session by clearing any existing data after each test to ensure a clean state for subsequent tests.
         */
        @AfterEach
        public void cleanUp() {
            d_gameSession.clearPreviousSession();
        }
    }
