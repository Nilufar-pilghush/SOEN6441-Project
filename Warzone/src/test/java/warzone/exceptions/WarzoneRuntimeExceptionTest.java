package test.java.warzone.exceptions;

        import main.java.warzone.exceptions.WarzoneRuntimeException;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link WarzoneRuntimeException} to ensure its proper instantiation and behavior.
 * Tests include instantiation without a message and with a specific error message.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */
public class WarzoneRuntimeExceptionTest {
    /**
     * Verifies that a WarzoneRuntimeException can be instantiated without providing an error message.
     * Ensures the created exception is an instance of WarzoneRuntimeException.
     */
    @Test
    public void testExceptionWithoutMessage() {
        WarzoneRuntimeException exception = new WarzoneRuntimeException();
        Assertions.assertTrue(exception instanceof WarzoneRuntimeException, "The exception should be a WarzoneRuntimeException instance.");
    }
    /**
     * Verifies that a WarzoneRuntimeException can be instantiated with an error message.
     * Asserts that the exception's message matches the expected message provided at creation.
     */
    @Test
    public void testExceptionWithMessage() {
        String expectedMessage = "This is an exception";
        WarzoneRuntimeException exception = new WarzoneRuntimeException(expectedMessage);
        Assertions.assertEquals(expectedMessage, exception.getMessage(), "The exception's message should match the expected message.");
    }
}
