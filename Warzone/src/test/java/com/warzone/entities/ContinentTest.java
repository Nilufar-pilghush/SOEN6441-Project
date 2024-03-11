package test.java.com.warzone.entities;

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
    private Continent d_Continent;

    /**
     * Sets up the test environment for each test method.
     * This method is executed before each test, ensuring a fresh instance of {@link Continent}.
     */

    @BeforeEach
    public void setUp() {
        d_Continent = new Continent();
    }

    /**
     * Tests both setting and getting the continent's name.
     * Verifies if the name set is correctly retrieved.
     */

    @Test
    public void testSetAndGetContinentName() {
        final String testName = "Asia";
        d_Continent.setName(testName);
        Assertions.assertEquals(testName, d_Continent.getName());
    }

    /**
     * Tests both setting and getting the continent's control value.
     * Verifies if the control value set is correctly retrieved.
     */

    @Test
    public void testSetControlValueAndGetControlValue() {
        final int testControlValue = 5;
        d_Continent.setControlValue(testControlValue);
        Assertions.assertEquals(testControlValue, d_Continent.getControlValue());
    }

    /**
     * Test case to verify continent control value retrieval
     */

    @Test
    public void whenGetControl_ExpectContinentControlValueTest(){
        d_Continent.setControlValue(5);
        Assertions.assertEquals(5, d_Continent.getControlValue());
    }

    /**
     * Tests both setting and getting the continent's owner.
     * Verifies if the owner set is correctly retrieved.
     */

    @Test
    public void testSetOwnerAndGetOwner() {
        final String testOwner = "Niloufar";
        d_Continent.setOwner(testOwner);
        Assertions.assertEquals(testOwner, d_Continent.getOwner());
    }

    /**
     * Tests the retrieval of countries from the continent.
     * Verifies if the countries added to the continent's collection are correctly retrieved.
     */

    @Test
    public void testGetCountries() {
        Country testCountry = new Country(100, "Asia", "Yes");
        d_Continent.getCountries().put("Iran", testCountry);
        Assertions.assertTrue(d_Continent.getCountries().containsKey("Iran"));
        Assertions.assertSame(testCountry, d_Continent.getCountries().get("Iran"));
    }
 }
