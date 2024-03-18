package test.java.warzone.entities;

import main.java.warzone.entities.Continent;
import main.java.warzone.entities.Country;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit test cases for the Continent class.
 * Includes tests for verifying the correct operation of setter and getter methods for
 * continent name, control value, owner, and countries collection.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */

public class ContinentTest {
    private Continent d_continent;

    /**
     * Sets up the test environment for each test method.
     * This method is executed before each test, ensuring a fresh instance of {@link Continent}.
     */

    @BeforeEach
    public void setUp() {
        d_continent = new Continent();
    }

    /**
     * Tests both setting and getting the continent's name.
     * Verifies if the name set is correctly retrieved.
     */

    @Test
    public void testSetAndGetContinentName() {
        final String testName = "Asia";
        d_continent.setName(testName);
        Assertions.assertEquals(testName, d_continent.getName());
    }

    /**
     * Tests both setting and getting the continent's control value.
     * Verifies if the control value set is correctly retrieved.
     */

    @Test
    public void testSetControlValueAndGetControlValue() {
        final int testControlValue = 5;
        d_continent.setControlValue(testControlValue);
        Assertions.assertEquals(testControlValue, d_continent.getControlValue());
    }

    /**
     * Test case to verify continent control value retrieval
     */

    @Test
    public void testGetControlValueReturnsCorrectValue() {
        d_continent.setControlValue(5);
        Assertions.assertEquals(5, d_continent.getControlValue());
    }

    /**
     * Tests both setting and getting the continent's owner.
     * Verifies if the owner set is correctly retrieved.
     */

    @Test
    public void testSetOwnerAndGetOwner() {
        final String testOwner = "Player1";
        d_continent.setOwner(testOwner);
        Assertions.assertEquals(testOwner, d_continent.getOwner());
    }

    /**
     * Tests the retrieval of countries from the continent.
     * Verifies if the countries added to the continent's collection are correctly retrieved.
     */

    @Test
    public void testGetCountries() {
        d_continent.getCountries().put("Iran", new Country("Iran", "Asia", 1L));
        Assertions.assertTrue(d_continent.getCountries().containsKey("Iran"));
    }
}
