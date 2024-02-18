package test.java.com.warzone.entities;

        import main.java.warzone.entities.Country;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import java.util.Map;
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
public class CountryTest {

    /**
     *
     */
    private Country country;

    /**
     *
     */
    @BeforeEach
    public void setUp() {
        country = new Country(12, "Canada", "Yes");
    }

    /**
     *
     */
    @Test
    public void testGetId() {assertEquals(12, country.getId());
    }

    /**
     *
     */
    @Test
    public void testGetName() {
        assertEquals("Canada", country.getName());
    }

    /**
     *
     */
    @Test
    public void testSetName() {
        country.setName("Iran");
        assertEquals("Iran", country.getName());
    }

    /**
     *
     */
    @Test
    public void testGetPresentInContinent() {
        assertEquals("Yes", country.getIsInContinent());
    }

    /**
     *
     */
    @Test
    public void testSetPresentInContinent() {
        country.setIsInContinent("Asia");
        assertEquals("Asia", country.getIsInContinent());
    }

    /**
     *
     */
    @Test
    public void testGetAdjacentCountriesInitiallyEmpty() {
        Map<Long, String> adjacentCountries = country.getAdjacentCountries();
        assertNotNull(adjacentCountries);
        assertTrue(adjacentCountries.isEmpty());
    }

    /**
     *
     */
    @Test
    public void testAddAdjacentCountry() {
        country.addAdjacentCountry(2L, "Country2");
        country.addAdjacentCountry(3L, "Country3");

        Map<Long, String> adjacentCountries = country.getAdjacentCountries();
        assertNotNull(adjacentCountries);
        assertEquals(2, adjacentCountries.size());
        assertTrue(adjacentCountries.containsKey(2L));
        assertTrue(adjacentCountries.containsKey(3L));
        assertEquals("Country2", adjacentCountries.get(2L));
        assertEquals("Country3", adjacentCountries.get(3L));
    }
}
