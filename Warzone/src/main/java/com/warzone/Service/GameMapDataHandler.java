package main.java.com.warzone.Service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Blueprint interface for managing game environment/map data.
 * Defines methods to create and save game environments, including continents, countries, and borders.
 * Provides functionality to handle game map data, such as reading from an input stream and writing to an output stream.
 * Implementations of this interface are responsible for parsing map data and manipulating it for the Warzone game.
 * Methods include creating a game environment from an input stream and saving the current game environment to an output stream.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 * @version 1.0.0
 */

public interface GameMapDataHandler {

    // create the game environment with the given file
    void createGameMap(InputStream p_InputStream) throws Exception;

    // save current game environment in a file
    void saveGameMap(OutputStream p_OutputStream);

}
