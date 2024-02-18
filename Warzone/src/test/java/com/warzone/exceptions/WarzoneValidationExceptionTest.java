package test.java.com.warzone.exceptions;

        import main.java.warzone.exceptions.WarzoneValidationException;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.Test;

/**
 * Test class
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */
public class WarzoneValidationExceptionTest {

    /**
     * Test case to verify no msg exception creation
     */
    @Test
    public void whenEmptyWarzoneRuntimeException_ExpectedCreationTest() {
        WarzoneValidationException warzoneValidationException = new WarzoneValidationException();
        Assertions.assertTrue(warzoneValidationException instanceof WarzoneValidationException);
    }

    /**
     * Test case to verify exception creation with msg
     */
    @Test
    public void whenWarzoneBaseException_ExpectedCreationTest() {
        String msg = "This is an exception";
        WarzoneValidationException warzoneValidationException = new WarzoneValidationException(msg);
        Assertions.assertEquals(msg, warzoneValidationException.getMessage());
    }
}
