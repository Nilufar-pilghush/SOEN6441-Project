package test.java.com.warzone;

        import main.java.warzone.GameEngine;
        import main.java.warzone.entities.GamePhase;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.Test;

/**
 * JUnit test cases for the Game Engine class.
 * Test case to verify game exit.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */
public class GameEngineTest {

    /**
     * Class to be tested
     */
    private GameEngine d_GameEngine;

    /**
     * Constructor to initialize game engine
     */
    public GameEngineTest() {
        d_GameEngine = new GameEngine();
    }

    /**
     * Test case to validate game exiting
     */
    @Test
    public void whenGameEngineExits_ExpectGameExitsTest() {
        Assertions.assertDoesNotThrow(() -> d_GameEngine.handleGamePhases(GamePhase.EXIT));
    }
}
