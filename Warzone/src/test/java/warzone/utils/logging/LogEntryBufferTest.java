package test.java.warzone.utils.logging;

import main.java.warzone.utils.logging.impl.ConsoleLogger;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
/**
 * Tests the functionality of the {@link LogEntryBuffer} class.
 * This test suite focuses on verifying the observer pattern implementation in the LogEntryBuffer,
 * specifically testing the registration mechanism for observers such as {@link ConsoleLogger}.
 * It ensures that observers can be successfully registered to the LogEntryBuffer, which is crucial
 * for the logging system's dynamic behavior.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class LogEntryBufferTest {
    /**
     * The LogEntryBuffer singleton instance to be tested.
     */
    private LogEntryBuffer d_logEntryBuffer = LogEntryBuffer.getInstance();

    /**
     * Tests the registration of an observer to the LogEntryBuffer.
     * Verifies that an observer, such as a ConsoleLogger, can be successfully registered,
     * which is indicated by the method returning true.
     */
    @Test
    public void testObserverRegistration(){
        Assertions.assertTrue(d_logEntryBuffer.registerObserver(new ConsoleLogger()));
    }
}
