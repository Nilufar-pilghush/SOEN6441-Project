package test.java.warzone.exceptions;

import main.java.warzone.exceptions.WarzoneValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link WarzoneValidationException} to ensure correct behavior for both instantiation without a message
 * and with a specific error message. These tests verify the basic functionalities of the exception class, ensuring it
 * behaves as expected under different conditions.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class WarzoneValidationExceptionTest {
    /**
     * Verifies that WarzoneValidationException can be instantiated without an error message.
     * This test ensures that an exception object is created and is an instance of WarzoneValidationException.
     */
    @Test
    public void testExceptionInstantiationWithoutMessage() {
        WarzoneValidationException exception = new WarzoneValidationException();
        Assertions.assertTrue(exception instanceof WarzoneValidationException, "Should be an instance of WarzoneValidationException.");
    }

    /**
     * Verifies that WarzoneValidationException can be instantiated with a specific error message.
     * This test checks that the provided message is correctly associated with the exception object.
     */
    @Test
    public void testExceptionInstantiationWithMessage() {
        String expectedMessage = "This is an exception";
        WarzoneValidationException exception = new WarzoneValidationException(expectedMessage);
        Assertions.assertEquals(expectedMessage, exception.getMessage(), "The exception message should match the provided message.");
    }
}
