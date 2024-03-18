package test.java.warzone;
        import main.java.warzone.Main;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.Test;
        import java.io.ByteArrayInputStream;
        import java.io.InputStream;
/**
 * Unit tests for the {@link Main} class to ensure it handles the game's start-up and exit procedures correctly.
 * This test suite aims to verify that the main application can start without errors and properly process exit commands.
 *
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class MainTest {
    /**
     * Validates that the main method can execute without throwing exceptions when a typical exit command is simulated.
     * This test ensures that the game can be started and exited gracefully based on user input.
     */
    @Test
    public void testGameExit() {
        String[] l_Args = new String[0];
        String input1 = "3";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(()-> Main.main(l_Args)); }
}
