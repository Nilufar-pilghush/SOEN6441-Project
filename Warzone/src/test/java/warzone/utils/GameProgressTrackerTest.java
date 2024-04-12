package test.java.warzone.utils;

import main.java.warzone.entities.GamePhase;
import main.java.warzone.entities.GameSession;
import main.java.warzone.utils.GameProgressTracker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Concrete class implementing the behaviors necessary to manage
 * game session creation/saving from/to user specified files.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */
public class GameProgressTrackerTest {
    private GameProgressTracker gameProgressTracker;

    @BeforeEach
    void setUp() {
        this.gameProgressTracker = new GameProgressTracker();
    }

    @Test
    public void testSaveGameProgress() {
        GameProgressTracker gameProgress = new GameProgressTracker();
        String fileName = "testGame";
        boolean result = gameProgress.saveGameProgress(fileName);
        Assertions.assertTrue(result);
    }

    /**
     * Test case Verify the successful loading of game progress from a valid file.
     */

    @Test
    public void testLoadGameProgressSuccess() {
        GamePhase result = gameProgressTracker.loadGameProgress("testLoadGame.ser");
        assertNotNull(result);
    }

    /**
     * Test case Verify the behavior when attempting to load game progress from a nonexistent file.
     */
    @Test
    public void testLoadGameProgressFailureFileNotFound() {
        GamePhase result = gameProgressTracker.loadGameProgress("nonexistentFile.ser");
        assertEquals(GamePhase.START_UP, result);
    }

    /**
     * Test case Verify the behavior when attempting to load game progress from an invalid file.
     */
    @Test
    public void testLoadGameProgressFailureInvalidFile() {
        GamePhase result = gameProgressTracker.loadGameProgress("invalidFile.ser");
        assertEquals(GamePhase.START_UP, result);
    }

    /**
     * Test case to verify the behavior when attempting to load game progress with a validation exception.
     */

    @Test
    public void testLoadGameProgressFailureValidationException() {
        GamePhase result = gameProgressTracker.loadGameProgress("validationException.ser");
        assertEquals(GamePhase.START_UP, result);
    }
}
