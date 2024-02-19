package test.java.com.warzone.entities;

        import main.java.warzone.entities.Country;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import java.util.Map;
        import static org.junit.jupiter.api.Assertions.*;

    /**
     * JUnit test cases for the Country class.
     * Test case to verify the correct retrieval of the country ID, name.
     * Test case to verify the correct setting of the country name.
     * Test case to verify the correct retrieval and setting of the continent name.
     * Test case to ensure that the map of adjacent countries is initially empty.
     * Test case to verify the correct addition of adjacent countries to the map.
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
         * Class to be tested.
         */
        private Country country;

        /**
         * Method to se tup country before every test.
         */
        @BeforeEach
        public void setUp() {
            country = new Country(100, "Iran", "Yes");
        }

        /**
         * Test case to test getId
         */
        @Test
        public void getIdTest() {
            assertEquals(100, country.getId());
        }

        /**
         * Test case to test getName
         */
        @Test
        public void getNameTest() {
            assertEquals("Iran", country.getName());
        }

        /**
         * Test case to set name
         */
        @Test
        public void setNameTest() {
            country.setName("Iran");
            assertEquals("Iran", country.getName());
        }

        /**
         * Test case to check if present in country
         */
        @Test
        public void getPresentInContinentTest() {
            assertEquals("Yes", country.getIsInContinent());
        }

        /**
         * Test case to check set present in continent
         */
        @Test
        public void setPresentInContinentTest() {
            country.setIsInContinent("Asia");
            assertEquals("Asia", country.getIsInContinent());
        }

        /**
         * Test case to get adjacent countries initially empty
         */
        @Test
        public void getAdjacentCountriesInitiallyEmptyTest() {
            Map<Long, String> adjacentCountries = country.getAdjacentCountries();
            assertNotNull(adjacentCountries);
            assertTrue(adjacentCountries.isEmpty());
        }

        /**
         * Test case to add adjacent country
         */
        @Test
        public void addAdjacentCountryTest() {
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
