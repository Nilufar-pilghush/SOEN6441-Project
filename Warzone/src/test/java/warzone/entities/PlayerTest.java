package test.java.warzone.entities;

import main.java.warzone.entities.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Player} class.
 * This test suite verifies the functionality of player management, including
 * name management and owned country IDs management.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class PlayerTest {
    private Player player;

    /**
     * Initializes a new player instance before each test to ensure a fresh environment.
     */
    @BeforeEach
    public void setUp() {
        player = new Player("Player3");
    }

    /**
     * Tests retrieving the name of the player.
     */
    @Test
    public void testGetName() {
        assertEquals("Player3", player.getName());
    }

    /**
     * Tests setting and then retrieving a new name for the player.
     */
    @Test
    public void testSetName() {
        player.setName("Player1");
        assertEquals("Player1", player.getName());
    }

    /**
     * Verifies that the owned countries set is initially empty.
     */
    @Test
    public void testGetOwnedCountryIdsInitiallyEmpty() {
        Set<String> ownedCountryIds = player.getOwnedCountries();
        assertNotNull(ownedCountryIds);
        assertTrue(ownedCountryIds.isEmpty());
    }

    /**
     * Tests adding countries to the owned countries set and verifying the contents.
     */
    @Test
    public void testGetOwnedCountryIdsAfterAdding() {
        player.getOwnedCountries().add("Country1");
        player.getOwnedCountries().add("Country2");

        Set<String> ownedCountryIds = player.getOwnedCountries();
        assertNotNull(ownedCountryIds);
        assertEquals(2, ownedCountryIds.size());
        assertTrue(ownedCountryIds.contains("Country1"));
        assertTrue(ownedCountryIds.contains("Country2"));
    }

    /**
     * Tests removing a country from the owned countries set.
     */
    @Test
    public void testRemoveCountry() {
        player.getOwnedCountries().add("Iran");
        player.removeOwnedCountry("Iran");
        Assertions.assertEquals(Boolean.FALSE, player.getOwnedCountries().contains("Iran"));
    }

    /**
     * Tests retrieving the list of orders for a player.
     */
    @Test
    public void testGetOrderList() {
        Assertions.assertNotNull(player.getOrderList());
    }

    /**
     * Tests adding an advance order to a player's order list.
     */
    @Test
    public void testAddAdvanceOrder() {
        player.addAttackOrder("Country1", "Country2", 10);
        assertEquals(1, player.getOrderList().size());
    }

    /**
     * Tests adding a bomb order to a player's order list.
     */
    @Test
    public void testAddBombOrder() {
        player.addBombOrder("Country2");
        assertEquals(1, player.getOrderList().size());
    }

    /**
     * Tests adding a blockade order to a player's order list.
     */
    @Test
    public void testAddBlockadeOrder() {
        player.addBlockadeOrder("Country2");
        assertEquals(1, player.getOrderList().size());
    }

    /**
     * Tests adding a diplomacy order to a player's order list.
     */
    @Test
    public void testAddDiplomacyOrder() {
        player.addDiplomacyOrder("Country2");
        assertEquals(1, player.getOrderList().size());
    }

    /**
     * Tests adding an airlift order to a player's order list.
     */
    @Test
    public void testAddAirliftOrder() {
        player.addAirliftOrder("Country1", "Country2", 10);
        assertEquals(1, player.getOrderList().size());
    }

    /**
     * Tests removing an order from a player's order list.
     */
    @Test
    public void testRemoveOrder() {
        player.addAirliftOrder("Country1", "Country2", 10);
        player.removeOrder(player.getOrderList().peek());
        assertEquals(0, player.getOrderList().size());
    }

    /**
     * Tests setting and retrieving the number of armies a player has.
     */
    @Test
    public void testGetNumberOfArmies() {
        player.setNumberOfArmies(10);
        Assertions.assertEquals(10, player.getNumberOfArmies());
    }

    /**
     * Tests adding armies to a player's current total.
     */
    @Test
    public void testAddNumberOfArmies() {
        player.setNumberOfArmies(10);
        player.addArmies(10);
        Assertions.assertEquals(20, player.getNumberOfArmies());
    }

    /**
     * Tests verifying ownership of a country.
     */
    @Test
    public void testDoesOwnCountry() {
        player.addOwnedCountry("Country1");
        Assertions.assertTrue(player.ownsCountry("Country1"));
    }

    /**
     * Tests adding a card to a player's collection and verifying ownership.
     */
    @Test
    public void testAddCard() {
        player.addCard("bomb");
        Assertions.assertTrue(player.ownsCard("bomb"));
    }

    /**
     * Tests using a card from a player's collection.
     */
    @Test
    public void testUseCard() {
        player.addCard("bomb");
        Assertions.assertTrue(player.useCard("bomb"));
    }

    /**
     * Tests setting and checking if a card was earned in the current turn.
     */
    @Test
    public void testIsEarnedCardThisTurn() {
        player.setEarnedCardThisTurn(true);
        Assertions.assertTrue(player.isEarnedCardThisTurn());
    }

    /**
     * Tests adding a player to the diplomacy list and verifying diplomacy.
     */
    @Test
    public void testHasDiplomacyWith() {
        player.addDiplomacyPlayer("Player1");
        Assertions.assertTrue(player.hasDiplomacyWith("Player1"));
    }

    /**
     * Tests getting the list of players with whom diplomacy is established.
     */
    @Test
    public void testGetDiplomacyPlayers() {
        player.addDiplomacyPlayer("Player1");
        player.addDiplomacyPlayer("Player2");
        Assertions.assertEquals(2, player.getDiplomacyPlayers().size());
    }

    /**
     * Tests removing a player from the diplomacy list.
     */
    @Test
    public void testRemoveDiplomacyPlayer() {
        player.addDiplomacyPlayer("Player1");
        player.removeDiplomacyPlayer("Player1");
        Assertions.assertEquals(0, player.getDiplomacyPlayers().size());
    }

    /**
     * Tests resetting the diplomacy list for a player.
     */
    @Test
    public void testResetDiplomacyPlayers() {
        player.resetDiplomacyPlayers();
        Assertions.assertEquals(0, player.getDiplomacyPlayers().size());
    }

    /**
     * Cleans up by clearing the player's order list after each test to ensure a clean state.
     */
    @AfterEach
    public void clean() {
        player.getOrderList().clear();
    }
}
