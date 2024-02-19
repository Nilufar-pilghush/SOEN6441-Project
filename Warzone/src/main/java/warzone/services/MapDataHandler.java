package main.java.warzone.services;

import main.java.warzone.exceptions.WarzoneRuntimeException;
import main.java.warzone.exceptions.WarzoneValidationException;

import java.io.InputStream;
import java.io.OutputStream;

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

public interface MapDataHandler {

    /**
     * Loads a main.java.game map from an InputStream.
     * @param p_InputStream The source stream of the map file.
     * @throws WarzoneValidationException If the map or it's data cannot be loaded.
     */
    void createGameMap(InputStream p_InputStream) throws WarzoneRuntimeException, WarzoneValidationException;

    /**
     * Saves the current main.java.game map to an OutputStream.
     * @param p_OutputStream The destination stream to save the map.
     */
    void saveGameMap(String p_OutputStream);

}
