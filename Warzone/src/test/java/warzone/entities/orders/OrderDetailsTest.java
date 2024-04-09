package test.java.warzone.entities.orders;

import main.java.warzone.entities.orders.OrderDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link OrderDetails} class to ensure its properties are correctly managed.
 * Validates the functionality of getters and setters for player name, target country, and number of armies.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class OrderDetailsTest {
    /**
     * Class to be tested
     */
    private OrderDetails d_OrderDetails;

    /**
     * Tests setting and getting the player name property of OrderDetails.
     * Verifies that the player name is correctly assigned and retrieved.
     */
    @Test
    public void testPlayerNameManagement() {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setPlayerName("Player1");
        Assertions.assertEquals("Player1", orderDetails.getPlayerName(), "Player name should be correctly set and retrieved.");
    }
    /**
     * Method to test set player name
     */
    @Test
    public void whenSetPlayerName_ExpectPlayerName(){
        d_OrderDetails = new OrderDetails();
        d_OrderDetails.setPlayerName("Snehil");
        Assertions.assertEquals("Snehil", d_OrderDetails.getPlayerName());
    }

    /**
     * Method to get tatget country
     */
    @Test
    public void whenGetTargetCountry_ExpectCountry(){
        d_OrderDetails = new OrderDetails();
        d_OrderDetails.setTargetCountry("Sri Lanka");
        Assertions.assertEquals("Sri Lanka", d_OrderDetails.getTargetCountry());
    }

    /**
     * Tests setting and getting the target country property of OrderDetails.
     * Ensures the target country is accurately set and fetched.
     */
    @Test
    public void testTargetCountryManagement() {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setTargetCountry("Turkey");
        Assertions.assertEquals("Turkey", orderDetails.getTargetCountry(), "Target country should be correctly set and retrieved.");
    }

    /**
     * Tests setting and getting the number of armies in OrderDetails.
     * Checks that the number of armies is properly assigned and accessed.
     */
//    @Test
//    public void testNumberOfArmiesManagement() {
//        OrderDetails orderDetails = new OrderDetails();
//        orderDetails.setNumberOfArmies(10);
//        Assertions.assertEquals(10, orderDetails.getNumberOfArmies(), "Number of armies should be correctly set and retrieved.");
//    }

    /**
     * Method to get number of armies
     */
    @Test
    public void whenGetNumberOfArmies_ExpectArmies(){
        d_OrderDetails = new OrderDetails();
        d_OrderDetails.setNumberOfArmies(10);
        Assertions.assertEquals(10, d_OrderDetails.getNumberOfArmies());
    }

    /**
     * Method to set number of armies
     */
    @Test
    public void whenSetNumberOfArmies_ExpectArmiesSet(){
        d_OrderDetails = new OrderDetails();
        d_OrderDetails.setNumberOfArmies(10);
        Assertions.assertEquals(10, d_OrderDetails.getNumberOfArmies());
    }
}