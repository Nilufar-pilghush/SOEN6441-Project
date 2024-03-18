package test.java.warzone.utils.logging;

import main.java.warzone.utils.logging.impl.ConsoleLogger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class ConsoleLoggerTest {
    /**
     * The ConsoleLogger instance to be tested.
     */
    private ConsoleLogger d_ConsoleLogger = new ConsoleLogger();

    /**
     * Tests that messages are correctly logged by the ConsoleLogger.
     * Verifies that the update method returns true when a message is logged, indicating successful logging.
     */
    @Test
    public void testLoggingMessage() {
        Assertions.assertTrue(d_ConsoleLogger.update("Hello"));
    }

}
