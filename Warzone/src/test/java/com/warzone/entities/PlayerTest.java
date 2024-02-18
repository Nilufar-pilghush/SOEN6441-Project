package test.java.com.warzone.entities;

        import main.java.warzone.entities.Player;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import java.util.Set;

        import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */
public class PlayerTest {

    /**
     * Class to be tested
     */
    private Player player;

    /**
     * Method to set up player before testing
     */
    @BeforeEach
    public void setUp() {
        player = new Player("Nasrin");
    }

    /**
     * Test case to verify get player name
     */
    @Test
    public void getNameTest() {
        assertEquals("Nasrin", player.getName());
    }

    /**
     * Test case to verify set player name
     */
    @Test
    public void setNameTest() {
        player.setName("Niloufar");
        assertEquals("Niloufar", player.getName());
    }

    /**
     * Test case to get owned country ids
     */
    @Test
    public void getOwnedCountryIdsInitiallyEmptyTest() {
        Set<String> ownedCountryIds = player.getOwnedCountries();
        assertNotNull(ownedCountryIds);
        assertTrue(ownedCountryIds.isEmpty());
    }

    /**
     * Test case to get owned countries after adding country
     */
    @Test
    public void getOwnedCountryIdsAfterAddingTest() {
        player.getOwnedCountries().add("Country1");
        player.getOwnedCountries().add("Country2");

        Set<String> ownedCountryIds = player.getOwnedCountries();
        assertNotNull(ownedCountryIds);
        assertEquals(2, ownedCountryIds.size());
        assertTrue(ownedCountryIds.contains("Country1"));
        assertTrue(ownedCountryIds.contains("Country2"));
    }
}
