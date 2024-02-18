package test.java.com.warzone.exceptions;

        import main.java.warzone.exceptions.WarzoneRuntimeException;
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
public class WarzoneRuntimeExceptionTest {

    /**
     * Test case to verify no msg exception creation
     */
    @Test
    public void whenEmptyWarzoneRuntimeException_ExpectedCreationTest() {
        WarzoneRuntimeException warzoneRuntimeException = new WarzoneRuntimeException();
        Assertions.assertTrue(warzoneRuntimeException instanceof WarzoneRuntimeException);
    }

    /**
     * Test case to verify exception creation with msg
     */
    @Test
    public void whenWarzoneBaseException_ExpectedCreationTest() {
        String msg = "This is an exception";
        WarzoneRuntimeException warzoneRuntimeException = new WarzoneRuntimeException(msg);
        Assertions.assertEquals(msg, warzoneRuntimeException.getMessage());
    }
}
