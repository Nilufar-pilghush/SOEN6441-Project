package main.java.warzone;
import main.java.warzone.exceptions.WarzoneBaseException;

/**
 * Main Class to initialize warzone game.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */

public class Main
    {
        /**
         * Serves as the Warzone game's initial launch point.
         * Sets up and showcases the primary game menu to the user.
         *
         * @param args Command-line arguments (unused in this scenario).
         * @throws WarzoneBaseException if a game phase validation problem occurs.
         */
        public static void main( String[] args ) throws WarzoneBaseException {
            GamePlay l_GamePlay = new GamePlay();
            l_GamePlay.showMainMenu();
        }
    }

