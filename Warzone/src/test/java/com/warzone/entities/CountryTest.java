package test.java.com.warzone.entities;

        import main.java.warzone.entities.Country;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import java.util.Map;
        import static org.junit.jupiter.api.Assertions.*;

    /**
     * Provides comprehensive test coverage for the {@link Country} class, focusing on
     * validating the functionality of ID, name, continent presence, and adjacency relationship management.
     * This includes testing the retrieval and assignment of country properties and the integrity of the adjacency relationships.
     *
     * @author Niloufar Pilgush
     * @author Nasrin Maarefi
     * @author Jerome Kithinji
     * @author Ali sayed Salehi
     * @author Fatemeh Chaji
     * @version 2.0.0
     */
    public class CountryTest {
        private Country d_country;

        /**
         * Initializes a {@link Country} instance with predefined attributes before each test, ensuring a consistent test environment.
         */

        @BeforeEach
        public void setUp() {
            d_country = new Country(100, "Iran", "Yes");
        }

        /**
         * Validates the retrieval of a country's ID, ensuring the getter method correctly returns the ID set upon instantiation.
         */

        @Test
        public void testGetId() {
            assertEquals(100, d_country.getId());
        }

        /**
         * Verifies that the country's name is correctly retrieved and consistent with initialization.
         */

        @Test
        public void testGetName() {
            assertEquals("Iran", d_country.getName());
        }

        /**
         * Tests the ability to change and retrieve a country's name.
         */

        @Test
        public void testSetName() {
            d_country.setName("Iran");
            assertEquals("Iran", d_country.getName());
        }

        /**
         * Verifies the retrieval of a country's continent presence status.
         */

        @Test
        public void testGetPresentInContinent() {
            assertEquals("Yes", d_country.getIsInContinent());
        }

        /**
         * Tests setting and getting a country's continent presence status.
         */

        @Test
        public void testSetPresentInContinent() {
            d_country.setIsInContinent("Asia");
            assertEquals("Asia", d_country.getIsInContinent());
        }

        /**
         * Verifies that a new country's adjacent countries map is initially empty.
         */

        @Test
        public void testGetAdjacentCountriesInitiallyEmpty() {
            assertTrue(d_country.getAdjacentCountries().isEmpty());
        }

        /**
         * Tests the addition of adjacent countries and verifies the updated adjacency relationship.
         */

        @Test
        public void testAddAdjacentCountry() {
            d_country.addAdjacentCountry(2L, "Country2");
            d_country.addAdjacentCountry(3L, "Country3");

            Map<Long, String> adjacentCountries = d_country.getAdjacentCountries();
            assertEquals(Map.of(2L, "Country2", 3L, "Country3"), adjacentCountries);
        }
    }
