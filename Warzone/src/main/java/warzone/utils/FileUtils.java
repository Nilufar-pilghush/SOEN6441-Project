package main.java.warzone.utils;
import main.java.warzone.constants.WarzoneConstants;

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
 * @version 1.0.0
 */
public class FileUtils {

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
     * This method is used to list the main.java.game sessions maps available
     * in the maps directory for efficient main.java.game play
     *
     * @return True if list maps is successful, false otherwise.
     * @throws FileNotFoundException if the main.java.game sessions directory with maps is not present
     */
    public static boolean listMaps() throws FileNotFoundException {
        File l_MapsDir = new File(WarzoneConstants.GAME_SESSIONS + WarzoneConstants.FORWARD_SLASH);
        if (l_MapsDir.exists()) {
            File[] l_ExistingMaps = l_MapsDir.listFiles();
            if (l_ExistingMaps == null || l_ExistingMaps.length == 0) {
                System.out.println("No existing maps found");
                return true;
            }
            System.out.print("Available maps--> ");
            for (int l_MapIndex = 0; l_MapIndex < l_ExistingMaps.length; l_MapIndex++) {
                File l_Map = l_ExistingMaps[l_MapIndex];
                if (l_Map.getName().endsWith(WarzoneConstants.GAME_MAP_EXTENSION)) {
                    System.out.print(l_Map.getName());
                    if (l_MapIndex != l_ExistingMaps.length - 1) {
                        System.out.print(WarzoneConstants.COMMA + WarzoneConstants.SPACE);
                    }
                }
            }
            System.out.println();
            return true;
        } else {
            System.out.println("Unable to find main.java.game sessions directory.");
            throw new FileNotFoundException();
        }
    }
}
