package test.java.warzone.utils.logging;

import main.java.warzone.utils.logging.impl.GameLogger;
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
public class GameLoggerTest {
    /**
     * The GameLogger instance under test.
     */
    private GameLogger d_gameLogger = new GameLogger();

    /**
     * Tests that messages are correctly logged by the GameLogger.
     * Verifies that the update method successfully logs a message, indicated by returning true.
     */
    @Test
    public void testMessageLogging() {
        Assertions.assertTrue(d_gameLogger.update("Hello"));
    }

}
