package test.java.com.warzone;

        import main.java.warzone.GameEngine;
        import main.java.warzone.entities.GamePhase;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link GameEngine} focusing on critical functionalities such as game phase transitions.
 * This class tests the behavior of the game engine, particularly verifying its ability to handle
 * the game exit phase without errors, ensuring that the game can be exited as expected.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class GameEngineTest {

    private GameEngine d_gameEngine;

    /**
     * Initializes a new instance of GameEngine for testing its phase handling capabilities,
     * particularly focusing on the game exit phase.
     */
    public void testGameEngine() {
        d_gameEngine = new GameEngine();
    }
    /**
     * Validates that the GameEngine can handle the EXIT phase without throwing any exceptions.
     * This test ensures that the game engine correctly processes the command to exit the game, demonstrating
     * proper handling of the exit phase.
     */
    @Test
    public void testExitGame() {
        Assertions.assertDoesNotThrow(() -> d_gameEngine.handleGamePhases(GamePhase.EXIT),
                "Game engine should exit the game without throwing exceptions.");
    }
}
