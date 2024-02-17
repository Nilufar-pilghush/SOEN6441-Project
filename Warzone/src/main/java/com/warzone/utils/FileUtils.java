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
}
