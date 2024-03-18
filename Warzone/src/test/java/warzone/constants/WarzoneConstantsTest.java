package test.java.warzone.constants;

import main.java.warzone.constants.WarzoneConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests to validate the constant values in WarzoneConstants.
 * Ensures that each constant retains its expected value, guarding against accidental changes. *
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class WarzoneConstantsTest {

    /**
     * Validates the EDIT_CONTINENT constant's value.
     */
    @Test
    public void testWhenWarzoneConstantUsed_ExpectCorrectValue() {
        final String expected = "editcontinent";
        Assertions.assertEquals(expected, WarzoneConstants.EDIT_CONTINENT);
    }

    /**
     * Validates the EDIT_COUNTRY constant's value.
     */
    @Test
    public void testWhenWarzoneConstantUsed_ExpectCorrectValue2() {
        final String expected = "editcountry";
        Assertions.assertEquals(expected, WarzoneConstants.EDIT_COUNTRY);
    }

    /**
     * Validates the EDIT_NEIGHBOR constant's value.
     */
    @Test
    public void testWhenWarzoneConstantUsed_ExpectCorrectValue3Test() {
        final String expected = "editneighbor";
        Assertions.assertEquals(expected, WarzoneConstants.EDIT_NEIGHBOR);
    }
}
