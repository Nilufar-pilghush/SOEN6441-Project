package main.java.com.warzone.Service;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Blueprint for managing game environment/map data.
 * Needs to handle game environment like creating, saving to specific files.
 */

public interface GameMapDataHandler {

    // create the game environment with the given file
    void createGameEnvironment(InputStream p_InputStream);

    // save current game environment in a file
    void saveGameEnvironment(OutputStream p_OutputStream);

}
