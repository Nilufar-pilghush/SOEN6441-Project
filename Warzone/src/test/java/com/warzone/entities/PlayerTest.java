package test.java.com.warzone.entities;

        import main.java.warzone.entities.Player;
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
    private Player d_player;

    /**
     * Initializes a {@link Player} instance with a predefined name before each test.
     */

    @BeforeEach
    public void setUp() {
        d_player = new Player("Player2");
    }

    /**
     * Tests whether the player's name is correctly retrieved.
     */

    @Test
    public void testGetName() {
        assertEquals("Player2", d_player.getName(), "Player name should match the expected value.");
    }
    /**
     * Tests whether the player's name can be successfully updated.
     */
    @Test
    public void testSetName() {
        d_player.setName("Player1");
        assertEquals("Player1", d_player.getName(), "Player name should be updated correctly.");
    }
    /**
     * Tests that the initial collection of owned country IDs is empty.
     * This verifies that a player does not own any countries immediately after instantiation.
     */
    @Test
    public void testGetOwnedCountryIdsInitiallyEmpty() {
        Set<String> ownedCountryIds = d_player.getOwnedCountries();
        assertNotNull(ownedCountryIds, "Owned countries set should not be null.");
        assertTrue(ownedCountryIds.isEmpty(), "Initially, the player should not own any countries.");
    }
    /**
     * Tests the functionality to add country IDs to a player's set of owned countries,
     * verifying both the addition and the correct retrieval of these IDs.
     */
    @Test
    public void testGetOwnedCountryIdsAfterAdding() {
        d_player.getOwnedCountries().add("Country1");
        d_player.getOwnedCountries().add("Country2");

        Set<String> ownedCountryIds = d_player.getOwnedCountries();
        assertNotNull(ownedCountryIds, "Owned countries set should not be null.");
        assertEquals(2, ownedCountryIds.size(), "The player should own exactly two countries.");
        assertTrue(ownedCountryIds.contains("Country1"), "The player should own Country1.");
        assertTrue(ownedCountryIds.contains("Country2"), "The player should own Country2.");
    }
}
