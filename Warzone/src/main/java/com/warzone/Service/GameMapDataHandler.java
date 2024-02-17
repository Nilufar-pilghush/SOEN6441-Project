package main.java.com.warzone.Service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Interface for game map management which handles the game map
 * like creating, saving to specific files.
 */

public interface GameMapDataHandler {

    /**
     * Loads a game map from an InputStream.
     * @param p_InputStream The source stream of the map file.
     * @throws Exception If the map cannot be loaded.
     */
    void createGameMap(InputStream p_InputStream) throws Exception;

    /**
     * Saves the current game map to an OutputStream.
     * @param p_OutputStream The destination stream to save the map.
     */
    void saveGameMap(OutputStream p_OutputStream);

}
