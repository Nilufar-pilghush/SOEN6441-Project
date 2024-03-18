package test.java.warzone.entities.orders;

import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.orders.Order;
import main.java.warzone.entities.orders.commands.AdvanceOrderCommand;
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
     * Sets up each test case by initializing game session and creating a sample order.
     */
    @BeforeEach
    public void setUp() {
        d_gameSession = GameSession.getInstance();
        d_gameSession.clearPreviousSession();
        d_order = new AdvanceOrderCommand("Player1", "Iran", "Turkey", 10);
    }

    /**
     * Validates player name retrieval.
     */
    @Test
    public void testGetPlayerName() {
        Assertions.assertEquals("Player1", d_order.getOrderDetails().getPlayerName());
    }

    /**
     * Validates player name setting.
     */
    @Test
    public void testSetPlayerName() {
        d_order.getOrderDetails().setPlayerName("Player2");
        Assertions.assertEquals("Player2", d_order.getOrderDetails().getPlayerName());
    }

    /**
     * Validates target country retrieval.
     */
    @Test
    public void testGetTargetCountry() {
        Assertions.assertEquals("Turkey", d_order.getOrderDetails().getTargetCountry());
    }

    /**
     * Validates target country setting.
     */
    @Test
    public void testSetTargetCountry() {
        d_order.getOrderDetails().setTargetCountry("Pak");
        Assertions.assertEquals("Pak", d_order.getOrderDetails().getTargetCountry());
    }

    /**
     * Validates source country retrieval.
     */
    @Test
    public void testGetSourceCountry() {
        Assertions.assertEquals("Iran", d_order.getOrderDetails().getSourceCountry());
    }

    /**
     * Validates source country setting.
     */
    @Test
    public void testSetSourceCountry() {
        d_order.getOrderDetails().setSourceCountry("China");
        Assertions.assertEquals("China", d_order.getOrderDetails().getSourceCountry());
    }

    /**
     * Validates number of armies retrieval.
     */
    @Test
    public void testGetNumberOfArmies() {
        Assertions.assertEquals(10, d_order.getOrderDetails().getNumberOfArmies());
    }

    /**
     * Validates number of armies setting.
     */
    @Test
    public void testSetNumberOfArmies() {
        d_order.getOrderDetails().setNumberOfArmies(15);
        Assertions.assertEquals(15, d_order.getOrderDetails().getNumberOfArmies());
    }
}

