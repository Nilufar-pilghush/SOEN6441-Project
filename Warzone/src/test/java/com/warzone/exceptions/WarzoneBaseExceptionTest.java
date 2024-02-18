package test.java.com.warzone.exceptions;

        import main.java.warzone.exceptions.WarzoneBaseException;
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
public class WarzoneBaseExceptionTest {

    /**
     * Test case to verify no msg exception creation
     */
    @Test
    public void whenEmptyWarzoneBaseException_ExpectedCreationTest(){
        WarzoneBaseException warzoneBaseException = new WarzoneValidationException();
        Assertions.assertTrue(warzoneBaseException instanceof  WarzoneBaseException);
    }

    /**
     * Test case to verify exception creation with msg
     */
    @Test
    public void whenWarzoneBaseException_ExpectedCreationTest(){
        String msg = "This is an exception";
        WarzoneBaseException warzoneBaseException1 = new WarzoneValidationException(msg);
        Assertions.assertEquals(msg, warzoneBaseException1.getMessage());
    }
}
