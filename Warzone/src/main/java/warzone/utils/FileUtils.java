package main.java.warzone.utils;
import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * This class is used to manage utils for file operations
 * It provides utility methods for file operations in the Warzone game.
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @since 3.0.0
 */
public class FileUtils {

    /**
     * LogEntryBuffer object to log the information
     * and notifying all the observers
     */
    private static LogEntryBuffer d_LogEntryBuffer = LogEntryBuffer.getInstance();

    /**
     * creates the data stream from the map file
     *
     * @param p_GameFileName the name of the main.java.game map file
     * @return InputStream of the main.java.game file
     * @throws FileNotFoundException the main.java.game file is not found
     */
    public static InputStream getStreamFromGameFile(String p_GameFileName) throws FileNotFoundException {
        if (!p_GameFileName.endsWith(WarzoneConstants.GAME_MAP_EXTENSION)) {
            p_GameFileName = p_GameFileName + WarzoneConstants.GAME_MAP_EXTENSION;
        }
        System.out.println("Trying to load existing map: " + p_GameFileName);
        File l_GameScene = new File(WarzoneConstants.GAME_SESSIONS + WarzoneConstants.FORWARD_SLASH + p_GameFileName);
        InputStream l_GameSceneMap = new FileInputStream(l_GameScene);
        return l_GameSceneMap;
    }


    /**
     * This method is used to list the game sessions maps available
     * in the maps directory for efficient game play
     *
     * @return True if list maps is successful, false otherwise.
     * @throws FileNotFoundException if the game sessions directory with maps is not present
     */
    public static boolean listMaps() throws FileNotFoundException {
        File l_MapsDir = new File(WarzoneConstants.GAME_SESSIONS + WarzoneConstants.FORWARD_SLASH);
        if (l_MapsDir.exists()) {
            File[] l_ExistingMaps = l_MapsDir.listFiles();
            if (l_ExistingMaps == null || l_ExistingMaps.length == 0) {
                d_LogEntryBuffer.logData("No existing maps found");
                return true;
            }
            StringBuilder response = new StringBuilder("Available maps--> ");
            for (int l_MapIndex = 0; l_MapIndex < l_ExistingMaps.length; l_MapIndex++) {
                File l_Map = l_ExistingMaps[l_MapIndex];
                if (l_Map.getName().endsWith(WarzoneConstants.GAME_MAP_EXTENSION)) {
                    response.append(l_Map.getName());
                    if (l_MapIndex != l_ExistingMaps.length - 1) {
                        response.append(WarzoneConstants.COMMA + WarzoneConstants.SPACE);
                    }
                }
            }
            d_LogEntryBuffer.logData(response.toString());
            return true;
        } else {
            d_LogEntryBuffer.logData("Unable to find game worlds directory.");
            throw new FileNotFoundException();
        }
    }

    /**
     * Checks whether a given game file is exists.
     *
     * @param p_FileName The name of the file to check.
     * @param p_DirectoryName The directory to check for the file
     * @return true if the file is found, else false.
     */
    public static boolean doesFileExist(String p_FileName, String p_DirectoryName) {
        File l_File = new File(p_DirectoryName, p_FileName);
        return l_File.exists();
    }

    /**
     * Method to create a directory if it doesn't exist
     *
     * @param p_DirName Directory to be created
     */
    public static void makeDirectoryIfAbsent(String p_DirName) {
        File l_directory = new File(p_DirName);
        if (!l_directory.exists()) {
            l_directory.mkdirs();
        }
    }
}
