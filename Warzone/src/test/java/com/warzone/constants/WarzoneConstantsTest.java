package test.java.com.warzone.constants;
import main.java.warzone.constants.WarzoneConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test class to validate the constants value stored in main.java.main.java.com.warzone constants
 *
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */
public class WarzoneConstantsTest {

    /**
     * Test case to validate edit continent constant value
     */
    @Test
    public void whenWarzoneConstantUsed_ExpectCorrectValue(){
        Assertions.assertEquals("editcontinent", WarzoneConstants.EDIT_CONTINENT);
    }

    /**
     * Test case to validate edit country constant value
     */
    @Test
    public void whenWarzoneConstantUsed_ExpectCorrectValue2(){
        Assertions.assertEquals("editcountry", WarzoneConstants.EDIT_COUNTRY);
    }

    /**
     * Test case to validate edit neighbor constant value
     */
    @Test
    public void whenWarzoneConstantUsed_ExpectCorrectValue3(){
        Assertions.assertEquals("editneighbor", WarzoneConstants.EDIT_NEIGHBOR);
    }
}
