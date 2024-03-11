package test.java.com.warzone;

        import main.java.warzone.GamePlay;
        import main.java.warzone.exceptions.WarzoneBaseException;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.Test;

        import java.io.ByteArrayInputStream;
        import java.io.InputStream;

    /**
     * Tests for {@link GamePlay} focusing on verifying the functionality of the main menu.
     * This class tests if the main menu can handle user inputs correctly, such as exiting the game,
     * and ensures that the game responds as expected to such commands.
     *
     *
     * @author Niloufar Pilgush
     * @author Nasrin Maarefi
     * @author Jerome Kithinji
     * @author Ali sayed Salehi
     * @author Fatemeh Chaji
     * @version 2.0.0
     */
    public class GamePlayTest {

        private GamePlay d_gameRunner;
        /**
         * Initializes a new instance of {@link GamePlay} to test its main menu functionality.
         */
        public void testGamePlay() {
            d_gameRunner = new GamePlay();
        }
        /**
         * Validates that selecting the exit option from the main menu correctly triggers game exit.
         * This test simulates user input for exiting the game and verifies that the game responds appropriately.
         *
         * @throws WarzoneBaseException if an error occurs during game play execution
         */
        @Test
        public void testMainMenuExit() throws WarzoneBaseException {
            String input1 = "3";
            InputStream in = new ByteArrayInputStream(input1.getBytes());
            System.setIn(in);
            Assertions.assertTrue(d_gameRunner.showMainMenu()); }
        }
