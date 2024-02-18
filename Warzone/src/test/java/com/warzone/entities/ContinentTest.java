package test.java.com.warzone.entities;
        import main.java.warzone.entities.Continent;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.Test;

/**
 * JUnit test cases for the Continent class.
 * Test case to verify the correct retrieval of the continent name.
 * Test case to verify the correct setting of the continent name.
 * Test case to verify the correct retrieval of the continent control value.
 * Test case to verify the correct setting of the continent control value.
 * Test case to verify the correct getting of the continent owner value.
 * Test case to verify the correct setting of the continent owner value.
 * Test case to verify the correct retrieval of the countries of continent.
 *
 * @author Snehil Sharma
 * @author Jatin
 * @author Kenish Rajeshbhai Halani
 * @author Udhisha
 * @author Aazam Arvind
 * @version 1.0.0
 */
public class ContinentTest {

    /**
     * Class to be tested
     */
    private Continent d_Continent;

    /**
     * Constructor to initialize ContinentTest class
     */
    public ContinentTest(){
        d_Continent = new Continent();
    }

    /**
     * Test case to verify continent name retrieval
     */
    @Test
    public void whenGetName_ExpectContinentName(){
        d_Continent.setName("Asia");
        Assertions.assertEquals("Asia", d_Continent.getName());
    }

    /**
     * Test case to verify continent name setting
     */
    @Test
    public void whenSetName_ExpectContinentNameSet(){
        d_Continent.setName("Asia");
        Assertions.assertEquals("Asia", d_Continent.getName());
    }

    /**
     * Test case to verify continent control value retrieval
     */
    @Test
    public void whenGetControl_ExpectContinentControlValue(){
        d_Continent.setControlValue(5);
        Assertions.assertEquals(5, d_Continent.getControlValue());
    }

    /**
     * Test case to verify continent control value setting
     */
    @Test
    public void whenSetControl_ExpectContinentControlValueSet(){
        d_Continent.setControlValue(5);
        Assertions.assertEquals(5, d_Continent.getControlValue());
    }

    /**
     * Test case to verify continent owner retrieval
     */
    @Test
    public void whenGetOwner_ExpectContinentOwner(){
        d_Continent.setOwner("Snehil");
        Assertions.assertEquals("Snehil", d_Continent.getOwner());
    }

    /**
     * Test case to verify continent owner setting
     */
    @Test
    public void whenSetOwner_ExpectContinentOwnerSet(){
        d_Continent.setOwner("Snehil");
        Assertions.assertEquals("Snehil", d_Continent.getOwner());
    }

    /**
     * Test case to verify countries retrieval from continent
     */
    @Test
    public void whenGetCountries_ExpectCountries(){
//        d_Continent.getCountries().put("India", new Country("India", "Asia", 1L));
//        Assertions.assertTrue(d_Continent.getCountries().containsKey("India"));
    }
}

