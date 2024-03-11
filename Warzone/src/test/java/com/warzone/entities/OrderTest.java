package test.java.com.warzone.entities;

        import main.java.warzone.entities.GameSession;
        import main.java.warzone.entities.Order;
        import main.java.warzone.exceptions.WarzoneValidationException;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;

    /**
     * This class tests the functionalities of the {@link Order} class, ensuring that orders are correctly
     * created, modified, and executed within a game session. It includes tests for setting and getting
     * player names, source and target countries, the number of armies, and the execution of orders.
     *
     * @author Niloufar Pilgush
     * @author Nasrin Maarefi
     * @author Jerome Kithinji
     * @author Ali sayed Salehi
     * @author Fatemeh Chaji
     * @version 2.0.0
     */
    public class OrderTest {

        private Order d_order;
        private GameSession d_gameSession;

        /**
         * Initializes test environment before each test case.
         */

        @BeforeEach
        public void setUp() {
            d_gameSession = GameSession.getInstance();
            d_gameSession.clearPreviousSession();
            d_order = new Order("Player1", "Iran", "Canada", 10);
        }

        /**
         * Tests retrieving the player's name from an order.
         */

        @Test
        public void testGetPlayerName() {
            Assertions.assertEquals("Player1", d_order.getPlayerName());
        }

        /**
         * Tests setting the player's name in an order.
         */
        @Test
        public void testSetPlayerName() {
            d_order.setPlayerName("Player2");
            Assertions.assertEquals("Player2", d_order.getPlayerName());
        }

        /**
         * Tests retrieving the source country name from an order.
         */
        @Test
        public void testGetSourceCountry() {
            Assertions.assertEquals("Iran", d_order.getSourceCountry());
        }

        /**
         * Tests setting the source country name in an order.
         */
        @Test
        public void testSetSourceCountry() {
            d_order.setSourceCountry("Iran");
            Assertions.assertEquals("Iran", d_order.getSourceCountry());
        }

        /**
         * Tests retrieving the target country name from an order.
         */

        @Test
        public void testGetTargetCountry() {
            Assertions.assertEquals("Canada", d_order.getTargetCountry());
        }

        /**
         * Tests setting the target country name in an order.
         */
        @Test
        public void testSetTargetCountry() {
            d_order.setTargetCountry("Canada");
            Assertions.assertEquals("Canada", d_order.getTargetCountry());
        }

        /**
         * Tests retrieving the number of armies from an order.
         */
        @Test
        public void testGetNumberOfArmies() {
            Assertions.assertEquals(10, d_order.getNumberOfArmies());
        }

        /**
         * Tests setting the number of armies in an order.
         */
        @Test
        public void testSetNumberOfArmies() {
            d_order.setNumberOfArmies(15);
            Assertions.assertEquals(15, d_order.getNumberOfArmies());
        }

        /**
         * Tests the execution of an order within a game session, ensuring no exceptions are thrown under valid conditions.
         * @throws WarzoneValidationException if the order execution fails due to invalid conditions
         */
        @Test
        public void testExecuteOrder() throws WarzoneValidationException {
            d_order.setPlayerName("Player2");
            d_gameSession.createPlayer("Player2");
            d_gameSession.createContinent("Asia", String.valueOf(10));
            d_gameSession.createCountry("Iran", "Asia");
            d_gameSession.createCountry("Turkey", "Asia");
            d_gameSession.getCountriesInSession().get("Iran").setOwner("Player1");
            d_gameSession.getCountriesInSession().get("Turkey").setOwner("Player2");
            d_gameSession.makeNeighbors("Iran", "Turkey");
        }
}

