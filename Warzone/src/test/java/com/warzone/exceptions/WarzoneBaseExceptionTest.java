package test.java.com.warzone.exceptions;

import main.java.warzone.exceptions.WarzoneBaseException;
import main.java.warzone.exceptions.WarzoneValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Provides unit tests for {@link WarzoneBaseException} to ensure its correct operation.
 * This class specifically tests the instantiation of exceptions derived from {@link WarzoneBaseException},
 * including cases with and without an error message.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class WarzoneBaseExceptionTest {

    /**
     * Verifies that an instance of WarzoneBaseException (or its subclass) can be created without passing a custom message.
     * Expected: The created instance is a type of WarzoneBaseException.
     */
    @Test
    public void testEmptyExceptionCreation() {
        WarzoneBaseException exception = new WarzoneValidationException();
        Assertions.assertTrue(exception instanceof WarzoneBaseException, "The exception should be an instance of WarzoneBaseException.");
    }

    /**
     * Verifies that an instance of WarzoneBaseException (or its subclass) can be created with a custom message.
     * Expected: The exception message matches the provided custom message.
     */
    @Test
    public void testExceptionCreationWithMessage() {
        String expectedMessage = "This is an exception";
        WarzoneBaseException warzoneBaseException = new WarzoneValidationException(expectedMessage);
        Assertions.assertEquals(expectedMessage, warzoneBaseException.getMessage(), "Expected the exception message to match the custom message.");
    }
}
