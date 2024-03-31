package main.java.warzone.services;

import main.java.warzone.exceptions.WarzoneRuntimeException;
import main.java.warzone.exceptions.WarzoneValidationException;
import java.io.InputStream;

/**
 * Interface for managing main.java.game map data.
 * Defines methods to create and save main.java.game environments, including continents, countries, and borders.
 * Provides functionality to handle main.java.game map data, such as reading from an input stream and writing to an output stream.
 * Implementations of this interface are responsible for parsing map data and manipulating it for the Warzone main.java.game.
 * Methods include creating a main.java.game environment from an input stream and saving the current main.java.game environment to an output stream.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */

public interface GameMapDataHandler{

    /**
     * Generates a game session from the provided input stream.
     *
     * @param p_InputStream The input stream representing the game session data.
     * @throws WarzoneRuntimeException    If the file representing the game session data is not found.
     * @throws WarzoneValidationException If there's a validation error while processing the game session data.
     */
    void makeGameSession(InputStream p_InputStream) throws WarzoneRuntimeException, WarzoneValidationException;

    /**
     * Saves the current game session to a specified file.
     *
     * @param p_GameSessionFileName The name of the file to which the game session data should be saved.
     */
    void saveGameSession(String p_GameSessionFileName);
}
