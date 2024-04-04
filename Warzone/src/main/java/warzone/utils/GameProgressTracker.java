
package main.java.warzone.utils;

import main.java.warzone.entities.GameSession;
import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.exceptions.WarzoneValidationException;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;

import java.util.*;

/**
 * This class is used to manage the game save and load
 * functionalities
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */
public class GameProgressTracker {

    /**
     * Singleton instance of the game session.
     */
    private GameSession d_CurrGameSession;

    /**
     * LogEntryBuffer object to log the information
     * and notify the observers
     */
    private LogEntryBuffer d_LogEntryBuffer;

    /**
     * Constructor to instantiate the game progress tracker
     */
    public GameProgressTracker() {
        d_CurrGameSession = GameSession.getInstance();
        d_LogEntryBuffer = LogEntryBuffer.getInstance();
    }

    /**
     * Method to save the progress of the game
     *
     * @param p_FileName Name of the file
     * @return True if game is successfully saved, false otherwise
     */
    public boolean saveGameProgress(String p_FileName) {
        try {
            FileOutputStream l_FileOutputStream = new FileOutputStream(WarzoneConstants.SAVED_GAMES + WarzoneConstants.FORWARD_SLASH + p_FileName);
            ObjectOutputStream l_ObjectOutputStream = new ObjectOutputStream(l_FileOutputStream);
            l_ObjectOutputStream.writeObject(d_CurrGameSession);
            l_ObjectOutputStream.flush();
            l_FileOutputStream.close();
            d_CurrGameSession.clearPreviousSession();
            d_LogEntryBuffer.logData("The game has been successfully saved to file /GameSessions/" + p_FileName);
            return true;
        } catch (Exception p_Exception) {
            d_LogEntryBuffer.logData(p_Exception.toString());
            return false;
        }
    }

    /**
     * Method for listing the saved game files
     */
    public void listSavedGameFiles() {
        d_LogEntryBuffer.logData("..............................................");
        d_LogEntryBuffer.logData("\t\t Warzone - Load Menu");
        d_LogEntryBuffer.logData("..............................................");
        d_LogEntryBuffer.logData("\t\t\t Load Game");
        d_LogEntryBuffer.logData("\t.............................\n");
        File[] l_Files = new File(WarzoneConstants.SAVED_GAMES + WarzoneConstants.FORWARD_SLASH).listFiles();
        if (l_Files.length > 0) {
            for (File l_SavedGame : l_Files) {
                d_LogEntryBuffer.logData("\t\t\t" + l_SavedGame.getName());
            }
        } else {
            d_LogEntryBuffer.logData("\t\t " + "no saved games found");
        }
        d_LogEntryBuffer.logData("");
        d_LogEntryBuffer.logData("\t.......................");
        d_LogEntryBuffer.logData("\t use file name to load");
        d_LogEntryBuffer.logData("...............................");
        d_LogEntryBuffer.logData("example command: loadgame filename");
    }

    /**
     * Method to load the progress of the game
     *
     * @param p_FileName File to load
     * @return Upcoming game segment
     */
    public GameSegment loadGameProgress(String p_FileName) {
        try {
            FileInputStream l_FileInputStream = new FileInputStream(WarzoneConstants.SAVED_GAMES + WarzoneConstants.FORWARD_SLASH + p_FileName);
            ObjectInputStream l_Os = new ObjectInputStream(l_FileInputStream);
            GameSession l_LoadedGameMap = (GameSession) l_Os.readObject();
            d_LogEntryBuffer.logData("The game is successfully loaded and will continue from where it last stopped.");
            l_Os.close();
            return GameSession.getInstance().makeGameFromLoaded(l_LoadedGameMap);
        } catch (IOException | ClassNotFoundException e) {
            d_LogEntryBuffer.logData("The file could not be loaded.");
            return GameSegment.START_UP;
        } catch (WarzoneValidationException e) {
            d_LogEntryBuffer.logData("Failed to create game from saved");
            return GameSegment.START_UP;
        }
    }

}