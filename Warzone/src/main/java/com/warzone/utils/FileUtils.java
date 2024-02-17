package main.java.com.warzone.utils;


import main.java.com.warzone.constants.WarzoneConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * This class is used to manage utils for file operations
 * @author Ali Sayed Salehi
 */
public class FileUtils {

    /**
     * creates the data stream from the map file
     *
     * @param p_GameFileName the name of the game map file
     * @return InputStream of the game file
     * @throws FileNotFoundException the game file is not found
     */
    public static InputStream getStreamFromGameFile(String p_GameFileName) throws FileNotFoundException {
        if (!p_GameFileName.endsWith(WarzoneConstants.GAME_MAP_EXTENSION)) {
            p_GameFileName = p_GameFileName + WarzoneConstants.GAME_MAP_EXTENSION;
        }
        System.out.println("Trying to load existing map: " + p_GameFileName);
        File l_GameScene = new File(WarzoneConstants.GAME_WORLDS + WarzoneConstants.FORWARD_SLASH + p_GameFileName);
        InputStream l_GameSceneMap = new FileInputStream(l_GameScene);
        return l_GameSceneMap;
    }


    /**
     * This method is used to list the game world maps available
     * in the maps directory for efficient game play
     *
     * @return True if list maps is successful, false otherwise.
     * @throws FileNotFoundException if the game world directory with maps is not present
     */
    public static boolean listMaps() throws FileNotFoundException {
        File l_MapsDir = new File(WarzoneConstants.GAME_WORLDS + WarzoneConstants.FORWARD_SLASH);
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
            System.out.println("Unable to find game worlds directory.");
            throw new FileNotFoundException();
        }
    }
}
