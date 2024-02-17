package main.java.com.warzone.service;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Blueprint for managing game environment/map data.
 * Needs to handle game environment like creating, saving to specific files.
 */

public interface GameMapDataHandler {

    // create the game environment with the given file
    void makeGameSession(InputStream p_InputStream) throws Exception;

    // save current game environment in a file
    void saveGameMap(OutputStream p_OutputStream);

}
